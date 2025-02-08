resource "aws_ecs_cluster" "main" {
    name = var.cluster_name
}

resource "aws_launch_configuration" "ecs_launch_config" {
    name = "ecs-launch-config-${var.cluster_name}"
    image_id = "ami-0c94855ba95c574c8"
    instance_type = var.instance_type
    security_groups = [var.ec2_sg_id]

    user_data = templatefile("${path.module}/user_data.tpl", {
        cluster_name = aws_ecs_cluster.main.name
    })

    lifecycle {
        create_before_destroy = true
    }
}

resource "aws_autoscaling_group" "ecs_asg" {
    name = "ecs-asg-${var.cluster_name}"
    launch_configuration = aws_launch_configuration.ecs_launch_config.id

    desired_capacity = var.desired_capacity
    min_size = var.min_size
    max_size = var.max_size

    vpc_zone_identifier = [var.public_subnet_a_id, var.public_subnet_b_id]

    tags = var.tags
}

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
                { name = "SPRING_DATASOURCE_URL", value = "jdbc:postgresql://${var.rds_endpoint}/${var.db_name}" },
                { name = "SPRING_DATASOURCE_USERNAME", value = var.db_username },
                { name = "SPRING_DATASOURCE_PASSWORD", value = var.db_password }
            ],
            cpu = 256,
            memory = 512
        }
    ])
}

resource "aws_ecs_task_definition" "hilbert_ml_task_definition" {
    family = "hilbert-ml-task"
    container_definitions = jsonencode([
        {
            name = "hilbert-ml-container",
            image = var.hilbert_ml_uri,
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

resource "aws_ecs_service" "hilbert_main_service" {
    name = "hilbert-main-service"
    cluster = aws_ecs_cluster.main.id
    task_definition = aws_ecs_task_definition.hilbert_main_task_definition
    desired_count = var.hilbert_main_desired_count
}

resource "aws_ecs_service" "hilbert_ml_service" {
    name = "hilbert-ml-service"
    cluster = aws_ecs_cluster.main.id
    task_definition = aws_ecs_task_definition.hilbert_ml_task_definition
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