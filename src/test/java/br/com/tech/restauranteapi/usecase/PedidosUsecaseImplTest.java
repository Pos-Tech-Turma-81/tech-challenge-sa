package br.com.tech.restauranteapi.usecase;

import br.com.tech.restauranteapi.gateway.ClienteGateway;
import br.com.tech.restauranteapi.gateway.ProdutoGateway;
import br.com.tech.restauranteapi.gateway.domain.*;
import br.com.tech.restauranteapi.gateway.impl.PedidosGatewayImpl;
import br.com.tech.restauranteapi.usecase.impl.PedidosUsecaseImpl;
import br.com.tech.restauranteapi.utils.enums.StatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PedidosUsecaseImplTest {

    @Mock
    private PedidosGatewayImpl pedidosRepository;
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
    void testRealizarCheckout_WithClienteAndMultipleProducts() {
        // Arrange
        Integer clienteId = 1;
        Integer produtoId1 = 10;
        Integer produtoId2 = 20;
        BigDecimal preco1 = new BigDecimal("5.00");
        BigDecimal preco2 = new BigDecimal("7.00");

        Produto produto1 = Produto.builder().id(produtoId1).preco(preco1).build();
        Produto produto2 = Produto.builder().id(produtoId2).preco(preco2).build();

        ProdutoPedido produtoPedido1 = ProdutoPedido.builder().produtoId(produtoId1).quantidade(2).build();
        ProdutoPedido produtoPedido2 = ProdutoPedido.builder().produtoId(produtoId2).quantidade(1).build();

        CriarPedido criarPedido = CriarPedido.builder()
                .clienteId(clienteId)
                .produtos(List.of(produtoPedido1, produtoPedido2))
                .build();

        Cliente cliente = new Cliente(clienteId, "Maria", "maria@email.com", "123456789", "12345678900", "Rua X, 123");
        Pedido pedido = new Pedido();
        Pedido pedidoSalvo = new Pedido();
        pedidoSalvo.setId(100);
        pedidoSalvo.setStatus(StatusEnum.EM_PREPARACAO);

        when(clienteGateway.buscarPorId(clienteId)).thenReturn(cliente);
        when(pedidosRepository.salvar(any(Pedido.class))).thenReturn(pedidoSalvo);
        when(produtoGateway.buscarPorId(produtoId1)).thenReturn(produto1);
        when(produtoGateway.buscarPorId(produtoId2)).thenReturn(produto2);

        // Prepare association objects
        AssociacaoPedidoProduto assoc1 = AssociacaoPedidoProduto.builder()
                .produto(produto1)
                .pedido(pedidoSalvo)
                .quantidade(2)
                .preco(preco1.multiply(BigDecimal.valueOf(2)))
                .build();
        AssociacaoPedidoProduto assoc2 = AssociacaoPedidoProduto.builder()
                .produto(produto2)
                .pedido(pedidoSalvo)
                .quantidade(1)
                .preco(preco2.multiply(BigDecimal.valueOf(1)))
                .build();

        when(associacaoPedidoProdutoUsecase.salvarTodas(anyList()))
                .thenReturn(List.of(assoc1, assoc2));

        // Act
        Pedido result = usecase.realizarCheckout(criarPedido);

        // Assert
        assertNotNull(result);
        assertEquals(100, result.getId());
        assertEquals(StatusEnum.EM_PREPARACAO, result.getStatus());
        assertNotNull(result.getAssociacoes());
        assertEquals(2, result.getAssociacoes().size());
        verify(clienteGateway).buscarPorId(clienteId);
        verify(pedidosRepository).salvar(any(Pedido.class));
        verify(produtoGateway).buscarPorId(produtoId1);
        verify(produtoGateway).buscarPorId(produtoId2);
        verify(associacaoPedidoProdutoUsecase).salvarTodas(anyList());
    }

    @Test
    void testRealizarCheckout_WithoutCliente() {
        // Arrange
        Integer produtoId = 10;
        BigDecimal preco = new BigDecimal("5.00");
        Produto produto = Produto.builder().id(produtoId).preco(preco).build();
        ProdutoPedido produtoPedido = ProdutoPedido.builder().produtoId(produtoId).quantidade(1).build();

        CriarPedido criarPedido = CriarPedido.builder()
                .clienteId(null)
                .produtos(List.of(produtoPedido))
                .build();

        Pedido pedidoSalvo = new Pedido();
        pedidoSalvo.setId(200);
        pedidoSalvo.setStatus(StatusEnum.EM_PREPARACAO);

        when(pedidosRepository.salvar(any(Pedido.class))).thenReturn(pedidoSalvo);
        when(produtoGateway.buscarPorId(produtoId)).thenReturn(produto);

        AssociacaoPedidoProduto assoc = AssociacaoPedidoProduto.builder()
                .produto(produto)
                .pedido(pedidoSalvo)
                .quantidade(1)
                .preco(preco)
                .build();

        when(associacaoPedidoProdutoUsecase.salvarTodas(anyList()))
                .thenReturn(List.of(assoc));

        // Act
        Pedido result = usecase.realizarCheckout(criarPedido);

        // Assert
        assertNotNull(result);
        assertEquals(200, result.getId());
        assertEquals(StatusEnum.EM_PREPARACAO, result.getStatus());
        assertNotNull(result.getAssociacoes());
        assertEquals(1, result.getAssociacoes().size());
        verify(clienteGateway, never()).buscarPorId(any());
        verify(pedidosRepository).salvar(any(Pedido.class));
        verify(produtoGateway).buscarPorId(produtoId);
        verify(associacaoPedidoProdutoUsecase).salvarTodas(anyList());
    }

    @Test
    void testListarFilaPedidos() {
        // Arrange
        Pedido pedido1 = new Pedido();
        pedido1.setId(1);
        Pedido pedido2 = new Pedido();
        pedido2.setId(2);
        Page<Pedido> page = new PageImpl<>(List.of(pedido1, pedido2), PageRequest.of(0, 10), 2);
        when(pedidosRepository.listarFilaPedidos(any())).thenReturn(page);

        // Act
        Page<Pedido> result = usecase.listarFilaPedidos(PageRequest.of(0, 10));

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        verify(pedidosRepository).listarFilaPedidos(any());
    }
}
