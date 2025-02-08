provider "aws" {
    region = "eu-west-1"
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
    db_name = "hilbert_ml"
    engine_version = "15"
    instance_class = "db.t3.micro"
    db_username = "postgres"
    multi_az = false
    publicly_accessible = true

    vpc_id = "some_vpc_id"
    subnet_ids = ["to be added"]
    allowed_cidrs = ["laptop.ip.address/32"]

    tags = {
        Environment = "dev"
    }
}