package br.com.tech.restauranteapi.domain;

import br.com.tech.restauranteapi.controller.dtos.AssociacaoPedidoProdutoDto;
import br.com.tech.restauranteapi.entity.AssociacaoPedidoProdutoEntity;
import br.com.tech.restauranteapi.entity.id.AssociacaoPedidoProdutoId;
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
}
