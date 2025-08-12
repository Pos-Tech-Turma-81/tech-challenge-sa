data "aws_vpc" "postech_vpc" {
  filter {
    name   = "tag:Name"
    values = ["postech"]
  }
}

data "aws_subnet" "private_a" {
  filter {
    name   = "tag:Name"
    values = ["private-a"]
  }
}

data "aws_subnet" "private_b" {
  filter {
    name   = "tag:Name"
    values = ["private-b"]
  }
}


resource "aws_security_group" "ecs_sg" {
  name        = "ecs-sg"
  description = "Permitir acesso ao RDS"
  vpc_id      = data.aws_vpc.postech_vpc.id

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_security_group" "rds_sg" {
  name        = "rds-postgres-sg"
  description = "Permitir acesso Postgres apenas do ECS"
  vpc_id      =  data.aws_vpc.postech_vpc.id

  ingress {
    description     = "Postgres from ECS"
    from_port       = 5432
    to_port         = 5432
    protocol        = "tcp"
    security_groups = [aws_security_group.ecs_sg.id]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}


resource "aws_db_subnet_group" "rds_subnet_group" {
  name       = "rds-subnet-group"
  subnet_ids = [data.aws_subnet.private_a.id, data.aws_subnet.private_b.id]
}


resource "aws_db_instance" "postgres" {
  identifier              = "postgres-restaurante"
  engine                  = "postgres"
  engine_version          = "15"
  instance_class          = "db.t3.micro"
  allocated_storage       = 20
  username                = "adminuser"
  password                = "SenhaForte123!"
  db_subnet_group_name    = aws_db_subnet_group.rds_subnet_group.name
  vpc_security_group_ids  = [aws_security_group.rds_sg.id]
  skip_final_snapshot     = true
}
