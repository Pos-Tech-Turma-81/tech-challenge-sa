data "aws_vpc" "postech_vpc" {
  filter {
    name   = "tag:Name"
    values = ["postech"]
  }
}

data "aws_subnet" "public_a" {
  filter {
    name   = "tag:Name"
    values = ["public-a"]
  }
}

data "aws_subnet" "public_b" {
  filter {
    name   = "tag:Name"
    values = ["public-b"]
  }
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


resource "aws_security_group" "ecs_sg" {
  vpc_id = data.aws_vpc.postech_vpc.id
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

resource "aws_cloudwatch_log_group" "restaurante_log_group" {
  name              = "/ecs/restaurante-logs"
  retention_in_days = 7
}


resource "aws_ecs_task_definition" "restaurante_task" {
  family                   = "restaurante-task"
  network_mode             = "awsvpc"
  requires_compatibilities = ["FARGATE"]
  cpu                      = "256"
  memory                   = "512"
  execution_role_arn       = aws_iam_role.ecs_task_execution_role.arn

  container_definitions = jsonencode([
    {
      name  = "restaurante-ecs"
      image = "617618117752.dkr.ecr.us-east-1.amazonaws.com/restaurante-app:latest"
      cpu   = 256
      memory = 512
      essential = true
       environment : [
        {
          name : "DB_HOST",
          value : "postgres-restaurante.ckzwimcqm9la.us-east-1.rds.amazonaws.com"
        },
        {
          name : "DB_NAME",
          value : "postgres_restaurante"
        },
        {
          name : "DB_USER",
          value : "adminuser"
        },
        {
          name : "DB_PASSWORD",
          value : "SenhaForte123!"
        }
      ],
      portMappings = [
        {
          containerPort = 8080  
          hostPort      = 8080 
        }
      ]
      logConfiguration = {
        logDriver = "awslogs"
        options = {
          awslogs-group         = aws_cloudwatch_log_group.restaurante_log_group.name
          awslogs-region        = "us-east-1"
          awslogs-stream-prefix = "ecs"
        }
      }
    }
  ])
}

resource "aws_iam_role_policy_attachment" "ecs_logs_policy" {
  role       = aws_iam_role.ecs_task_execution_role.name
  policy_arn = "arn:aws:iam::aws:policy/CloudWatchLogsFullAccess"
}


resource "aws_ecs_service" "restaurante_ecs_service" {
  name            = "restaurante-ecs-service"
  cluster         = aws_ecs_cluster.fargate_cluster.id
  task_definition = aws_ecs_task_definition.restaurante_task.arn
  desired_count   = 1
  launch_type     = "FARGATE"
  network_configuration {
    subnets          = [data.aws_subnet.public_a.id, data.aws_subnet.public_b.id]
    security_groups  = [aws_security_group.ecs_sg.id]
    assign_public_ip = true
  }

  load_balancer {
    target_group_arn = aws_lb_target_group.restaurante_target_group.arn
    container_name   = "restaurante-ecs"
    container_port   = 8080  # Porta que o container está ouvindo
  }
}

# Criar o NLB
resource "aws_lb" "restaurante_nlb" {
  name               = "restaurante-nlb"
  internal           = false  # Deixe false para ser acessível da internet
  load_balancer_type = "network"  # Network Load Balancer
  subnets            = [data.aws_subnet.public_a.id, data.aws_subnet.public_b.id]  # Subnet pública
}

# Target group para o ECS
resource "aws_lb_target_group" "restaurante_target_group" {
  name        = "restaurante-target-group"
  port        = 8080  # Porta usada pelo container
  protocol    = "TCP"  # Use TCP para um NLB
  vpc_id      = data.aws_vpc.postech_vpc.id
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

resource "aws_lb_listener" "restaurante_nlb_listener" {
  load_balancer_arn = aws_lb.restaurante_nlb.arn
  port              = 80  # O listener escuta na porta 80 para tráfego externo
  protocol          = "TCP"  # Para HTTP
  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.restaurante_target_group.arn
  }
}