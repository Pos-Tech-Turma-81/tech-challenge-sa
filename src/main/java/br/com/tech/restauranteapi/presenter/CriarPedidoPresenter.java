package br.com.tech.restauranteapi.presenter;

import br.com.tech.restauranteapi.controller.dtos.CriarPedidoDto;
import br.com.tech.restauranteapi.domain.CriarPedido;

public class CriarPedidoPresenter {
    public static CriarPedido toDomain(CriarPedidoDto dto) {
        return CriarPedido
                .builder()
                .clienteId(dto.getClienteId())
                .produtos(dto.getProdutos().stream()
                        .map(ProdutoPedidoPresenter::toDomain)
                        .collect(java.util.stream.Collectors.toList()))
                .build();
    }
}