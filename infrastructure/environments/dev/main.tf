provider "aws" {
    region = "eu-west-1"
}

module "network" {
    source = "../../modules/network"

    cidr_block = "10.0.0.0/16"
    region = "eu-west-1"
    public_subnet_a_cidr = "10.0.1.0/24"
    public_subnet_b_cidr = "10.0.2.0/24"

    tags = {
        Environment = "dev"
    }
}

module "s3" {
    source = "../../modules/s3"

    bucket_name = "hilbert-frontend-dev"
    acl = "private"
    tags = {
        Name = "My bucket"
        Environment = "Dev"
    }
}

module "rds" {
    source = "../../modules/rds"
    
    identifier = "dev-rds-instance"
    allocated_storage = 20
    db_name = "hilbert_db"
    engine_version = "15"
    instance_class = "db.t3.micro"
    db_username = "postgres"
    multi_az = false
    publicly_accessible = true

    vpc_id = module.network.vpc_id
    subnet_ids = [module.network.public_subnet_a_id, module.network.public_subnet_b_id]
    allowed_cidrs = [var.allowed_cidr]
    internet_gateway_id = module.network.internet_gateway_id
    public_route_table_a_id = module.network.public_route_table_a_id
    public_route_table_b_id = module.network.public_route_table_b_id

    tags = {
        Environment = "dev"
    }
}

data "aws_caller_identity" "current" {}

data "aws_ecr_repository" "hilbert_main_repo" {
    name = "hilbert-main"
    registry_id = data.aws_caller_identity.current.account_id
}

data "aws_ecr_repository" "hilbert_ml_repo" {
    name = "hilbert_ml"
    registry_id = data.aws_caller_identity.current.account_id
}

module "ecs" {
    source = "../../modules/ecs"
    cluster_name = "dev-cluster"
    instance_type = "t2.micro"

    desired_capacity = 2
    min_size = 2
    max_size = 2

    public_subnet_a_id = module.network.public_subnet_a_id
    public_subnet_b_id = module.network.public_subnet_b_id    
    ec2_sg_id = module.network.rds_sg_id

    hilbert_main_image_uri = "${data.aws_ecr_repository.hilbert_main_repo.repository_url}:latest"
    hilbert_ml_image_uri = "${data.aws_ecr_repository.hilbert_ml_repo.repository_url}:latest"

    hilbert_main_desired_count = 1
    hilbert_ml_desired_count = 1

    rds_endpoint = module.rds.rds_endpoint
    db_name = module.rds.db_name
    db_username = module.rds.db_username
    db_password = module.rds.db_password

    tags = {
        Environment = "dev"
    }
}