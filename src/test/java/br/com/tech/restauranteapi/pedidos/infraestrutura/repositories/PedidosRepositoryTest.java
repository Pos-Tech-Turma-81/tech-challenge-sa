package br.com.tech.restauranteapi.pedidos.infraestrutura.repositories;

import br.com.tech.restauranteapi.clientes.dominio.Cliente;
import br.com.tech.restauranteapi.pedidos.dominio.Pedidos;
import br.com.tech.restauranteapi.pedidos.infraestrutura.entidades.PedidosEntity;
import br.com.tech.restauranteapi.pedidos.infraestrutura.repositories.PedidosRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

class PedidosRepositoryTest {

    private EntityManager entityManager;
    private PedidosRepository pedidosRepository;

    @BeforeEach
    void setUp() {
        entityManager = mock(EntityManager.class);
        pedidosRepository = new PedidosRepository(entityManager);
    }

    @Test
    void deveSalvarPedidoComSucesso() {
        Pedidos pedido = new Pedidos();
        pedido.setStatus("AGUARDANDO");
        pedido.setDataHoraInclusaoPedido(Timestamp.from(Instant.now()));

        // Mocka o comportamento do entityManager.persist
        doNothing().when(entityManager).persist(any());

        // Testa salvar
        Pedidos salvo = pedidosRepository.salvar(pedido);

        assertNotNull(salvo);
        assertEquals("AGUARDANDO", salvo.getStatus());
        verify(entityManager, times(1)).persist(any());
    }

    @Test
    void deveListarPedidosComSucesso() {
        String jpql = "SELECT p FROM PedidosEntity p";

        // Mock do TypedQuery<PedidosEntity>
        TypedQuery<PedidosEntity> typedQuery = mock(TypedQuery.class);

        // Mock do PedidosEntity retornado
        PedidosEntity pedidosEntityMock = mock(PedidosEntity.class);

        // Cria mocks dos objetos necessários para construir Pedidos
        Cliente clienteMock = mock(Cliente.class);

        // Mocka o toPedidosDomain para retornar um objeto Pedidos válido
        when(pedidosEntityMock.toPedidosDomain()).thenReturn(
                new Pedidos(1, clienteMock, "AGUARDANDO", Timestamp.from(Instant.now()), null)
        );

        List<PedidosEntity> listaMock = List.of(pedidosEntityMock);

        when(entityManager.createQuery(jpql, PedidosEntity.class)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(listaMock);

        // Executa o método a ser testado
        List<Pedidos> pedidos = pedidosRepository.listarFilaPedidos();

        // Verificações
        assertNotNull(pedidos);
        assertFalse(pedidos.isEmpty());
        assertEquals("AGUARDANDO", pedidos.get(0).getStatus());

        verify(entityManager, times(1)).createQuery(jpql, PedidosEntity.class);
        verify(typedQuery, times(1)).getResultList();
        verify(pedidosEntityMock, times(1)).toPedidosDomain();
    }
}
