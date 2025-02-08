resource "aws_db_instance" "main" {
    allocated_storage = var.allocated_storage
    db_name = var.db_name
    engine = "postgres"
    engine_version = var.engine_version
    instance_class = var.instance_class
    username = var.db_username
    password = random_password.db_password.result
    parameter_group_name = aws_db_parameter_group.main.name
    vpc_security_group_ids = [aws_security_group.rds_sg.id]
    db_subnet_group_name = aws_db_subnet_group.rds_subnet_group.name
    multi_az = var.multi_az
    publicly_accessible = var.publicly_accessible
    skip_final_snapshot = true
    identifier = var.identifier

    depends_on = [
        var.internet_gateway_id,
        var.public_route_table_a_id,
        var.public_route_table_b_id 
    ]

    tags = var.tags
}

resource "random_password" "db_password" {
    length = 16
    special = true
}

resource "aws_db_parameter_group" "main" {
    name = "rds-param-group-${var.identifier}"
    family = "postgres${var.engine_version}"
}

resource "aws_security_group" "rds_sg" {
    name = "rds-sg-${var.identifier}"
    description = "Security group for RDS instance"
    vpc_id = var.vpc_id

    ingress {
        from_port = 5432
        to_port = 5432
        protocol = "tcp"
        cidr_blocks = var.allowed_cidrs
    }

    egress {
        from_port = 0
        to_port = 0
        protocol = "-1"
        cidr_blocks = ["0.0.0.0/0"]
    }

    tags = var.tags
}

resource "aws_db_subnet_group" "rds_subnet_group" {
    name = "rds-subnet-group-${var.identifier}"
    subnet_ids = var.subnet_ids
    description = "Subnet group for RDS instance"

    tags = var.tags
}
