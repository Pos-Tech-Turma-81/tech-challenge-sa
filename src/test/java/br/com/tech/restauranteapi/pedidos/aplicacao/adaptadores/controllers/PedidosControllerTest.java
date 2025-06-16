package br.com.tech.restauranteapi.pedidos.aplicacao.adaptadores.controllers;

import br.com.tech.restauranteapi.controller.PedidosController;
import br.com.tech.restauranteapi.controller.dtos.*;
import br.com.tech.restauranteapi.usecase.PedidosUsecase;
import br.com.tech.restauranteapi.utils.enums.StatusEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@ExtendWith(MockitoExtension.class)
class PedidosControllerTest {

    @InjectMocks
    private PedidosController controller;

    @Mock
    private PedidosUsecase pedidosService;


    @Test
    @DisplayName("Deve criar um pedido com sucesso")
    void deveCriarPedidoComSucesso() throws Exception {
        CriarPedidoDto dto = new CriarPedidoDto();
        dto.setClienteId(1);
        dto.setProdutos(List.of(
                new ProdutoPedidoDto(1, 2)
        ));

        PedidoResponseDto pedidoResponse = PedidoResponseDto.builder()
                .pedidoId(1)
                .clienteId(1)
                .status(StatusEnum.EM_PREPARACAO)
                .produtos(List.of(
                        ProdutoPedidoResponseDto.builder()
                                .produtoId(1)
                                .quantidade(2)
                                .preco(new BigDecimal("10.50"))
                                .nomeProduto("Produto Teste")
                                .build()
                ))
                .build();

        Mockito.when(pedidosService.realizarCheckout(Mockito.any())).thenReturn(pedidoResponse);

        ResponseEntity<PedidoResponseDto> response =  controller.realizarCheckout(dto);

        assertEquals(1, response.getBody().getPedidoId());
        assertEquals(1, response.getBody().getClienteId());
        assertEquals(StatusEnum.EM_PREPARACAO, response.getBody().getStatus());

    }

    @Test
    @DisplayName("Deve criar um pedido sem cliente com sucesso")
    void deveCriarPedidoSemClienteComSucesso() throws Exception {
        CriarPedidoDto dto = new CriarPedidoDto();
        dto.setClienteId(null);
        dto.setProdutos(List.of(
                new ProdutoPedidoDto(1, 2)
        ));

        PedidoResponseDto pedidoResponse = PedidoResponseDto.builder()
                .pedidoId(2)
                .clienteId(null)
                .status(StatusEnum.EM_PREPARACAO)
                .produtos(List.of(
                        ProdutoPedidoResponseDto.builder()
                                .produtoId(1)
                                .quantidade(2)
                                .preco(new BigDecimal("10.50"))
                                .nomeProduto("Produto Teste")
                                .build()
                ))
                .build();

        Mockito.when(pedidosService.realizarCheckout(Mockito.any())).thenReturn(pedidoResponse);

        ResponseEntity<PedidoResponseDto> response =  controller.realizarCheckout(dto);

        assertEquals(2, response.getBody().getPedidoId());
        assertNull(response.getBody().getClienteId());
        assertEquals(StatusEnum.EM_PREPARACAO, response.getBody().getStatus());
    }


    @Test
    @DisplayName("Deve retornar lista de pedidos com status AGUARDANDO")
    void deveListarPedidosAguardando() throws Exception {
        PedidoDto pedido = PedidoDto.builder()
                .id(1)
                .status(StatusEnum.EM_PREPARACAO)
                .build();

        Pageable pageable = PageRequest.of(0, 10);
        Page<PedidoDto> page = new PageImpl<>(List.of(pedido), pageable, 1);

        // Mock do service com filtro de status
        Mockito.when(pedidosService.listarFilaPedidos(any()))
                .thenReturn(page);

        ResponseEntity<Page<PedidoDto>> response =  controller.listarFilaPedidos(PageRequest.of(0, 10));

        assertEquals(1, response.getBody().getContent().get(0).getId());
        assertEquals(StatusEnum.EM_PREPARACAO, response.getBody().getContent().get(0).getStatus());
    }
}
