package br.com.tech.restauranteapi.pedidos.dominio.portas.repositories;

import br.com.tech.restauranteapi.pedidos.dominio.Pedidos;

import java.util.List;
import java.util.Optional;

public interface PedidosRepositoryPort {
    Pedidos salvar(Pedidos pedidos);
    List<Pedidos> listarFilaPedidos();
}
