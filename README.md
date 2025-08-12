
# 🍔 Tech Challenge – Documentação do Projeto

---

## 📌 Introdução do Problema

O sistema visa digitalizar o processo de pedidos em uma rede de fast food por meio de uma **aplicação de autoatendimento**. Os clientes poderão montar seus pedidos, visualizar o andamento e retirá-los no balcão assim que estiverem prontos.

### Objetivos:

- Reduzir o tempo de atendimento.
- Diminuir erros operacionais.
- Automatizar a visualização de pedidos em tempo real.
- Permitir integração futura com métodos de pagamento.

---

## ⚡ Inclusão do Event Storming

Durante o processo de modelagem, utilizamos a técnica de **Event Storming** para mapear os principais eventos, comandos e agregados do domínio.

### Atores:
- `Cliente`
- `Atendente`
- `Cozinha`
- `Estabelecimento`

### Agregados:
- `Pedido`
- `Cliente`
- `Pagamento`
- `Cozinha`
- `Atendente`


### Eventos mapeados:
![image](https://github.com/user-attachments/assets/80170ea2-8e02-43ff-9c03-e02acd4da80f)

### Event Storming - Etapa 1 (Organizando os Eventos):
![image](https://github.com/user-attachments/assets/30b10d1d-80c6-45c2-9e1b-b0a0551218a3)

### Event Storming - Etapa 2 (Adição dos Eventos Pivotais):
 ![image](https://github.com/user-attachments/assets/2960a73d-13dc-4727-a70b-1526f2b0896d)

### Event Storming - Etapa 3 (Fluxo Completo):
![image](https://github.com/user-attachments/assets/97f17e0e-6aa6-4b2a-a5dc-7b66303fe099)

### Event Storming - Etapa 4 (Fluxo Verticalizado):
![image](https://github.com/user-attachments/assets/b5f77fd2-60bd-4829-ab3d-e7f2eab7ae4a)


O Event Storming permitiu visualizar o fluxo completo de domínio e estabelecer os **bounded contexts** com clareza.


> 📌 **Event Storming**: É possível acessar o Event Storming no Miro [aqui](https://miro.com/app/board/uXjVI_TRUv8=/?share_link_id=237320070737).

---

## 📖 Dicionário de Linguagem Ubíqua

Este dicionário define os principais termos usados no domínio da aplicação para garantir comunicação clara entre todos os envolvidos.

---

### 🧑‍🍳 Cliente
Pessoa que acessa o sistema de autoatendimento para realizar um pedido.

- Pode ou não se identificar com **CPF**, **nome** e **e-mail**.
- Pode montar um **combo** com os itens desejados.

---

### 🧾 Pedido
Conjunto de itens selecionados pelo cliente, podendo conter:

1. **Lanche**  
2. **Acompanhamento**  
3. **Bebida**  
4. **Sobremesa**

- O pedido segue por um fluxo de **status** (Recebido → Em preparação → Pronto → Finalizado).
- Só entra na cozinha após a **confirmação do pagamento**.

---

### 🍔 Combo
Conjunto opcional de produtos do pedido (lanche, acompanhamento, bebida, sobremesa), montado pelo cliente.

---

### 💳 Pagamento
Processo de finalização do pedido.

---

### 🧑‍🍳 Cozinha
Responsável por **preparar os pedidos** assim que forem confirmados.

- Recebe os pedidos em tempo real após pagamento.
- Atualiza os status de preparo.

---

### 📺 Monitor de Acompanhamento
Tela (física ou digital) onde o cliente visualiza o **progresso do pedido**.

Status possíveis:

- Recebido  
- Em preparação  
- Pronto  
- Finalizado

---

### 🛎️ Retirada
Etapa final do pedido. O sistema **notifica o cliente** quando o pedido está **pronto para retirada**.

---

### 🧑‍💼 Painel Administrativo
Interface usada pelo estabelecimento para **gestão interna**, com as seguintes funcionalidades:

- Gerenciar clientes
- Gerenciar produtos e categorias
- Acompanhar pedidos em andamento
- Visualizar tempo de espera

---

### 🛍️ Produto
Item disponível para escolha do cliente, com:

- Nome
- Categoria (Lanche, Acompanhamento, Bebida, Sobremesa)
- Preço
- Descrição
- Imagem

---

### 🗂️ Categoria
Classificação fixa de produtos no sistema:

- **Lanche**
- **Acompanhamento**
- **Bebida**
- **Sobremesa**

---

### 🧑‍💻 Identificação
Processo opcional onde o cliente informa **CPF**, **nome** e **e-mail**.  
Permite o estabelecimento:

- Reconhecer clientes frequentes
- Criar campanhas promocionais futuras

---

### 📦 Checkout
Ato de **finalizar e confirmar o pedido**, enviando-o para a fila da cozinha.

- Pagamento realizado via **QRCode** através do **Mercado Pago**.

---

### 🔄 Fila de Pedidos
Organização dos pedidos em espera no sistema, com tempo de entrada e status de avanço no processo de preparação.

---

### 🧰 Infraestrutura

- A aplicação será entregue como um **monolito**

---
## 📚 Requisitos do Sistema

### Requisitos de Negócio
- Cadastro e login de clientes (opcional).
- Criação de pedidos personalizados: lanche, acompanhamento, bebida e sobremesa.
- Pagamento por QRCode (Mercado Pago).
- Acompanhamento dos pedidos.
- Painel administrativo com:
  - Gerenciamento de produtos por categoria.
  - Acompanhamento de pedidos.
- APIs RESTful seguindo padrões Clean Code + Clean Architecture.

### Requisitos de Infraestrutura
- Orquestração com Kubernetes (K8s) utilizando Minikube.
- Suporte à escalabilidade com Horizontal Pod Autoscaler (HPA).
- Deploys via Deployments + Services.
- Armazenamento de configurações com ConfigMaps.
- Armazenamento de dados sensíveis com Secrets.

### ☁️ Kubernetes – Componentes Utilizados

| Componente   | Descrição                                               |
|--------------|---------------------------------------------------------|
| `Deployment` | Garante replicação e atualização do pod                 |
| `Service`    | Exposição interna dos pods                              |
| `HPA`        | Escalabilidade automática com base em CPU               |
| `ConfigMaps` | Parametrizações e variáveis não sensíveis               |
| `Secrets`    | Armazenamento de tokens/segredos (ex: API Mercado Pago) |
| `Volume`     | Persistência de dados                              |


---

### Desenho da Arquitetura

<img width="709" height="901" alt="docs drawio" src="https://github.com/user-attachments/assets/5a27616f-9da8-4569-ba2b-20f478af3564" />


---

## 🛠️ Tecnologias e Ferramentas

- **Spring Boot**: Backend REST
- **JPA / Hibernate**: Persistência
- **PostgreSQL**: Banco de dados
- **Docker**: Contêiner da aplicação
- **Minikube (Kubernetes)**: Orquestração
- **Swagger**: Documentação das APIs
- **Mercado Pago**: Gateway de pagamento (via QR Code)

---

## 🛠️ APIs do Sistema

- Cadastro do Cliente  
- Identificação via CPF  
- CRUD de Produtos  
- Busca de produtos por categoria  
- Fake checkout  
- Listagem de pedidos

---

### 🧪 Swagger

Documentação interativa das APIs REST disponibilizadas no backend.

---

> 📌 **Importância**: Essa linguagem ubíqua será utilizada nos eventos de Event Storming, modelagem tática e implementação do sistema, evitando ambiguidades e ruídos de comunicação.

---

## ▶️ Como Rodar o Projeto

### Pré-requisitos

- Kubernetes 
- Minikube
- Docker
- Maven

### Passos

#### 1. Clone o Repositório
```bash
git clone https://github.com/eusoumabel/tech-challenge-sa.git
cd tech-challenge-sa
```

#### 2. Inicie o Docker

Abra o aplicativo do Docker:

```bash
open -a Docker
```

#### 3. Acesse o Diretório de Configuração do Kubernetes
```bash
cd infra/kubernetes
```

#### 4. Inicie o Minikube

Se for a primeira vez utilizando:
```bash
minikube start --driver=docker
```

Se o Minikube já estiver configurado anteriormente:
```bash
minikube start
```

#### 5. Suba os Recursos do Projeto

5.1. Suba o banco de dados PostgreSQL:
```bash
kubectl apply -f postgress
```

5.2. Crie os segredos da aplicação:

Abra o arquivo **infra/kubernetes/criar_secrets.txt**, copie todo o conteúdo e cole no terminal para executar os comandos de criação dos secrets.

5.3. Suba a aplicação principal:
```bash
kubectl apply -f restaurante-app
```

#### 6. Verifique o Status dos Pods
```bash
kubectl get pods
```
Aguarde até que ambos os Pods estejam com o status Ready (ex: 1/1).

#### 7. Obtenha o IP do Minikube
```bash
minikube ip
```

#### 8. Acesse a API via Swagger (Linux)

Acesse no navegador:

```bash
http://192.168.49.2:31000/swagger-ui/index.html#/
```

> ⚠️ Todos os passos foram realizados com sucesso utilizando o Linux. Para Windows e Mac, é necessários os passos abaixo:

#### 9. Acesse a API via Swagger (Windows e Mac)

9.1. Redirecionar a porta do Minikube para o localhost:

```bash
kubectl port-forward svc/svc-restaurante-app 8080:8080
```

9.2. Acesse no navegador:

```bash
http://localhost:8080/swagger-ui/index.html#/
```

---
