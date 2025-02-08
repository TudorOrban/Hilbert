variable "bucket_name" {
    description = "The name of the S3 bucket"
    type = string
}

variable "acl" {
    description = "The ACL of the S3 bucket"
    type = string
}

variable "tags" {
    description = "The tags of the S3 bucket"
    type = map(string)
}