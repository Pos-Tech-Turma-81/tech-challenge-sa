package br.com.tech.restauranteapi.api;

import br.com.tech.restauranteapi.controller.PedidosController;
import br.com.tech.restauranteapi.controller.dtos.*;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PedidosApiTest {

    @Mock
    private PedidosController pedidosController;

    @InjectMocks
    private PedidosApi pedidosApi;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("Realizar checkout via API")
    class RealizarCheckoutApi {

        @Test
        @DisplayName("Deve retornar CREATED ao criar pedido")
        void deveRetornarCreatedAoCriarPedido() {
            CriarPedidoDto criarPedidoDto = CriarPedidoDto.builder()
                    .clienteId(1)
                    .produtos(List.of(new ProdutoPedidoDto(1, 2)))
                    .build();

            PedidoResponseDto responseDto = PedidoResponseDto.builder()
                    .pedidoId(1)
                    .status(StatusEnum.EM_PREPARACAO)
                    .build();

            when(pedidosController.realizarCheckout(any(CriarPedidoDto.class))).thenReturn(responseDto);

            ResponseEntity<PedidoResponseDto> response = pedidosApi.realizarCheckout(criarPedidoDto);

            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertEquals(1, response.getBody().getPedidoId());
            verify(pedidosController).realizarCheckout(any(CriarPedidoDto.class));
        }
    }

    @Nested
    @DisplayName("Listar pedidos na fila via API")
    class ListarFilaPedidosApi {

        @Test
        @DisplayName("Deve retornar OK ao listar pedidos")
        void deveRetornarOkAoListarPedidos() {
            Pageable pageable = PageRequest.of(0, 10);

            PedidoDto pedidoDto = PedidoDto.builder()
                    .id(1) // ajuste aqui
                    .status(StatusEnum.EM_PREPARACAO)
                    .build();
            Page<PedidoDto> pedidosPage = new PageImpl<>(List.of(pedidoDto));

            when(pedidosController.listarFilaPedidos(pageable)).thenReturn(pedidosPage);

            ResponseEntity<Page<PedidoDto>> response = pedidosApi.listarFilaPedidos(pageable);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(1, response.getBody().getContent().size());
            verify(pedidosController).listarFilaPedidos(pageable);
        }
    }

    @Nested
    @DisplayName("Atualizar status via API")
    class AtualizarStatusApi {

        @Test
        @DisplayName("Deve retornar OK ao atualizar status")
        void deveRetornarOkAoAtualizarStatus() {
            Integer pedidoId = 1;
            AtualizarStatusPedidoDto dto = new AtualizarStatusPedidoDto(StatusEnum.FINALIZADO);

            PedidoResponseDto responseDto = PedidoResponseDto.builder()
                    .pedidoId(pedidoId)
                    .status(StatusEnum.FINALIZADO)
                    .build();

            when(pedidosController.atualizarStatus(pedidoId, dto)).thenReturn(responseDto);

            ResponseEntity<PedidoResponseDto> response = pedidosApi.atualizarStatus(pedidoId, dto);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(StatusEnum.FINALIZADO, response.getBody().getStatus());
            verify(pedidosController).atualizarStatus(pedidoId, dto);
        }
    }
}