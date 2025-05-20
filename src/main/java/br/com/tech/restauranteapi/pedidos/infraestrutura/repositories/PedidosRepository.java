package br.com.tech.restauranteapi.pedidos.infraestrutura.repositories;

import br.com.tech.restauranteapi.pedidos.dominio.Pedidos;
import br.com.tech.restauranteapi.pedidos.dominio.portas.repositories.PedidosRepositoryPort;
import br.com.tech.restauranteapi.pedidos.infraestrutura.entidades.PedidosEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

public class PedidosRepository implements PedidosRepositoryPort {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Pedidos salvar(Pedidos pedidos) {
        PedidosEntity entity = pedidos.toEntity();
        entityManager.persist(entity);
        return entity.toPedidosDomain();
    }

    @Override
    public List<Pedidos> listarFilaPedidos() {
        String jpql = "SELECT p FROM PedidosEntity p";
        return entityManager.createQuery(jpql, PedidosEntity.class)
                .getResultList()
                .stream()
                .map(PedidosEntity::toPedidosDomain)
                .collect(Collectors.toList());
    }

}
