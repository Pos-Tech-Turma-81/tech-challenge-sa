
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

- Na **ETAPA 1**, o checkout será uma **simulação (fake checkout)** apenas para fins de fluxo.

---

### 🔄 Fila de Pedidos
Organização dos pedidos em espera no sistema, com tempo de entrada e status de avanço no processo de preparação.

---

### 🧰 Infraestrutura

- A aplicação será entregue como um **monolito**

---

### 🛠️ APIs do Sistema

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

- Docker
- Maven

### Passos

1. Clone o repositório:
```bash
git clone https://github.com/eusoumabel/tech-challenge-sa.git
cd tech-challenge-sa
```

2. Builda e gera o JAR:
```bash
mvn clean package -DskipTests
```

3. Gera a imagem docker:
```bash
docker build -t restaurante-app .
```

4. Navega para `/infra`:
```bash
cd infra
```

5. Gera os Containers:
```bash
docker-compose up
```

6. Acesse a API (Swagger):
```
http://localhost:8080/swagger-ui/index.html#/
```
---
