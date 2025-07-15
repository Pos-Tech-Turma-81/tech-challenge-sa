package br.com.tech.restauranteapi.gateway;

import br.com.tech.restauranteapi.domain.Pedido;
import br.com.tech.restauranteapi.entity.PedidoEntity;
import br.com.tech.restauranteapi.repository.SpringPedidoRepository;
import br.com.tech.restauranteapi.utils.enums.StatusEnum;
import br.com.tech.restauranteapi.gateway.impl.PedidosGatewayImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PedidosGatewayImplTest {

    @Mock
    private SpringPedidoRepository repository;

    @InjectMocks
    private PedidosGatewayImpl gateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("Salvar pedido")
    class SalvarPedido {

        @Test
        @DisplayName("Deve salvar pedido com sucesso")
        void deveSalvarPedidoComSucesso() {
            Pedido pedido = mock(Pedido.class);
            PedidoEntity pedidoEntity = mock(PedidoEntity.class);
            PedidoEntity savedEntity = mock(PedidoEntity.class);
            Pedido expectedPedido = mock(Pedido.class);

            when(pedido.toEntity()).thenReturn(pedidoEntity);
            when(repository.save(pedidoEntity)).thenReturn(savedEntity);
            when(savedEntity.toPedidosDomain()).thenReturn(expectedPedido);

            Pedido result = gateway.salvar(pedido);

            assertEquals(expectedPedido, result);
            verify(repository).save(pedidoEntity);
        }
    }

    @Nested
    @DisplayName("Listar fila de pedidos")
    class ListarFilaPedidos {

        @Test
        @DisplayName("Deve listar pedidos em preparação com sucesso")
        void deveListarPedidosEmPreparacaoComSucesso() {
            Pageable pageable = mock(Pageable.class);
            Page<PedidoEntity> pedidoEntities = mock(Page.class);
            Page<Pedido> expectedPedidos = mock(Page.class);

            when(repository.getByPedidosPreparacao(StatusEnum.EM_PREPARACAO, pageable)).thenReturn(pedidoEntities);
            when(pedidoEntities.map(any(java.util.function.Function.class))).thenReturn(expectedPedidos);

            Page<Pedido> result = gateway.listarFilaPedidos(pageable);

            assertEquals(expectedPedidos, result);
        }
    }

    @Nested
    @DisplayName("Buscar pedido por ID")
    class BuscarPedidoPorId {

        @Test
        @DisplayName("Deve retornar pedido quando encontrado")
        void deveRetornarPedidoQuandoEncontrado() {
            Integer id = 1;
            PedidoEntity pedidoEntity = mock(PedidoEntity.class);
            Pedido expectedPedido = mock(Pedido.class);

            when(repository.findById(id)).thenReturn(Optional.of(pedidoEntity));
            when(pedidoEntity.toPedidosDomain()).thenReturn(expectedPedido);

            Pedido result = gateway.buscarPorId(id);

            assertEquals(expectedPedido, result);
        }

        @Test
        @DisplayName("Deve retornar nulo quando pedido não encontrado")
        void deveRetornarNuloQuandoPedidoNaoEncontrado() {
            Integer id = 1;

            when(repository.findById(id)).thenReturn(Optional.empty());

            Pedido result = gateway.buscarPorId(id);

            assertNull(result);
        }
    }

    @Nested
    @DisplayName("Atualizar pedido")
    class AtualizarPedido {

        @Test
        @DisplayName("Deve atualizar pedido com sucesso")
        void deveAtualizarPedidoComSucesso() {
            Pedido pedido = mock(Pedido.class);
            PedidoEntity pedidoEntity = mock(PedidoEntity.class);
            PedidoEntity updatedEntity = mock(PedidoEntity.class);
            Pedido expectedPedido = mock(Pedido.class);

            when(pedido.toEntity()).thenReturn(pedidoEntity);
            when(repository.save(pedidoEntity)).thenReturn(updatedEntity);
            when(updatedEntity.toPedidosDomain()).thenReturn(expectedPedido);

            Pedido result = gateway.atualizar(pedido);

            assertEquals(expectedPedido, result);
            verify(repository).save(pedidoEntity);
        }
    }
}