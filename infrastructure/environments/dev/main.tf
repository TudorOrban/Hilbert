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