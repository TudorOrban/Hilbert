#!/bin/bash
echo ECS_CLUSTER=dev-cluster >> /etc/ecs/ecs.config
echo ECS_AVAILABLE_LOGGING_DRIVERS=["json-file","awslogs"] >> /etc/ecs/ecs.config
echo ECS_LOGLEVEL=info >> /etc/ecs/ecs.config
yum update -y
yum install -y ecs-init amazon-ssm-agent
systemctl enable ecs
systemctl enable amazon-ssm-agent
systemctl start amazon-ssm-agent
