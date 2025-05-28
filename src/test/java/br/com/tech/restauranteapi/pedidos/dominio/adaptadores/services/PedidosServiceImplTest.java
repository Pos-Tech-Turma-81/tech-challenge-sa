package br.com.tech.restauranteapi.pedidos.dominio.adaptadores.services;

import br.com.tech.restauranteapi.associacaoPedidoProduto.dominio.AssociacaoPedidoProduto;
import br.com.tech.restauranteapi.associacaoPedidoProduto.dominio.portas.interfaces.AssociacaoPedidoProdutoServicePort;
import br.com.tech.restauranteapi.clientes.dominio.Cliente;
import br.com.tech.restauranteapi.clientes.dominio.portas.repositories.ClienteRepositoryPort;
import br.com.tech.restauranteapi.pedidos.dominio.Pedido;
import br.com.tech.restauranteapi.pedidos.dominio.dtos.CriarPedidoDto;
import br.com.tech.restauranteapi.pedidos.dominio.dtos.ProdutoPedidoDto;
import br.com.tech.restauranteapi.pedidos.infraestrutura.repositories.PedidosRepository;
import br.com.tech.restauranteapi.produtos.dominio.Produto;
import br.com.tech.restauranteapi.produtos.infraestrutura.repositories.ProdutoRepository;
import br.com.tech.restauranteapi.utils.enums.StatusEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidosServiceImplTest {

    @InjectMocks
    private PedidosServiceImpl pedidosService;

    @Mock
    private PedidosRepository pedidosRepository;

    @Mock
    private AssociacaoPedidoProdutoServicePort associacaoService;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ClienteRepositoryPort clienteRepository;


    @Test
    void deveRealizarCheckoutComSucesso() {
        // Mock do Produto que será retornado pelo produtoRepository
        Produto produtoMock = Produto.builder()
                .id(10)
                .nome("Produto Teste")
                .preco(new BigDecimal("50.00"))
                .build();

        Cliente cliente = Cliente.builder().id(1).build();
        List<AssociacaoPedidoProduto> ass = List.of(
                AssociacaoPedidoProduto
                        .builder()
                        .pedido(Pedido.builder()
                                .id(1)
                                .build()
                        )
                        .produto(produtoMock)
                        .quantidade(2)
                        .build()
        );

        // Configura mock do produtoRepository para retornar o produtoMock quando buscarPorId(10) for chamado
        when(produtoRepository.buscarPorId(10)).thenReturn(produtoMock);
        when(clienteRepository.findById(any())).thenReturn(cliente);
        when(associacaoService.salvarTodas(any())).thenReturn(ass);

        CriarPedidoDto dto = CriarPedidoDto.builder()
                .clienteId(1)
                .produtos(List.of(
                        ProdutoPedidoDto.builder()
                                .produtoId(10)
                                .quantidade(2)
                                .build()
                ))
                .build();

        Pedido pedidoSalvo = new Pedido();
        pedidoSalvo.setId(100);
        pedidoSalvo.setCliente(dto.getClienteId() != null ? new br.com.tech.restauranteapi.clientes.dominio.Cliente(dto.getClienteId(), null, null, null, null, null) : null);
        pedidoSalvo.setStatus(StatusEnum.EM_PREPARACAO);

        when(pedidosRepository.salvar(any())).thenReturn(pedidoSalvo);

        var response = pedidosService.realizarCheckout(dto);

        assertNotNull(response);
        assertEquals(100, response.getPedidoId());
        assertEquals(StatusEnum.EM_PREPARACAO, response.getStatus());
        assertEquals(1, response.getClienteId());
        assertEquals(10, response.getProdutos().get(0).getProdutoId());
        assertEquals(2, response.getProdutos().get(0).getQuantidade());

        verify(pedidosRepository, times(1)).salvar(any());
        verify(associacaoService, times(1)).salvarTodas(any());
        verify(produtoRepository, times(1)).buscarPorId(10);  // verifica se buscou o produto
    }

    @Test
    void deveRealizarCheckoutComProdutoDuplicados() {
        // Mock do Produto que será retornado pelo produtoRepository
        Produto produtoMock = Produto.builder()
                .id(10)
                .nome("Produto Teste")
                .preco(new BigDecimal("50.00"))
                .build();

        Cliente cliente = Cliente.builder().id(1).build();
        List<AssociacaoPedidoProduto> ass = List.of(
                AssociacaoPedidoProduto
                        .builder()
                        .pedido(Pedido.builder()
                                .id(1)
                                .build()
                        )
                        .produto(produtoMock)
                        .quantidade(3)
                        .preco(new BigDecimal(30))
                        .build()
        );

        // Configura mock do produtoRepository para retornar o produtoMock quando buscarPorId(10) for chamado
        when(produtoRepository.buscarPorId(10)).thenReturn(produtoMock);
        when(clienteRepository.findById(any())).thenReturn(cliente);
        when(associacaoService.salvarTodas(any())).thenReturn(ass);

        CriarPedidoDto dto = CriarPedidoDto.builder()
                .clienteId(1)
                .produtos(List.of(
                        ProdutoPedidoDto.builder()
                                .produtoId(10)
                                .quantidade(2)
                                .build(),
                        ProdutoPedidoDto.builder()
                                .produtoId(10)
                                .quantidade(1)
                                .build()
                ))
                .build();

        Pedido pedidoSalvo = new Pedido();
        pedidoSalvo.setId(100);
        pedidoSalvo.setCliente(dto.getClienteId() != null ? new br.com.tech.restauranteapi.clientes.dominio.Cliente(dto.getClienteId(), null, null, null, null, null) : null);
        pedidoSalvo.setStatus(StatusEnum.EM_PREPARACAO);

        when(pedidosRepository.salvar(any())).thenReturn(pedidoSalvo);

        var response = pedidosService.realizarCheckout(dto);

        assertNotNull(response);
        assertEquals(100, response.getPedidoId());
        assertEquals(StatusEnum.EM_PREPARACAO, response.getStatus());
        assertEquals(1, response.getClienteId());
        assertEquals(10, response.getProdutos().get(0).getProdutoId());
        assertEquals(3, response.getProdutos().get(0).getQuantidade());
        assertEquals(new BigDecimal(30), response.getProdutos().get(0).getPreco());

        verify(pedidosRepository, times(1)).salvar(any());
        verify(associacaoService, times(1)).salvarTodas(any());
        verify(produtoRepository, times(1)).buscarPorId(10);  // verifica se buscou o produto
    }

    @Test
    void deveRealizarCheckoutComSucessoSemCliente() {
        // Mock do Produto que será retornado pelo produtoRepository
        Produto produtoMock = Produto.builder()
                .id(10)
                .nome("Produto Teste")
                .preco(new BigDecimal("50.00"))
                .build();

        Cliente cliente = Cliente.builder().id(1).build();
        List<AssociacaoPedidoProduto> ass = List.of(
                AssociacaoPedidoProduto
                        .builder()
                        .pedido(Pedido.builder()
                                .id(1)
                                .build()
                        )
                        .produto(produtoMock)
                        .quantidade(2)
                        .build()
        );

        // Configura mock do produtoRepository para retornar o produtoMock quando buscarPorId(10) for chamado
        when(produtoRepository.buscarPorId(10)).thenReturn(produtoMock);
        when(associacaoService.salvarTodas(any())).thenReturn(ass);

        CriarPedidoDto dto = CriarPedidoDto.builder()
                .produtos(List.of(
                        ProdutoPedidoDto.builder()
                                .produtoId(10)
                                .quantidade(2)
                                .build()
                ))
                .build();

        Pedido pedidoSalvo = new Pedido();
        pedidoSalvo.setId(100);
        pedidoSalvo.setCliente(dto.getClienteId() != null ? new br.com.tech.restauranteapi.clientes.dominio.Cliente(dto.getClienteId(), null, null, null, null, null) : null);
        pedidoSalvo.setStatus(StatusEnum.EM_PREPARACAO);

        when(pedidosRepository.salvar(any())).thenReturn(pedidoSalvo);

        var response = pedidosService.realizarCheckout(dto);

        assertNotNull(response);
        assertEquals(100, response.getPedidoId());
        assertEquals(StatusEnum.EM_PREPARACAO, response.getStatus());
        assertNull(response.getClienteId());
        assertEquals(10, response.getProdutos().get(0).getProdutoId());
        assertEquals(2, response.getProdutos().get(0).getQuantidade());

        verify(pedidosRepository, times(1)).salvar(any());
        verify(associacaoService, times(1)).salvarTodas(any());
        verify(produtoRepository, times(1)).buscarPorId(10);  // verifica se buscou o produto
    }
}
