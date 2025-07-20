package br.com.tech.restauranteapi.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AssociacaoProduto {
    private Integer quantidade;
    private BigDecimal preco;
    private Produto produto;
    private Pedido pedido;

    public AssociacaoProduto(Integer quantidade, BigDecimal preco, Produto produto,  Pedido pedido) {
        this.quantidade = quantidade;
        this.preco = preco;
        this.produto = produto;
        this.pedido = pedido;
    }
}