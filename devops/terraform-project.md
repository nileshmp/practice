### **Project Overview:**
You will deploy a **Spring Boot application** on **AWS** (or any other cloud provider you prefer). You'll be using **Terraform** to provision the infrastructure, which includes:

1. **VPC** with public and private subnets.
2. **Application Load Balancer (ALB)** for routing traffic to your application.
3. **NGINX** reverse proxy for handling traffic to the Spring Boot application.
4. **Spring Boot application** deployed on **EC2 instances** or **ECS**.
5. **Helm** used for managing Kubernetes resources (if you decide to go the Kubernetes route).

### **Project Breakdown**

#### 1. **Infrastructure Setup with Terraform:**

- **VPC (Virtual Private Cloud):**
    - Create a custom VPC with public and private subnets.
    - Attach an **Internet Gateway** for public access.
    - Set up **NAT Gateway** for private subnets (if using private EC2 instances).

- **Security Groups and Networking:**
    - Create **security groups** to control access (allow HTTP/HTTPS from the internet, allow communication between EC2 instances and NGINX).

- **Application Load Balancer (ALB):**
    - Set up an **ALB** to distribute traffic to your EC2 instances running the Spring Boot application.

- **Auto Scaling Group**:
    - Configure **auto-scaling** to handle traffic spikes by automatically adding/removing EC2 instances.

#### 2. **Spring Boot Application Deployment:**

- **EC2 Instances**:
    - Provision **EC2 instances** (or **ECS instances** if you go the containerized route) to run the Spring Boot application.
    - Deploy the Spring Boot application on EC2 instances using **user-data** or a configuration management tool like **Ansible**, **Chef**, or **Docker**.

- Alternatively, you can use **AWS Elastic Beanstalk** for easier management of your Spring Boot application, but doing it manually with EC2 will help you learn more.

#### 3. **NGINX Setup:**

- **NGINX** will act as a reverse proxy in front of the Spring Boot application to route incoming traffic.
- **NGINX on EC2 instances** can forward requests from the ALB to the appropriate Spring Boot app.

#### 4. **Helm (Optional for Kubernetes):**
- If you want to add Kubernetes into the mix, you can use **EKS (Elastic Kubernetes Service)** or set up a Kubernetes cluster on EC2 instances.
- Use **Helm** to install the Spring Boot application and NGINX.

Alternatively, you can skip Kubernetes for simplicity and focus on AWS EC2 deployment for now.

### **Step-by-Step Terraform and App Deployment**

#### **Step 1: Setup Your Terraform Project**

1. **Initialize Terraform Project**:
    - Create a directory for your project and inside that directory, initialize a new Terraform project.
      ```bash
      mkdir terraform-springboot-project
      cd terraform-springboot-project
      terraform init
      ```

2. **Create Terraform Configuration Files**:
   You’ll organize your Terraform code into different files:
    - **main.tf** – Main resources (VPC, Subnets, Security Groups, ALB, etc.)
    - **variables.tf** – Define your variables (like instance types, region, etc.)
    - **outputs.tf** – Define output values for things like public IP or DNS of your resources.

#### **Step 2: Define Your VPC and Subnets**

In **main.tf**, define the VPC and subnets.

```hcl
resource "aws_vpc" "main" {
  cidr_block = var.vpc_cidr_block
  enable_dns_support = true
  enable_dns_hostnames = true
}

resource "aws_subnet" "public" {
  vpc_id     = aws_vpc.main.id
  cidr_block = var.public_subnet_cidr
  availability_zone = "us-east-1a"
  map_public_ip_on_launch = true
}

resource "aws_subnet" "private" {
  vpc_id     = aws_vpc.main.id
  cidr_block = var.private_subnet_cidr
  availability_zone = "us-east-1b"
}

resource "aws_internet_gateway" "gw" {
  vpc_id = aws_vpc.main.id
}
```

#### **Step 3: Configure Security Groups**

Create security groups to manage access to your EC2 instances and ALB.

```hcl
resource "aws_security_group" "sg_alb" {
  name        = "alb-sg"
  description = "Allow inbound traffic to ALB"
  vpc_id      = aws_vpc.main.id

  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_security_group" "sg_app" {
  name        = "app-sg"
  description = "Allow inbound traffic to app instances"
  vpc_id      = aws_vpc.main.id

  ingress {
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    security_groups = [aws_security_group.sg_alb.id]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}
```

#### **Step 4: Setup Application Load Balancer (ALB)**

Configure the ALB to route traffic to your EC2 instances.

```hcl
resource "aws_lb" "app_lb" {
  name               = "app-lb"
  internal           = false
  load_balancer_type = "application"
  security_groups   = [aws_security_group.sg_alb.id]
  subnets            = [aws_subnet.public.id]

  enable_deletion_protection = false
}

resource "aws_lb_target_group" "app_target" {
  name     = "app-target"
  port     = 8080
  protocol = "HTTP"
  vpc_id   = aws_vpc.main.id
}
```

#### **Step 5: Deploy EC2 Instances for Spring Boot App**

Create EC2 instances to run the Spring Boot app.

```hcl
resource "aws_instance" "app_instance" {
  ami             = var.ami_id
  instance_type   = "t2.micro"
  subnet_id       = aws_subnet.private.id
  security_groups = [aws_security_group.sg_app.id]
  user_data       = file("user_data.sh")

  tags = {
    Name = "Spring Boot App"
  }
}
```

You’ll use `user_data.sh` to install your Spring Boot app on EC2 and run it as a service. Example of `user_data.sh`:

```bash
#!/bin/bash
yum update -y
yum install java -y
aws s3 cp s3://your-bucket/springboot-app.jar /home/ec2-user/app.jar
nohup java -jar /home/ec2-user/app.jar &
```

#### **Step 6: Install NGINX (Reverse Proxy)**

You can either run NGINX on an EC2 instance or use Helm to deploy it on Kubernetes if you go that route. For simplicity, let’s deploy NGINX on an EC2 instance.

```hcl
resource "aws_instance" "nginx_instance" {
  ami             = var.ami_id
  instance_type   = "t2.micro"
  subnet_id       = aws_subnet.public.id
  security_groups = [aws_security_group.sg_app.id]
  user_data       = file("nginx_user_data.sh")

  tags = {
    Name = "NGINX Proxy"
  }
}
```

`nginx_user_data.sh`:

```bash
#!/bin/bash
yum update -y
yum install nginx -y
service nginx start
echo "server {
  listen 80;
  location / {
    proxy_pass http://<spring_boot_ip>:8080;
  }
}" > /etc/nginx/nginx.conf
service nginx restart
```

#### **Step 7: Apply Terraform Configuration**

Run the following Terraform commands to provision the infrastructure:

```bash
terraform plan
terraform apply
```

#### **Step 8: Deploy Spring Boot Application**

Ensure your Spring Boot application is deployed and accessible via the ALB and NGINX reverse proxy.

---

### **Optional Step (Helm for Kubernetes)**

If you want to work with **Helm** and **Kubernetes** for better container orchestration:

1. **Set up an EKS Cluster** using Terraform.
2. Use **Helm** to deploy your Spring Boot app on Kubernetes.
3. Install **NGINX Ingress Controller** using Helm.

---

### **Conclusion:**

By the end of this project, you'll have gained hands-on experience in:

- Setting up a **VPC**, **subnets**, **security groups**, **ALB** with Terraform.
- Deploying and managing **Spring Boot** applications on **EC2** instances.
- Configuring **NGINX** as a reverse proxy.
- Optionally, integrating **

Kubernetes** and **Helm** for deployment and management.