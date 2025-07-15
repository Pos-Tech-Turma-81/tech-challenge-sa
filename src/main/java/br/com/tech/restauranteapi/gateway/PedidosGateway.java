package br.com.tech.restauranteapi.gateway;

import br.com.tech.restauranteapi.domain.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PedidosGateway {
    Pedido salvar(Pedido pedidos);
    Page<Pedido> listarFilaPedidos(Pageable page);
    Pedido buscarPorId(Integer id);
    Pedido atualizar(Pedido pedido);
}
