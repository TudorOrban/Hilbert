/*
 * Network Module
 *
 * Creates the network infrastructure for the application:
 * - A VPC with DNS hostnames enabled.
 * - Two public subnets in different AZs for RDS, with internet gateway access.
 * - Two private subnets in different AZs for ECS, with a NAT gateway for outbound internet access.
 * - An Application Load Balancer in the public subnets to expose the ECS services.
 * - Security groups to control traffic flow between the ALB, ECS instances, and the internet.
 */
resource "aws_vpc" "main" {
    cidr_block = var.cidr_block
    enable_dns_hostnames = true

    tags = var.tags
}

# Public subnets (for RDS)
resource "aws_subnet" "public_a" {
    vpc_id = aws_vpc.main.id
    cidr_block = var.public_subnet_a_cidr
    availability_zone = "${var.region}a"

    tags = var.tags
}

resource "aws_subnet" "public_b" {
    vpc_id = aws_vpc.main.id
    cidr_block = var.public_subnet_b_cidr
    availability_zone = "${var.region}b"

    tags = var.tags
}

resource "aws_internet_gateway" "gw" {
    vpc_id = aws_vpc.main.id

    tags = {
        Name = "main-igw"
    }
}

resource "aws_route_table" "public_a" {
    vpc_id = aws_vpc.main.id

    route {
        cidr_block = "0.0.0.0/0"
        gateway_id = aws_internet_gateway.gw.id
    }

    tags = {
        Name = "public-route-table-a"
    }
}

resource "aws_route_table_association" "public_a" {
    subnet_id = aws_subnet.public_a.id
    route_table_id = aws_route_table.public_a.id
}

resource "aws_route_table" "public_b" {
    vpc_id = aws_vpc.main.id

    route {
        cidr_block = "0.0.0.0/0"
        gateway_id = aws_internet_gateway.gw.id
    }

    tags = {
        Name = "public-route-table-b"
    }
}

resource "aws_route_table_association" "public_b" {
    subnet_id = aws_subnet.public_b.id
    route_table_id = aws_route_table.public_b.id
}

# Private subnets (for ECS)
resource "aws_subnet" "private_a" {
    vpc_id = aws_vpc.main.id
    cidr_block = var.private_subnet_a_cidr
    availability_zone = "${var.region}a"

    tags = var.tags
}

resource "aws_subnet" "private_b" {
    vpc_id = aws_vpc.main.id
    cidr_block = var.private_subnet_b_cidr
    availability_zone = "${var.region}b"

    tags = var.tags
}

resource "aws_nat_gateway" "nat_gateway" {
    allocation_id = aws_eip.nat_gateway_eip.id
    subnet_id = aws_subnet.public_a.id

    tags = {
        Name = "nat-gateway"
    }
}

resource "aws_eip" "nat_gateway_eip" {
    vpc = true

    tags = {
        Name = "nat-gateway-eip"
    }
}

resource "aws_route_table" "private_a" {
    vpc_id = aws_vpc.main.id

    route {
        cidr_block = "0.0.0.0/0"
        nat_gateway_id = aws_nat_gateway.nat_gateway.id
    }

    tags = {
        Name = "private-route-table-a"
    }
}

resource "aws_route_table_association" "private_a" {
    subnet_id = aws_subnet.private_a.id
    route_table_id = aws_route_table.private_a.id
}

resource "aws_route_table" "private_b" {
    vpc_id = aws_vpc.main.id

    route {
        cidr_block = "0.0.0.0/0"
        nat_gateway_id = aws_nat_gateway.nat_gateway.id
    }

    tags = {
        Name = "private-route-table-b"
    }
}

resource "aws_route_table_association" "private_b" {
    subnet_id = aws_subnet.private_b.id
    route_table_id = aws_route_table.private_b.id
}

# Load Balancer
resource "aws_lb" "app_lb" {
    name = "app-lb"
    internal = false
    load_balancer_type = "application"
    subnets = [aws_subnet.public_a.id, aws_subnet.public_b.id]
    security_groups = [aws_security_group.alb_sg.id]
}

resource "aws_lb_listener" "http" {
    load_balancer_arn = aws_lb.app_lb.arn
    port = 80
    protocol = "HTTP"

    default_action {
        type = "forward"
        target_group_arn = aws_lb_target_group.app_tg.arn
    }
}

resource "aws_lb_target_group" "app_tg" {
    name = "app-tg"
    port = 8080
    protocol = "HTTP"
    vpc_id = aws_vpc.main.id
}

resource "aws_security_group" "alb_sg" {
    name = "alb-sg"
    vpc_id = aws_vpc.main.id

    ingress {
        from_port = 80
        to_port = 80
        protocol = "tcp"
        cidr_blocks = ["0.0.0.0/0"]
    }

    egress {
        from_port = 0
        to_port = 0
        protocol = "-1"
        cidr_blocks = ["0.0.0.0/0"]
    }
}

resource "aws_security_group" "ecs_tasks_sg" {
    name = "ecs-tasks-sg"
    vpc_id = aws_vpc.main.id

    ingress {
        from_port = 8080
        to_port = 8080
        protocol = "tcp"
        security_groups = [aws_security_group.alb_sg.id]
    }

    egress { 
        from_port = 0
        to_port = 0
        protocol = "-1"  
        cidr_blocks = ["0.0.0.0/0"]
    }
}