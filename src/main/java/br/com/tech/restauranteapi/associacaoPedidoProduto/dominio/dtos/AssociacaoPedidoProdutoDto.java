package br.com.tech.restauranteapi.associacaoPedidoProduto.dominio.dtos;

import br.com.tech.restauranteapi.associacaoPedidoProduto.dominio.AssociacaoPedidoProduto;
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
    private Double preco;

    public static AssociacaoPedidoProdutoDto builderAssociacao(AssociacaoPedidoProduto associacao) {
        return AssociacaoPedidoProdutoDto.builder()
                .pedidoId(associacao.getPedidoId())
                .produtoId(associacao.getProdutoId())
                .quantidade(associacao.getQuantidade())
                .preco(associacao.getPreco())
                .build();
    }
}
