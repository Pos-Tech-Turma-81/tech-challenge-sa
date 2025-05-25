package br.com.tech.restauranteapi.pedidos.infraestrutura.repositories;

import br.com.tech.restauranteapi.pedidos.dominio.Pedidos;
import br.com.tech.restauranteapi.pedidos.dominio.portas.repositories.PedidosRepositoryPort;
import br.com.tech.restauranteapi.pedidos.infraestrutura.entidades.PedidosEntity;
import br.com.tech.restauranteapi.utils.enums.StatusEnum;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PedidosRepository implements PedidosRepositoryPort {
    @PersistenceContext
    private EntityManager entityManager;

    private final SpringPedidosRepository springDataPedidosRepository;

    public PedidosRepository(EntityManager entityManager, SpringPedidosRepository springDataPedidosRepository) {
        this.entityManager = entityManager;
        this.springDataPedidosRepository = springDataPedidosRepository;
    }

    @Override
    @Transactional
    public Pedidos salvar(Pedidos pedidos) {
        PedidosEntity entity = pedidos.toEntity();
        entityManager.persist(entity);
        return entity.toPedidosDomain();
    }

    @Override
    public List<Pedidos> listarPedidos(StatusEnum status, Integer clienteId) {

        List<PedidosEntity> entities = springDataPedidosRepository.listarPedidos(status, clienteId);

        return entities.stream()
                .map(PedidosEntity::toPedidosDomain)
                .collect(Collectors.toList());
    }
}
