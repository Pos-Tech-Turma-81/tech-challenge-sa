package br.com.tech.restauranteapi.pedidos.dominio.portas.repositories;

import br.com.tech.restauranteapi.pedidos.dominio.Pedidos;
import br.com.tech.restauranteapi.utils.enums.StatusEnum;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PedidosRepositoryPort {
    Pedidos salvar(Pedidos pedidos);
    List<Pedidos> listarPedidos(StatusEnum status, Integer clienteId);
}
