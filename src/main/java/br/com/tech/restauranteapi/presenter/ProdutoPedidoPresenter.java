package br.com.tech.restauranteapi.presenter;

import br.com.tech.restauranteapi.controller.dtos.ProdutoPedidoDto;
import br.com.tech.restauranteapi.domain.ProdutoPedido;

public class ProdutoPedidoPresenter {
    public static ProdutoPedido toDomain(ProdutoPedidoDto dto) {
        return ProdutoPedido
                .builder()
                .produtoId(dto.getProdutoId())
                .quantidade(dto.getQuantidade())
                .build();
    }
}