variable "identifier" {
    type = string
    description = "A unique identifier for the RDS instance"
}

variable "allocated_storage" {
    type = number
    description = "Allocated storage in GB"
}

variable "db_name" {
    type = string
    description = "Database name"
}

variable "engine_version" {
    type = string
    description = "PostgreSQL engine version"
}

variable "instance_class" {
    type = string
    description = "RDS instance class"
}

variable "db_username" {
    type = string
    description = "Database username"
}

variable "multi_az" {
    type = bool
    description = "Whether to create a multi-AZ deployment"
}

variable "publicly_accessible" {
    type = bool
    description = "Whether the RDS instance is publicly accessible"
}

variable "vpc_id" {
    type = string
    description = "VPC ID"
}

variable "subnet_ids" {
    type = list(string)
    description = "List of subnet IDs"
}

variable "allowed_cidrs" {
    type = list(string)
    description = "List of allowed CIDR blocks for security group"
}

variable "tags" {
    type = map(string)
    description = "Tags for the RDS instance"
}