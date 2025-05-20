package br.com.tech.restauranteapi.associacaoPedidoProduto.dominio;

import br.com.tech.restauranteapi.associacaoPedidoProduto.dominio.dtos.AssociacaoPedidoProdutoDto;
import br.com.tech.restauranteapi.associacaoPedidoProduto.infraestrutura.entidades.AssociacaoPedidoProdutoEntity;
import br.com.tech.restauranteapi.pedidos.infraestrutura.entidades.AssociacaoPedidoProdutoId;
import br.com.tech.restauranteapi.produtos.dominio.Produto;
import br.com.tech.restauranteapi.produtos.infraestrutura.entidades.ProdutoEntity;
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
    private Integer pedidoId;
    private Integer produtoId;
    private Integer quantidade;
    private BigDecimal preco;
    private Produto produto;

    public static AssociacaoPedidoProduto builderAssociacao(AssociacaoPedidoProdutoDto dto) {
        return AssociacaoPedidoProduto.builder()
                .pedidoId(dto.getPedidoId())
                .produtoId(dto.getProdutoId())
                .quantidade(dto.getQuantidade())
                .preco(dto.getPreco())
                .build();
    }

    public static AssociacaoPedidoProduto builderAssociacao(AssociacaoPedidoProdutoEntity entity) {
        return AssociacaoPedidoProduto.builder()
                .pedidoId(entity.getId().getPedidoId())
                .produtoId(entity.getId().getProdutosId())
                .quantidade(entity.getQuantidade())
                .preco(entity.getPreco())
                .produto(entity.getProduto() != null ? Produto.builderProduto(entity.getProduto()) : null)
                .build();
    }

    public AssociacaoPedidoProdutoDto toDto() {
        return AssociacaoPedidoProdutoDto.builder()
                .pedidoId(this.pedidoId)
                .produtoId(this.produtoId)
                .quantidade(this.quantidade)
                .preco(this.preco)
                .build();
    }

    public AssociacaoPedidoProdutoEntity toEntity() {
        return AssociacaoPedidoProdutoEntity.builder()
                .id(new AssociacaoPedidoProdutoId(this.pedidoId, this.produtoId))
                .quantidade(this.quantidade)
                .preco(this.preco)
                .produto(this.produto != null ? this.produto.toEntity() : null)
                .build();
    }
}
