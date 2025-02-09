output "vpc_id" {
    value = aws_vpc.main.id
}

output "public_subnet_a_id" {
    value = aws_subnet.public_a.id
}

output "public_subnet_b_id" {
    value = aws_subnet.public_b.id
}

output "internet_gateway_id" {
    value = aws_internet_gateway.gw.id
}

output "public_route_table_a_id" {
    value = aws_route_table.public_a.id
}

output "public_route_table_b_id" {
    value = aws_route_table.public_b.id
}

output "private_subnet_a_id" {
    value = aws_subnet.private_a.id
}

output "private_subnet_b_id" {
    value = aws_subnet.private_b.id
}