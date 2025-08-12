provider "aws" {
  region = "us-east-1"  # ou a região que preferir
}

resource "aws_iam_role" "ecs_task_execution_role" {
  name = "ecsTaskExecutionRole-estudo"

  assume_role_policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Action = "sts:AssumeRole",
        Effect = "Allow",
        Principal = {
          Service = "ecs-tasks.amazonaws.com"
        }
      }
    ]
  })
}

resource "aws_iam_role_policy_attachment" "ecs_task_execution_policy" {
  role       = aws_iam_role.ecs_task_execution_role.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"
}

resource "aws_ecs_cluster" "fargate_cluster" {
  name = "estudo-cluster"
}

resource "aws_vpc" "my_vpc" {
  cidr_block = "10.0.0.0/16"
}

resource "aws_subnet" "my_subnet" {
  vpc_id            = aws_vpc.my_vpc.id
  cidr_block        = "10.0.1.0/24"
  availability_zone = "us-east-1a"  # ajuste conforme a sua região
}

resource "aws_security_group" "ecs_sg" {
  vpc_id = aws_vpc.my_vpc.id
  ingress {
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_internet_gateway" "my_igw" {
  vpc_id = aws_vpc.my_vpc.id
}

resource "aws_route_table" "my_route_table" {
  vpc_id = aws_vpc.my_vpc.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.my_igw.id
  }
}

resource "aws_route_table_association" "my_route_table_association" {
  subnet_id      = aws_subnet.my_subnet.id
  route_table_id = aws_route_table.my_route_table.id
}

resource "aws_ecs_task_definition" "my_task" {
  family                   = "my-task"
  network_mode             = "awsvpc"
  requires_compatibilities = ["FARGATE"]
  cpu                      = "256"
  memory                   = "512"
  execution_role_arn       = aws_iam_role.ecs_task_execution_role.arn

  container_definitions = jsonencode([
    {
      name  = "hello-world"
      image = "851725589063.dkr.ecr.us-east-1.amazonaws.com/restaurante-app"
      cpu   = 256
      memory = 512
      essential = true
      portMappings = [
        {
          containerPort = 8080  
          hostPort      = 8080 
        }
      ]
    }
  ])
}


resource "aws_ecs_service" "my_ecs_service" {
  name            = "my-ecs-service"
  cluster         = aws_ecs_cluster.fargate_cluster.id
  task_definition = aws_ecs_task_definition.my_task.arn
  desired_count   = 1
  launch_type     = "FARGATE"
  network_configuration {
    subnets          = [aws_subnet.my_subnet.id]
    security_groups  = [aws_security_group.ecs_sg.id]
    assign_public_ip = true
  }

  load_balancer {
    target_group_arn = aws_lb_target_group.my_target_group.arn
    container_name   = "hello-world"
    container_port   = 8080  # Porta que o container está ouvindo
  }
}

# Criar o NLB
resource "aws_lb" "my_nlb" {
  name               = "kotlin-nlb"
  internal           = false  # Deixe false para ser acessível da internet
  load_balancer_type = "network"  # Network Load Balancer
  subnets            = [aws_subnet.my_subnet.id]  # Subnet pública
}

# Target group para o ECS
resource "aws_lb_target_group" "my_target_group" {
  name        = "my-target-group"
  port        = 8080  # Porta usada pelo container
  protocol    = "TCP"  # Use TCP para um NLB
  vpc_id      = aws_vpc.my_vpc.id
  target_type = "ip"

  health_check {
    interval            = 30
    path                = "/"  # Ajuste conforme o endpoint da aplicação
    protocol            = "HTTP"  # Isso é importante para health checks
    matcher             = "200-399"
    timeout             = 5
    healthy_threshold   = 2
    unhealthy_threshold = 2
  }
}

resource "aws_lb_listener" "my_nlb_listener" {
  load_balancer_arn = aws_lb.my_nlb.arn
  port              = 80  # O listener escuta na porta 80 para tráfego externo
  protocol          = "TCP"  # Para HTTP
  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.my_target_group.arn
  }
}