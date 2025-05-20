package br.com.tech.restauranteapi.pedidos.dominio.portas.interfaces;

import br.com.tech.restauranteapi.pedidos.dominio.dtos.CriarPedidoDto;
import br.com.tech.restauranteapi.pedidos.dominio.dtos.PedidoResponseDto;
import br.com.tech.restauranteapi.pedidos.dominio.dtos.PedidosDto;

import java.util.List;
import java.util.Optional;

public interface PedidosServicePort {
    PedidoResponseDto realizarCheckout(CriarPedidoDto dto);
    List<PedidosDto> listarFilaPedidos();
}
