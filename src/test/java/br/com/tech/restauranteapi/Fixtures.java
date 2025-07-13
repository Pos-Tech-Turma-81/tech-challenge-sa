package br.com.tech.restauranteapi;

import br.com.tech.restauranteapi.gateway.domain.Cliente;
import br.com.tech.restauranteapi.gateway.entity.AssociacaoPedidoProdutoEntity;
import br.com.tech.restauranteapi.gateway.entity.ClienteEntity;
import br.com.tech.restauranteapi.gateway.entity.PedidoEntity;
import br.com.tech.restauranteapi.gateway.entity.ProdutoEntity;
import br.com.tech.restauranteapi.gateway.entity.id.AssociacaoPedidoProdutoId;
import br.com.tech.restauranteapi.utils.enums.CategoriaEnum;
import br.com.tech.restauranteapi.utils.enums.StatusEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Fixtures {
    public static final ClienteEntity mockClienteEntity = ClienteEntity.builder()
            .id(2)
            .nome("Maria")
            .email("maria@email.com")
            .telefone("123456789")
            .cpf("123.456.789-00")
            .endereco("Rua X, 123")
            .build();

    public static final ProdutoEntity mockProdutoEntity = ProdutoEntity.builder()
            .id(3)
            .nome("Coca-Cola")
            .categoria(CategoriaEnum.BEBIDA)
            .preco(new BigDecimal("7.50"))
            .descricao("Refrigerante 350ml")
            .imagem(null)
            .build();

    public static final PedidoEntity mockPedidoEntity = PedidoEntity.builder()
            .id(1)
            .clientId(mockClienteEntity)
            .status(StatusEnum.RECEBIDO)
            .dataHoraInclusaoPedido(LocalDateTime.now())
            .associacoes(List.of())
            .build();

    public static final AssociacaoPedidoProdutoId mockId = AssociacaoPedidoProdutoId.builder()
            .produto(mockProdutoEntity)
            .pedido(mockPedidoEntity)
            .build();

    public static final AssociacaoPedidoProdutoEntity mockAssociacaoPedidoProdutoEntity = AssociacaoPedidoProdutoEntity.builder()
            .id(mockId)
            .quantidade(2)
            .preco(new BigDecimal("10"))
            .build();

    public static final Cliente mockCliente = new Cliente(
            1,
            "Maria",
            "maria@email.com",
            "123456789",
            "12345678900",
            "Rua X, 123");
}
