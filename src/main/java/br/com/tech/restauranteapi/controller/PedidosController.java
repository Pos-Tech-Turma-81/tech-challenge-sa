package br.com.tech.restauranteapi.controller;

import br.com.tech.restauranteapi.controller.dtos.*;
import br.com.tech.restauranteapi.domain.Pedido;
import br.com.tech.restauranteapi.presenter.CriarPedidoPresenter;
import br.com.tech.restauranteapi.presenter.PedidoPresenter;
import br.com.tech.restauranteapi.usecase.PedidosUsecase;
import br.com.tech.restauranteapi.utils.enums.StatusEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@AllArgsConstructor
@Transactional
public class PedidosController {

    private final PedidosUsecase pedidosService;

    @Operation(summary = "Realizar checkout / criar pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping("/checkout")
    public ResponseEntity<PedidoResponseDto> realizarCheckout(@RequestBody @Valid CriarPedidoDto criarPedidoDto) {
        Pedido response = pedidosService.realizarCheckout(
                CriarPedidoPresenter.toDomain(criarPedidoDto)
        );

        PedidoResponseDto responseDto = PedidoPresenter.toResponseDto(response);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @Operation(summary = "Listar pedidos na fila (status EM PREPARACAO)")
    @GetMapping("/fila")
    public ResponseEntity<Page<PedidoDto>> listarFilaPedidos(@PageableDefault(size = 10) Pageable pageable) {
        Page<Pedido> pedidos = pedidosService.listarFilaPedidos(pageable);
        return ResponseEntity.ok(pedidos.map(PedidoPresenter::toDto));
    }

    @Operation(summary = "Atualizar status do pedido.")
    @PatchMapping("/{pedidoId}/status")
    public ResponseEntity<PedidoResponseDto> atualizarStatus(
            @PathVariable Integer pedidoId,
            @RequestBody @Valid AtualizarStatusPedidoDto dto) {
        Pedido pedidoAtualizado = pedidosService.atualizarStatus(pedidoId, dto.getStatus());
        PedidoResponseDto responseDto = PedidoPresenter.toResponseDto(pedidoAtualizado);
        return ResponseEntity.ok(responseDto);
    }
}
