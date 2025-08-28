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

resource "aws_security_group" "eks_cluster_sg" {
  name        = "eks-cluster-sg"
  description = "Security Group for EKS Cluster"
  vpc_id      =  data.aws_vpc.postech_vpc.id

  # Permite comunicação do cluster para os nós
  ingress {
    description = "Allow EKS Control Plane"
    from_port   = 443
    to_port     = 443
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  # Saída liberada
  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}


resource "aws_eks_access_entry" "eks-access-entry" {
  cluster_name      = aws_eks_cluster.eks_cluster_restaurante.name
  principal_arn     = var.principalArn
  kubernetes_groups = ["fiap"]
  type              = "STANDARD"
}

resource "aws_eks_access_policy_association" "eks-access-policy" {
  cluster_name  = aws_eks_cluster.eks_cluster_restaurante.name
  policy_arn    = var.policyArn
  principal_arn = var.principalArn

  access_scope {
    type = "cluster"
  }
}

# 8. Cluster EKS
resource "aws_eks_cluster" "eks_cluster_restaurante" {
  name     = "eks-fargate-eks_cluster_restaurante"
  role_arn = var.labRole

  vpc_config {
    subnet_ids = [data.aws_subnet.private_a.id, data.aws_subnet.private_b.id]
    security_group_ids = [aws_security_group.eks_cluster_sg.id]
  }

  access_config {
    authentication_mode = var.accessConfig
  }
}

resource "aws_eks_node_group" "eks_nodes" {
  cluster_name    = aws_eks_cluster.eks_cluster_restaurante.name
  node_group_name = var.nodeGroup
  node_role_arn   = var.labRole
  subnet_ids      = [data.aws_subnet.private_a.id, data.aws_subnet.private_b.id]
  disk_size       = 50
  instance_types  = [var.instanceType]

  scaling_config {
    desired_size = 1
    min_size     = 1
    max_size     = 1
  }

  update_config {
    max_unavailable = 1
  }
}

