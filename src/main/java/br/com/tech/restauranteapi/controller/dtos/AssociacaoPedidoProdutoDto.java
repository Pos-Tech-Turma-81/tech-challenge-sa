package br.com.tech.restauranteapi.controller.dtos;

import br.com.tech.restauranteapi.domain.AssociacaoPedidoProduto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
public class AssociacaoPedidoProdutoDto {

    private Integer pedidoId;
    private Integer produtoId;
    private Integer quantidade;
    private BigDecimal preco;

    public AssociacaoPedidoProdutoDto(Integer pedidoId, Integer produtoId, Integer quantidade, BigDecimal preco) {
        this.pedidoId = pedidoId;
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.preco = preco;
    }

}
