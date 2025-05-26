package br.com.tech.restauranteapi.pedidos.dominio.portas.repositories;

import br.com.tech.restauranteapi.pedidos.dominio.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PedidosRepositoryPort {
    Pedido salvar(Pedido pedidos);
    Page<Pedido> listarFilaPedidos(Pageable page);
}
