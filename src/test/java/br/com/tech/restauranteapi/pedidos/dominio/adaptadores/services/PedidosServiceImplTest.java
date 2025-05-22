package br.com.tech.restauranteapi.pedidos.dominio.adaptadores.services;

import br.com.tech.restauranteapi.associacaoPedidoProduto.dominio.AssociacaoPedidoProduto;
import br.com.tech.restauranteapi.associacaoPedidoProduto.dominio.portas.interfaces.AssociacaoPedidoProdutoServicePort;
import br.com.tech.restauranteapi.pedidos.dominio.Pedidos;
import br.com.tech.restauranteapi.pedidos.dominio.dtos.CriarPedidoDto;
import br.com.tech.restauranteapi.pedidos.dominio.dtos.ProdutoPedidoDto;
import br.com.tech.restauranteapi.pedidos.infraestrutura.repositories.PedidosRepository;
import br.com.tech.restauranteapi.utils.enums.StatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PedidosServiceImplTest {

    @Mock
    private PedidosRepository pedidosRepository;

    @Mock
    private AssociacaoPedidoProdutoServicePort associacaoService;

    @InjectMocks
    private PedidosServiceImpl pedidosService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveRealizarCheckoutComSucesso() {
        // Arrange
        CriarPedidoDto dto = CriarPedidoDto.builder()
                .clienteId(1)
                .produtos(List.of(
                        ProdutoPedidoDto.builder()
                                .produtoId(10)
                                .quantidade(2)
                                .preco(BigDecimal.valueOf(19.90))
                                .build()
                ))
                .build();

        Pedidos pedidoSalvo = new Pedidos();
        pedidoSalvo.setId(100);
        pedidoSalvo.setCliente(dto.getClienteId() != null ? new br.com.tech.restauranteapi.clientes.dominio.Cliente(dto.getClienteId(), null, null, null, null, null) : null);
        pedidoSalvo.setStatus(StatusEnum.AGUARDANDO);
        pedidoSalvo.setDataHoraInclusaoPedido(Timestamp.from(Instant.now()));

        when(pedidosRepository.salvar(any())).thenReturn(pedidoSalvo);

        // Act
        var response = pedidosService.realizarCheckout(dto);

        // Assert
        assertNotNull(response);
        assertEquals(100, response.getPedidoId());
        assertEquals(StatusEnum.AGUARDANDO, response.getStatus());
        assertEquals(1, response.getClienteId());
        assertEquals(1, response.getProdutos().size());
        assertEquals(10, response.getProdutos().get(0).getProdutoId());
        assertEquals(2, response.getProdutos().get(0).getQuantidade());

        verify(pedidosRepository, times(1)).salvar(any());
        verify(associacaoService, times(1)).salvar(any());
    }
}
