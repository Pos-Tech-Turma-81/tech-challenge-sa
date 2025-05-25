package br.com.tech.restauranteapi.pedidos.infraestrutura.repositories;

import br.com.tech.restauranteapi.pedidos.dominio.Pedidos;
import br.com.tech.restauranteapi.pedidos.infraestrutura.entidades.PedidosEntity;
import br.com.tech.restauranteapi.utils.enums.StatusEnum;
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
        Pedidos pedido = new Pedidos();
        pedido.setStatus(StatusEnum.AGUARDANDO);

        doNothing().when(entityManager).persist(any(PedidosEntity.class));

        Pedidos salvo = pedidosRepository.salvar(pedido);

        assertNotNull(salvo);
        assertEquals(StatusEnum.AGUARDANDO, salvo.getStatus());
        verify(entityManager, times(1)).persist(any(PedidosEntity.class));
    }

    @Test
    void deveListarPedidosUsandoSpringData() {
        PedidosEntity entity = new PedidosEntity();
        entity.setStatus(StatusEnum.AGUARDANDO);

        List<PedidosEntity> listaEntities = List.of(entity);

        when(springPedidosRepository.listarPedidos(
                eq(StatusEnum.AGUARDANDO),
                eq(123))
        ).thenReturn(listaEntities);

        List<Pedidos> resultado = pedidosRepository.listarPedidos(
                StatusEnum.AGUARDANDO,
                123
        );

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(StatusEnum.AGUARDANDO, resultado.get(0).getStatus());

        verify(springPedidosRepository, times(1)).listarPedidos(
                eq(StatusEnum.AGUARDANDO),
                eq(123));
    }
}
