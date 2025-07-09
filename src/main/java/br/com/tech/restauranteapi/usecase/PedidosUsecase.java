package br.com.tech.restauranteapi.usecase;

import br.com.tech.restauranteapi.controller.dtos.CriarPedidoDto;
import br.com.tech.restauranteapi.controller.dtos.PedidoDto;
import br.com.tech.restauranteapi.controller.dtos.PedidoResponseDto;
import br.com.tech.restauranteapi.gateway.domain.CriarPedido;
import br.com.tech.restauranteapi.gateway.domain.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PedidosUsecase {
    Pedido realizarCheckout(CriarPedido dto);
    Page<Pedido> listarFilaPedidos(Pageable pageable);
}
