package br.com.tech.restauranteapi.controller;

import br.com.tech.restauranteapi.controller.dtos.CriarPedidoDto;
import br.com.tech.restauranteapi.controller.dtos.PedidoDto;
import br.com.tech.restauranteapi.controller.dtos.PedidoResponseDto;
import br.com.tech.restauranteapi.controller.dtos.ProdutoPedidoResponseDto;
import br.com.tech.restauranteapi.gateway.domain.*;
import br.com.tech.restauranteapi.usecase.PedidosUsecase;
import br.com.tech.restauranteapi.utils.enums.StatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PedidosControllerTest {

    @Mock
    private PedidosUsecase pedidosService;

    @InjectMocks
    private PedidosController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRealizarCheckout_Success() {

        Produto produto = new Produto();
        produto.setId(10);
        produto.setNome("Pizza");

        AssociacaoProduto assoc = AssociacaoProduto.builderAssociacao(new AssociacaoPedidoProduto());
        assoc.setProduto(produto);
        assoc.setQuantidade(2);
        assoc.setPreco(BigDecimal.valueOf(35));

        Cliente cliente = new Cliente(5,"José","teste@email.com","11999996666","12345678900","ABC");

        Pedido pedido = new Pedido();
        pedido.setId(100);
        pedido.setCliente(cliente);
        pedido.setStatus(StatusEnum.RECEBIDO);
        pedido.setDataHoraInclusaoPedido(LocalDateTime.now());
        pedido.setAssociacoes(List.of(assoc));

        when(pedidosService.realizarCheckout(any())).thenReturn(pedido);

        CriarPedidoDto criarPedidoDto = new CriarPedidoDto();

        ResponseEntity<PedidoResponseDto> response = controller.realizarCheckout(criarPedidoDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(100, response.getBody().getPedidoId());
        assertEquals(5, response.getBody().getClienteId());
        assertEquals(StatusEnum.RECEBIDO, response.getBody().getStatus());
        assertNotNull(response.getBody().getProdutos());
        assertEquals(1, response.getBody().getProdutos().size());
        ProdutoPedidoResponseDto produtoDto = response.getBody().getProdutos().getFirst();
        assertEquals(10, produtoDto.getProdutoId());
        assertEquals("Pizza", produtoDto.getNomeProduto());
        assertEquals(2, produtoDto.getQuantidade());
        assertEquals(BigDecimal.valueOf(35), produtoDto.getPreco());
    }

    @Test
    void testListarFilaPedidos_Success() {
        Pedido pedido = new Pedido();
        pedido.setId(200);
        pedido.setStatus(StatusEnum.EM_PREPARACAO);

        Page<Pedido> page = new PageImpl<>(List.of(pedido), PageRequest.of(0, 10), 1);
        when(pedidosService.listarFilaPedidos(any())).thenReturn(page);

        ResponseEntity<Page<PedidoDto>> response = controller.listarFilaPedidos(PageRequest.of(0, 10));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getTotalElements());
    }
}
