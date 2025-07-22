package br.com.tech.restauranteapi.controller;

import br.com.tech.restauranteapi.controller.dtos.*;
import br.com.tech.restauranteapi.domain.*;
import br.com.tech.restauranteapi.presenter.CriarPedidoPresenter;
import br.com.tech.restauranteapi.presenter.PedidoPresenter;
import br.com.tech.restauranteapi.usecase.PedidosUsecase;
import br.com.tech.restauranteapi.utils.enums.StatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        CriarPedidoDto criarPedidoDto = new CriarPedidoDto();
        CriarPedido criarPedidoDomain = mock(CriarPedido.class);
        Pedido pedidoDomain = mock(Pedido.class);
        PedidoResponseDto pedidoResponseDto = mock(PedidoResponseDto.class);

        try (
                MockedStatic<CriarPedidoPresenter> mockCriar = mockStatic(CriarPedidoPresenter.class);
                MockedStatic<PedidoPresenter> mockPresenter = mockStatic(PedidoPresenter.class)
        ) {
            mockCriar.when(() -> CriarPedidoPresenter.toDomain(criarPedidoDto)).thenReturn(criarPedidoDomain);
            when(pedidosService.realizarCheckout(CriarPedidoPresenter.toDomain(criarPedidoDto))).thenReturn(pedidoDomain);
            mockPresenter.when(() -> PedidoPresenter.toResponseDto(pedidoDomain)).thenReturn(pedidoResponseDto);

            var response = controller.realizarCheckout(criarPedidoDto);

            assertEquals(201, response.getStatusCodeValue());
            assertEquals(pedidoResponseDto, response.getBody());

            mockCriar.verify(() -> CriarPedidoPresenter.toDomain(criarPedidoDto), Mockito.times(2));
            mockPresenter.verify(() -> PedidoPresenter.toResponseDto(pedidoDomain));
        }
    }

    @Test
    void testListarFilaPedidos_Success() {
        Pedido pedido = mock(Pedido.class);
        PedidoDto dto = mock(PedidoDto.class);

        Page<Pedido> page = new PageImpl<>(List.of(pedido));
        when(pedidosService.listarFilaPedidos(any())).thenReturn(page);

        try (MockedStatic<PedidoPresenter> mock = mockStatic(PedidoPresenter.class)) {
            mock.when(() -> PedidoPresenter.toDto(pedido)).thenReturn(dto);

            PageRequest pageable = PageRequest.of(0, 10);
            var response = controller.listarFilaPedidos(pageable);

            assertEquals(200, response.getStatusCodeValue());
            assertNotNull(response.getBody());
            assertEquals(dto, response.getBody().getContent().getFirst());

            verify(pedidosService).listarFilaPedidos(pageable);
            mock.verify(() -> PedidoPresenter.toDto(pedido));
        }
    }

    @Test
    void testAtualizarStatus_Success() {
        int pedidoId = 123;
        StatusEnum newStatus = StatusEnum.RECEBIDO;

        AtualizarStatusPedidoDto statusDto = new AtualizarStatusPedidoDto();
        statusDto.setStatus(newStatus);

        Pedido pedidoAtualizado = mock(Pedido.class);
        PedidoResponseDto responseDto = mock(PedidoResponseDto.class);

        when(pedidosService.atualizarStatus(pedidoId, newStatus)).thenReturn(pedidoAtualizado);

        try (MockedStatic<PedidoPresenter> mock = mockStatic(PedidoPresenter.class)) {
            mock.when(() -> PedidoPresenter.toResponseDto(pedidoAtualizado)).thenReturn(responseDto);

            ResponseEntity<PedidoResponseDto> response =
                    controller.atualizarStatus(pedidoId, statusDto);

            assertEquals(200, response.getStatusCodeValue());
            assertEquals(responseDto, response.getBody());

            verify(pedidosService).atualizarStatus(pedidoId, newStatus);
            mock.verify(() -> PedidoPresenter.toResponseDto(pedidoAtualizado));
        }
    }
}
