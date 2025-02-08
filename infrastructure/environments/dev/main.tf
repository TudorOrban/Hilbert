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

