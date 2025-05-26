package br.com.tech.restauranteapi.pedidos.dominio.portas.interfaces;

import br.com.tech.restauranteapi.pedidos.dominio.dtos.CriarPedidoDto;
import br.com.tech.restauranteapi.pedidos.dominio.dtos.PedidoResponseDto;
import br.com.tech.restauranteapi.pedidos.dominio.dtos.PedidoDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PedidosServicePort {
    PedidoResponseDto realizarCheckout(CriarPedidoDto dto);
    List<PedidoDto> listarFilaPedidos(Pageable pageable);
}
