package br.com.tech.restauranteapi.pedidos.dominio.portas.interfaces;

import br.com.tech.restauranteapi.pedidos.dominio.dtos.CriarPedidoDto;
import br.com.tech.restauranteapi.pedidos.dominio.dtos.PedidoResponseDto;
import br.com.tech.restauranteapi.pedidos.dominio.dtos.PedidoDto;

import java.util.List;

public interface PedidosServicePort {
    PedidoResponseDto realizarCheckout(CriarPedidoDto dto);
    List<PedidoDto> listarFilaPedidos();
}
