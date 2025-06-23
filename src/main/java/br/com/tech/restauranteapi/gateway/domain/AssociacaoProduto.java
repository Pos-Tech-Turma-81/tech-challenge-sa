package br.com.tech.restauranteapi.gateway.domain;

import br.com.tech.restauranteapi.controller.dtos.AssociacaoPedidoProdutoDto;
import br.com.tech.restauranteapi.gateway.entity.AssociacaoPedidoProdutoEntity;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AssociacaoProduto {
    private Integer quantidade;
    private BigDecimal preco;
    private Produto produto;

    public static AssociacaoProduto builderAssociacao(AssociacaoPedidoProdutoEntity entity) {
        return AssociacaoProduto.builder()
                .produto(entity.getId().getProduto().toProdutoDomain())
                .quantidade(entity.getQuantidade())
                .preco(entity.getPreco())
                .produto(Produto.builderProduto(entity.getId().getProduto()))
                .build();
    }

    public static AssociacaoProduto builderAssociacao(AssociacaoPedidoProduto domain) {
        return AssociacaoProduto.builder()
                .produto(domain.getProduto())
                .quantidade(domain.getQuantidade())
                .preco(domain.getPreco())
                .build();
    }

    public AssociacaoPedidoProdutoDto toDto() {
        return AssociacaoPedidoProdutoDto.builder()
                .produtoId(this.produto.getId())
                .quantidade(this.quantidade)
                .preco(this.preco)
                .build();
    }

}
