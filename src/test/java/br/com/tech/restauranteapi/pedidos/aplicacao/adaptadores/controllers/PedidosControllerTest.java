package br.com.tech.restauranteapi.pedidos.aplicacao.adaptadores.controllers;

import br.com.tech.restauranteapi.pedidos.dominio.dtos.*;
import br.com.tech.restauranteapi.pedidos.dominio.portas.interfaces.PedidosServicePort;
import br.com.tech.restauranteapi.utils.enums.StatusEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class PedidosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PedidosServicePort pedidosService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve criar um pedido com sucesso")
    void deveCriarPedidoComSucesso() throws Exception {
        CriarPedidoDto dto = new CriarPedidoDto();
        dto.setClienteId(1);
        dto.setProdutos(List.of(
                new ProdutoPedidoDto(1, 2)
        ));

        PedidoResponseDto response = PedidoResponseDto.builder()
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

        Mockito.when(pedidosService.realizarCheckout(Mockito.any())).thenReturn(response);

        mockMvc.perform(post("/pedidos/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pedidoId").value(1))
                .andExpect(jsonPath("$.clienteId").value(1))
                .andExpect(jsonPath("$.status").value(StatusEnum.EM_PREPARACAO.toString()));
    }

    @Test
    @DisplayName("Deve criar um pedido sem cliente com sucesso")
    void deveCriarPedidoSemClienteComSucesso() throws Exception {
        CriarPedidoDto dto = new CriarPedidoDto();
        dto.setClienteId(null);
        dto.setProdutos(List.of(
                new ProdutoPedidoDto(1, 2)
        ));

        PedidoResponseDto response = PedidoResponseDto.builder()
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

        Mockito.when(pedidosService.realizarCheckout(Mockito.any())).thenReturn(response);

        mockMvc.perform(post("/pedidos/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pedidoId").value(2))
                .andExpect(jsonPath("$.clienteId").doesNotExist())
                .andExpect(jsonPath("$.status").value(StatusEnum.EM_PREPARACAO.toString()));
    }


    @Test
    @DisplayName("Deve retornar lista de pedidos com status AGUARDANDO")
    void deveListarPedidosAguardando() throws Exception {
        PedidoDto pedido = PedidoDto.builder()
                .id(1)
                .status(StatusEnum.EM_PREPARACAO)
                .build();

        // Mock do service com filtro de status
        Mockito.when(pedidosService.listarFilaPedidos(any()))
                .thenReturn(List.of(pedido));

        mockMvc.perform(get("/pedidos/fila")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].status").value(StatusEnum.EM_PREPARACAO.toString()));
    }
}
