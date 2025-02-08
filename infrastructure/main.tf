provider "aws" {
    region = "eu-west-1"
}

resource "aws_s3_bucket" "hilbert_frontend" {
    bucket = "hilbert-frontend"
    acl = "private"

    tags = {
        Name = "My bucket"
        Environment = "Dev"
    }
}