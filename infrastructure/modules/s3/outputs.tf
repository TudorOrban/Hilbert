output "bucket_id" {
    description = "The name of the S3 bucket"
    value = aws_s3_bucket.this.id
}

output "s3_website_url" {
    value = aws_s3_bucket_website_configuration.this.website_endpoint
}