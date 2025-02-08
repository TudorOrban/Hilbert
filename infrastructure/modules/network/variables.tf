variable "cidr_block" {
    type = string
    description = "CIDR block for the VPC"
}

variable "region" {
  type = string
  description = "AWS region"
}

variable "public_subnet_a_cidr" {
    type = string
    description = "CIDR block for public subnet A"
}

variable "public_subnet_b_cidr" {
    type = string
    description = "CIDR block for public subnet B"
}

variable "tags" {
    type = map(string)
    description = "Tags for the network resources"
}