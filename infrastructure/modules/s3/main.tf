resource "aws_s3_bucket" "this" {
    bucket = var.bucket_name
    acl = var.acl
    tags = var.tags
}

resource "aws_s3_bucket_website_configuration" "this" {
    bucket = aws_s3_bucket.this.bucket

    index_document {
        suffix = "index.html"
    }

    error_document {
        key = "error.html"
    }
}

resource "aws_s3_bucket_policy" "this" {
    bucket = aws_s3_bucket.this.bucket
    policy = data.aws_iam_policy_document.this.json
}

data "aws_iam_policy_document" "this" {
    statement {
        actions = ["s3:GetObject"]
        resources = ["${aws_s3_bucket.this.arn}/*"]
        
        principals {
            type = "AWS"
            identifiers = ["*"]
        }
    }
}