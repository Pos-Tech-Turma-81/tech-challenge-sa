package br.com.tech.restauranteapi.associacaoPedidoProduto.infraestrutura.entidades;

import br.com.tech.restauranteapi.pedidos.infraestrutura.entidades.AssociacaoPedidoProdutoId;
import br.com.tech.restauranteapi.pedidos.infraestrutura.entidades.PedidoEntity;
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


    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @Column(name = "preco", nullable = false)
    private BigDecimal preco;
}
