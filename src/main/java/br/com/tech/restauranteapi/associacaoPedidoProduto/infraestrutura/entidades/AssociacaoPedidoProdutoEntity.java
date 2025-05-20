package br.com.tech.restauranteapi.associacaoPedidoProduto.infraestrutura.entidades;

import br.com.tech.restauranteapi.pedidos.infraestrutura.entidades.AssociacaoPedidoProdutoId;
import br.com.tech.restauranteapi.pedidos.infraestrutura.entidades.PedidosEntity;
import br.com.tech.restauranteapi.produtos.infraestrutura.entidades.ProdutoEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "associacao_pedido_produto")
public class AssociacaoPedidoProdutoEntity {

    @EmbeddedId
    private AssociacaoPedidoProdutoId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("pedidoId")
    @JoinColumn(name = "pedido_id", referencedColumnName = "id")
    private PedidosEntity pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("produtosId")
    @JoinColumn(name = "produtos_id", referencedColumnName = "id")
    private ProdutoEntity produto;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @Column(name = "preco", nullable = false)
    private BigDecimal preco;
}
