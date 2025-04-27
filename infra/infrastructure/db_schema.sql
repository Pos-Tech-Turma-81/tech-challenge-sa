CREATE TABLE Clientes (
    id SERIAL,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    telefone VARCHAR(20),
    cpf VARCHAR(14),
    endereco TEXT,
    PRIMARY KEY (id)
);

CREATE TABLE Produtos (
    id SERIAL,
    nome VARCHAR(255) NOT NULL,
    categoria_id INT,
    preco DECIMAL(10, 2) NOT NULL,
    descricao TEXT,
    imagem TEXT,
    PRIMARY KEY (id)
);

CREATE TABLE Pedidos (
    id SERIAL,
    cliente_id INT NOT NULL,
    status VARCHAR(50),
    data_hora_inclusao_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (cliente_id) REFERENCES Clientes(id)
);

CREATE TABLE Pagamentos (
    id SERIAL,
    pedido_id INT NOT NULL,
    valor DECIMAL(10, 2) NOT NULL,
    id_mercado_pago VARCHAR(255),
    status VARCHAR(50),
    PRIMARY KEY (id),
    FOREIGN KEY (pedido_id) REFERENCES Pedidos(id)
);

CREATE TABLE Associacao_Pedido_Produto (
    pedido_id INT NOT NULL,
    produtos_id INT NOT NULL,
    quantidade INT NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (pedido_id, produtos_id),
    FOREIGN KEY (pedido_id) REFERENCES Pedidos(id),
    FOREIGN KEY (produtos_id) REFERENCES Produtos(id)
);