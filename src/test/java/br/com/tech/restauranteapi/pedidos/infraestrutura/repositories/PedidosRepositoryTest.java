package br.com.tech.restauranteapi.pedidos.infraestrutura.repositories;

import br.com.tech.restauranteapi.clientes.dominio.Cliente;
import br.com.tech.restauranteapi.pedidos.dominio.Pedido;
import br.com.tech.restauranteapi.pedidos.infraestrutura.entidades.PedidoEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PedidosRepositoryTest {

    private EntityManager entityManager;
    private SpringPedidosRepository springPedidosRepository;
    private PedidosRepository pedidosRepository;

    @BeforeEach
    void setUp() {
        entityManager = mock(EntityManager.class);
        springPedidosRepository = mock(SpringPedidosRepository.class);
        pedidosRepository = new PedidosRepository(entityManager, springPedidosRepository);
    }

    @Test
    void deveSalvarPedidoComSucesso() {
        Pedido pedido = new Pedido();
        pedido.setStatus("AGUARDANDO");
        pedido.setDataHoraInclusaoPedido(Timestamp.from(Instant.now()));

        doNothing().when(entityManager).persist(any(PedidosEntity.class));

        // Testa salvar
        Pedido salvo = pedidosRepository.salvar(pedido);

        assertNotNull(salvo);
        assertEquals(StatusEnum.AGUARDANDO, salvo.getStatus());
        verify(entityManager, times(1)).persist(any(PedidosEntity.class));
    }

    @Test
    void deveListarPedidosUsandoSpringData() {
        PedidosEntity entity = new PedidosEntity();
        entity.setStatus(StatusEnum.AGUARDANDO);

        // Mock do TypedQuery<PedidosEntity>
        TypedQuery<PedidoEntity> typedQuery = mock(TypedQuery.class);

        // Mock do PedidosEntity retornado
        PedidoEntity pedidoEntityMock = mock(PedidoEntity.class);

        // Cria mocks dos objetos necessários para construir Pedidos
        Cliente clienteMock = mock(Cliente.class);

        // Mocka o toPedidosDomain para retornar um objeto Pedidos válido
        when(pedidoEntityMock.toPedidosDomain()).thenReturn(
                new Pedido(1, clienteMock, "AGUARDANDO", Timestamp.from(Instant.now()), null)
        );

        List<PedidoEntity> listaMock = List.of(pedidoEntityMock);

        when(entityManager.createQuery(jpql, PedidoEntity.class)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(listaMock);

        // Executa o método a ser testado
        List<Pedido> pedidos = pedidosRepository.listarFilaPedidos();

        // Verificações
        assertNotNull(pedidos);
        assertFalse(pedidos.isEmpty());
        assertEquals("AGUARDANDO", pedidos.get(0).getStatus());

        verify(entityManager, times(1)).createQuery(jpql, PedidoEntity.class);
        verify(typedQuery, times(1)).getResultList();
        verify(pedidoEntityMock, times(1)).toPedidosDomain();
    }
}
