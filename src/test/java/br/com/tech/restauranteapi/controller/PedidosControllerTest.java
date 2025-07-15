package br.com.tech.restauranteapi.controller;

import br.com.tech.restauranteapi.controller.dtos.AtualizarStatusPedidoDto;
import br.com.tech.restauranteapi.controller.dtos.CriarPedidoDto;
import br.com.tech.restauranteapi.controller.dtos.PedidoDto;
import br.com.tech.restauranteapi.controller.dtos.PedidoResponseDto;
import br.com.tech.restauranteapi.controller.dtos.ProdutoPedidoDto;
import br.com.tech.restauranteapi.domain.Pedido;
import br.com.tech.restauranteapi.domain.AssociacaoProduto;
import br.com.tech.restauranteapi.domain.Produto;
import br.com.tech.restauranteapi.usecase.PedidosUsecase;
import br.com.tech.restauranteapi.utils.enums.StatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PedidosControllerTest {

    @Mock
    private PedidosUsecase pedidosService;

    @InjectMocks
    private PedidosController pedidosController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("Realizar checkout")
    class RealizarCheckout {

        @Test
        @DisplayName("Deve criar pedido com sucesso")
        void deveCriarPedidoComSucesso() {
            CriarPedidoDto criarPedidoDto = CriarPedidoDto.builder()
                    .clienteId(1)
                    .produtos(List.of(new ProdutoPedidoDto(1, 2)))
                    .build();

            AssociacaoProduto associacaoProduto = AssociacaoProduto.builder()
                    .quantidade(2)
                    .preco(BigDecimal.valueOf(10.0))
                    .produto(Produto.builder().id(1).build()) // ajuste conforme o builder de Produto
                    .build();
            Pedido pedidoMock = new Pedido();
            pedidoMock.setId(1);
            pedidoMock.setStatus(StatusEnum.EM_PREPARACAO);
            pedidoMock.setAssociacoes(List.of(associacaoProduto));

            when(pedidosService.realizarCheckout(any(br.com.tech.restauranteapi.domain.CriarPedido.class))).thenReturn(pedidoMock);

            ResponseEntity<PedidoResponseDto> response = pedidosController.realizarCheckout(criarPedidoDto);

            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertEquals(1, response.getBody().getPedidoId());
            assertEquals(StatusEnum.EM_PREPARACAO, response.getBody().getStatus());
            verify(pedidosService).realizarCheckout(any(br.com.tech.restauranteapi.domain.CriarPedido.class));
        }
    }

    @Nested
    @DisplayName("Listar pedidos na fila")
    class ListarFilaPedidos {

        @Test
        @DisplayName("Deve listar pedidos na fila com sucesso")
        void deveListarPedidosNaFilaComSucesso() {
            Pageable pageable = PageRequest.of(0, 10);

            Pedido pedido = new Pedido();
            pedido.setId(1);
            pedido.setStatus(StatusEnum.EM_PREPARACAO);

            Page<Pedido> pedidosMock = new PageImpl<>(List.of(pedido));

            when(pedidosService.listarFilaPedidos(pageable)).thenReturn(pedidosMock);

            ResponseEntity<Page<PedidoDto>> response = pedidosController.listarFilaPedidos(pageable);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(1, response.getBody().getContent().size());
            verify(pedidosService).listarFilaPedidos(pageable);
        }
    }

    @Nested
    @DisplayName("Atualizar status do pedido")
    class AtualizarStatus {

        @Test
        @DisplayName("Deve atualizar status do pedido com sucesso")
        void deveAtualizarStatusDoPedidoComSucesso() {
            Integer pedidoId = 1;
            AtualizarStatusPedidoDto dto = new AtualizarStatusPedidoDto(StatusEnum.FINALIZADO);

            Pedido pedidoMock = new Pedido();
            pedidoMock.setId(pedidoId);
            pedidoMock.setStatus(StatusEnum.FINALIZADO);
            pedidoMock.setAssociacoes(new ArrayList<>()); // Corrige NullPointerException

            when(pedidosService.atualizarStatus(pedidoId, StatusEnum.FINALIZADO)).thenReturn(pedidoMock);

            ResponseEntity<PedidoResponseDto> response = pedidosController.atualizarStatus(pedidoId, dto);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(StatusEnum.FINALIZADO, response.getBody().getStatus());
            verify(pedidosService).atualizarStatus(pedidoId, StatusEnum.FINALIZADO);
        }
    }
}