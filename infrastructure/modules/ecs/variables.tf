variable "cluster_name" {
    type = string
}

variable "instance_type" {
    type = string
    default = "t2.micro"
}

variable "desired_capacity" {
    type = number
    default = 2
}

variable "min_size" {
    type = number
    default = 2
}

variable "max_size" {
    type = number
    default = 2
}

variable "public_subnet_a_id" {
    type = string
}

variable "public_subnet_b_id" {
    type = string
}

variable "ec2_sg_id" {
    type = string
}

variable "hilbert_main_image_uri" {
    type = string
}

variable "hilbert_ml_image_uri" {
    type = string
}

variable "hilbert_main_desired_count" {
    type = number
    default = 1
}

variable "hilbert_ml_desired_count" {
    type = number
    default = 1
}

variable "rds_endpoint" {
    type = string
}

variable "db_name" {
    type = string
}

variable "db_username" {
    type = string
}

variable "db_password" {
    type = string
    sensitive = true
}

variable "tags" {
    type = map(string)
    default = {}
}