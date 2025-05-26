package br.com.tech.restauranteapi.associacaoPedidoProduto.dominio;

import br.com.tech.restauranteapi.associacaoPedidoProduto.dominio.dtos.AssociacaoPedidoProdutoDto;
import br.com.tech.restauranteapi.associacaoPedidoProduto.infraestrutura.entidades.AssociacaoPedidoProdutoEntity;
import br.com.tech.restauranteapi.pedidos.dominio.Pedido;
import br.com.tech.restauranteapi.pedidos.infraestrutura.entidades.AssociacaoPedidoProdutoId;
import br.com.tech.restauranteapi.produtos.dominio.Produto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssociacaoPedidoProduto {
    private Integer quantidade;
    private Pedido pedido;
    private BigDecimal preco;
    private Produto produto;


    public static AssociacaoPedidoProduto builderAssociacao(AssociacaoPedidoProdutoEntity entity) {
        return AssociacaoPedidoProduto.builder()
                .pedido(entity.getId().getPedido().toPedidosDomain())
                .produto(entity.getId().getProduto().toProdutoDomain())
                .quantidade(entity.getQuantidade())
                .preco(entity.getPreco())
                .produto(Produto.builderProduto(entity.getId().getProduto()))
                .build();
    }

    public AssociacaoPedidoProdutoDto toDto() {
        return AssociacaoPedidoProdutoDto.builder()
                .pedidoId(this.pedido.getId())
                .produtoId(this.produto.getId())
                .quantidade(this.quantidade)
                .preco(this.preco)
                .build();
    }

    public AssociacaoPedidoProdutoEntity toEntity() {
        return AssociacaoPedidoProdutoEntity.builder()

                .id(new AssociacaoPedidoProdutoId(this.pedido.toEntity(), this.produto.toEntity()))
                .quantidade(this.quantidade)
                .preco(this.preco)
                .build();
    }

}
