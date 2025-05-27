package br.com.tech.restauranteapi.pedidos.dominio.portas.interfaces;

import br.com.tech.restauranteapi.pedidos.dominio.dtos.CriarPedidoDto;
import br.com.tech.restauranteapi.pedidos.dominio.dtos.PedidoDto;
import br.com.tech.restauranteapi.pedidos.dominio.dtos.PedidoResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PedidosServicePort {
    PedidoResponseDto realizarCheckout(CriarPedidoDto dto);
    Page<PedidoDto> listarFilaPedidos(Pageable pageable);
}
