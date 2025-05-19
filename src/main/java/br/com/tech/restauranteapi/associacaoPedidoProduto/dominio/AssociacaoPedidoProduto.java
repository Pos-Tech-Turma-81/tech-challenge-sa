package br.com.tech.restauranteapi.associacaoPedidoProduto.dominio;

import br.com.tech.restauranteapi.associacaoPedidoProduto.dominio.dtos.AssociacaoPedidoProdutoDto;
import br.com.tech.restauranteapi.associacaoPedidoProduto.infraestrutura.entidades.AssociacaoPedidoProdutoEntity;
import br.com.tech.restauranteapi.pedidos.infraestrutura.entidades.AssociacaoPedidoProdutoId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssociacaoPedidoProduto {
    private Integer pedidoId;
    private Integer produtoId;
    private Integer quantidade;
    private Double preco;

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
                .build();
    }
}
