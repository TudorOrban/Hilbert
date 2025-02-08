
output "db_endpoint" {
    value = aws_db_instance.main.endpoint
}

output "db_port" {
    value = aws_db_instance.main.port
}

output "db_name" {
    value = aws_db_instance.main.db_name
}

output "db_username" {
    value = aws_db_instance.main.username
}

output "db_password" {
    value     = random_password.db_password.result
    sensitive = true
}

output "rds_sg_id" {
    value = aws_security_group.rds_sg.id
}