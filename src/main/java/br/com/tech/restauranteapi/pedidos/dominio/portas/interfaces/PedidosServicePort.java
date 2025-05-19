package br.com.tech.restauranteapi.pedidos.dominio.portas.interfaces;

import br.com.tech.restauranteapi.pedidos.dominio.dtos.PedidosDto;

import java.util.List;
import java.util.Optional;

public interface PedidosServicePort {
    PedidosDto realizarCheckout(PedidosDto pedidoDto);
    List<PedidosDto> listarFilaPedidos();
    Optional<PedidosDto> obterProximoPedido();
    void finalizarPedido(Integer pedidoId);
}
