package br.com.tech.restauranteapi.usecase;

import br.com.tech.restauranteapi.domain.*;
import br.com.tech.restauranteapi.utils.enums.StatusEnum;
import br.com.tech.restauranteapi.utils.enums.CategoriaEnum;
import br.com.tech.restauranteapi.domain.AssociacaoPedidoProduto;
import br.com.tech.restauranteapi.gateway.ClienteGateway;
import br.com.tech.restauranteapi.gateway.PedidosGateway;
import br.com.tech.restauranteapi.gateway.ProdutoGateway;
import br.com.tech.restauranteapi.usecase.impl.PedidosUsecaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class PedidosUsecaseImplTest {

    @Mock
    private PedidosGateway pedidosGateway;

    @Mock
    private ClienteGateway clienteGateway;

    @Mock
    private ProdutoGateway produtoGateway;

    @Mock
    private AssociacaoPedidoProdutoUsecase associacaoPedidoProdutoUsecase;

    @InjectMocks
    private PedidosUsecaseImpl usecase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve realizar checkout com sucesso")
    void shouldPerformCheckoutSuccessfully() {
        CriarPedido criarPedido = new CriarPedido(1, List.of(new ProdutoPedido(1, 2)));
        Cliente cliente = new Cliente(1, "12345678900", "João", "joao@email.com", "11999999999", "Rua A");
        Pedido pedidoSalvo = new Pedido(
                1,
                cliente,
                StatusEnum.EM_PREPARACAO,
                LocalDateTime.now(),
                List.of()
        );
        Produto produto = new Produto(
                1,
                "Produto A",
                CategoriaEnum.BEBIDA,
                BigDecimal.TEN,
                "desc",
                new byte[0]
        );

        when(clienteGateway.buscarPorId(1)).thenReturn(cliente);
        when(pedidosGateway.salvar(any(Pedido.class))).thenReturn(pedidoSalvo);
        when(produtoGateway.buscarPorId(1)).thenReturn(produto);
        when(associacaoPedidoProdutoUsecase.salvarTodas(anyList())).thenReturn(List.of(
                new AssociacaoPedidoProduto(1, pedidoSalvo, BigDecimal.valueOf(20), produto)
        ));

        Pedido result = usecase.realizarCheckout(criarPedido);

        assertEquals(pedidoSalvo, result);
        verify(pedidosGateway, times(1)).salvar(any(Pedido.class));
        verify(associacaoPedidoProdutoUsecase, times(1)).salvarTodas(anyList());
    }

    @Test
    @DisplayName("Deve listar fila de pedidos com sucesso")
    void shouldListOrderQueueSuccessfully() {
        Pageable pageable = Pageable.ofSize(10);
        Page<Pedido> pedidosPage = mock(Page.class);

        when(pedidosGateway.listarFilaPedidos(pageable)).thenReturn(pedidosPage);

        Page<Pedido> result = usecase.listarFilaPedidos(pageable);

        assertEquals(pedidosPage, result);
        verify(pedidosGateway, times(1)).listarFilaPedidos(pageable);
    }

    @Test
    @DisplayName("Deve atualizar status do pedido com sucesso")
    void shouldUpdateOrderStatusSuccessfully() {
        Cliente cliente = new Cliente(1, "12345678900", "João", "joao@email.com", "11999999999", "Rua A");
        Pedido pedido = new Pedido(
                1,
                cliente,
                StatusEnum.EM_PREPARACAO,
                LocalDateTime.now(),
                List.of()
        );

        when(pedidosGateway.buscarPorId(1)).thenReturn(pedido);
        when(pedidosGateway.atualizar(any(Pedido.class))).thenReturn(pedido);

        Pedido result = usecase.atualizarStatus(1, StatusEnum.PRONTO);

        assertEquals(StatusEnum.PRONTO, result.getStatus());
        verify(pedidosGateway, times(1)).buscarPorId(1);
        verify(pedidosGateway, times(1)).atualizar(any(Pedido.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar status de pedido inexistente")
    void shouldThrowExceptionWhenUpdatingStatusOfNonExistentOrder() {
        when(pedidosGateway.buscarPorId(999)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> usecase.atualizarStatus(999, StatusEnum.PRONTO));
        verify(pedidosGateway, times(1)).buscarPorId(999);
        verify(pedidosGateway, never()).atualizar(any(Pedido.class));
    }
}