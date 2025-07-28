package br.com.tech.restauranteapi.api;

import br.com.tech.restauranteapi.controller.PedidosController;
import br.com.tech.restauranteapi.controller.dtos.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
@AllArgsConstructor
public class PedidosApi {

    private final PedidosController controller;

    @Operation(summary = "Realizar checkout / criar pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping("/checkout")
    public ResponseEntity<PedidoResponseDto> realizarCheckout(@RequestBody @Valid CriarPedidoDto criarPedidoDto) {
        PedidoResponseDto responseDto = controller.realizarCheckout(criarPedidoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @Operation(summary = "Listar pedidos na fila (status EM PREPARACAO)")
    @GetMapping("/fila")
    public ResponseEntity<Page<PedidoDto>> listarFilaPedidos(@PageableDefault(size = 10) Pageable pageable) {
        Page<PedidoDto> pedidos = controller.listarFilaPedidos(pageable);
        return ResponseEntity.ok(pedidos);
    }

    @Operation(summary = "Atualizar status do pedido.")
    @PatchMapping("/{pedidoId}/status")
    public ResponseEntity<PedidoResponseDto> atualizarStatus(
            @PathVariable Integer pedidoId,
            @RequestBody @Valid AtualizarStatusPedidoDto dto) {
        PedidoResponseDto responseDto = controller.atualizarStatus(pedidoId, dto);
        return ResponseEntity.ok(responseDto);
    }
}