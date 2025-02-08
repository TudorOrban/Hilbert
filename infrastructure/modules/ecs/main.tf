resource "aws_ecs_cluster" "main" {
    name = var.cluster_name
}

resource "aws_launch_template" "ecs_launch_template" {
    name_prefix = "ecs-launch-template-"
    image_id    = data.aws_ami.ecs_optimized.id
    instance_type = var.instance_type
    user_data = base64encode(templatefile("${path.module}/user_data.tpl", {
        cluster_name = aws_ecs_cluster.main.name
    }))
    network_interfaces {
        security_groups = [var.ec2_sg_id]
    }

    lifecycle {
        create_before_destroy = true
    }

    tags = {
        Updated = timestamp()
    }
}

resource "aws_autoscaling_group" "ecs_asg" {
    name = "ecs-asg-${var.cluster_name}"
    launch_template {
        id = aws_launch_template.ecs_launch_template.id
        version = "$Latest"
    }

    desired_capacity = var.desired_capacity
    min_size = var.min_size
    max_size = var.max_size

    vpc_zone_identifier = [var.public_subnet_a_id, var.public_subnet_b_id]
}

# Hilbert Main Service
resource "aws_ecs_task_definition" "hilbert_main_task_definition" {
    family = "hilbert-main-task"
    container_definitions = jsonencode([
        {
            name = "hilbert-main-container",
            image = var.hilbert_main_image_uri,
            portMappings = [
                {
                    containerPort = 8080,
                    hostPort = 8080
                }
            ],
            environment = [
                { name = "SPRING_PROFILES_ACTIVE", value = "docker-prod" },
                { name = "DATABASE_URL_PROD", value = "jdbc:postgresql://${var.db_endpoint}:5432/${var.db_name}"},
                { name = "DATABASE_USER_PROD", value = var.db_username },
                { name = "DATABASE_PASSWORD_PROD", value = var.db_password },
                { name = "FRONTEND_API_URL_PROD", value = var.s3_website_url },
            ],
            secrets = [
                {
                    name = "JWT_SECRET_PROD",
                    valueFrom = "arn:aws:secretsmanager:eu-west-1:474668403865:secret:hilbert-app-secrets-szLLQu:JWT_SECRET_PROD::"
                },
                {
                    name = "HILBERT_ML_API_KEY_PROD",
                    valueFrom = "arn:aws:secretsmanager:eu-west-1:474668403865:secret:hilbert-app-secrets-szLLQu:HILBERT_ML_API_KEY_PROD::"
                }
            ]
            cpu = 256,
            memory = 512
        }
    ])

    execution_role_arn = aws_iam_role.ecs_tasks_role.arn
}

resource "aws_iam_role" "ecs_tasks_role" {
    name = "ecs-tasks-role"
    assume_role_policy = jsonencode({
        Version = "2012-10-17"
        Statement = [
            {
                Action = "sts:AssumeRole"
                Effect = "Allow"
                Principal = {
                    Service = "ecs-tasks.amazonaws.com"
                }
            }
        ]
    })
}

resource "aws_iam_policy" "ecs_tasks_secrets_policy" {
    name = "ecs-tasks-secrets-policy"
    policy = jsonencode({
        Version = "2012-10-17"
        Statement = [
            {
                Action = [
                    "secretsmanager:GetSecretValue",
                    "secretsmanager:DescribeSecret",
                ],
                Effect = "Allow",
                Resource = [
                    "arn:aws:secretsmanager:eu-west-1:474668403865:secret:hilbert-app-secrets-szLLQu*"
                ]
            }
        ]
    })
}

resource "aws_iam_policy_attachment" "ecs_tasks_secrets_policy_attachment" {
    name = "ecs-tasks-secrets-policy-attachment"
    policy_arn = aws_iam_policy.ecs_tasks_secrets_policy.arn
    roles = [aws_iam_role.ecs_tasks_role.name]
}

resource "aws_iam_policy_attachment" "ecs_tasks_ecr_policy_attachment" {
    name = "ecs-tasks-ecr-policy-attachment"
    policy_arn = "arn:aws:iam::aws:policy/AmazonEC2ContainerRegistryReadOnly"
    roles = [aws_iam_role.ecs_tasks_role.name]
}

resource "aws_ecs_service" "hilbert_main_service" {
    name = "hilbert-main-service"
    cluster = aws_ecs_cluster.main.id
    task_definition = aws_ecs_task_definition.hilbert_main_task_definition.arn
    desired_count = var.hilbert_main_desired_count
}

# Hilbert ML Service
resource "aws_ecs_task_definition" "hilbert_ml_task_definition" {
    family = "hilbert-ml-task"
    container_definitions = jsonencode([
        {
            name = "hilbert-ml-container",
            image = var.hilbert_ml_image_uri,
            portMappings = [
                {
                    containerPort = 5000,
                    hostPort = 5000
                }
            ],
            cpu = 256,
            memory = 512
        }
    ])
}

resource "aws_ecs_service" "hilbert_ml_service" {
    name = "hilbert-ml-service"
    cluster = aws_ecs_cluster.main.id
    task_definition = aws_ecs_task_definition.hilbert_ml_task_definition.arn
    desired_count = var.hilbert_ml_desired_count 
}


data "aws_ami" "ecs_optimized" {
    most_recent = true
    owners = ["amazon"]
    filter {
        name = "name"
        values = ["amzn-ami-*"]
    }
}