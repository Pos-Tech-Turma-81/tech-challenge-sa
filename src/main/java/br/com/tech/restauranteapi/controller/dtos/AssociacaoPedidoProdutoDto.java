package br.com.tech.restauranteapi.controller.dtos;

import br.com.tech.restauranteapi.domain.AssociacaoPedidoProduto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssociacaoPedidoProdutoDto {

    private Integer pedidoId;
    private Integer produtoId;
    private Integer quantidade;
    private BigDecimal preco;

    public static AssociacaoPedidoProdutoDto builderAssociacao(AssociacaoPedidoProduto associacao) {
        return AssociacaoPedidoProdutoDto.builder()
                .pedidoId(associacao.getPedido().getId())
                .produtoId(associacao.getProduto().getId())
                .quantidade(associacao.getQuantidade())
                .preco(associacao.getPreco())
                .build();
    }
}
