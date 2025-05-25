package br.com.tech.restauranteapi.pedidos.aplicacao.adaptadores.controllers;

import br.com.tech.restauranteapi.pedidos.dominio.dtos.CriarPedidoDto;
import br.com.tech.restauranteapi.pedidos.dominio.dtos.PedidoResponseDto;
import br.com.tech.restauranteapi.pedidos.dominio.dtos.PedidosDto;
import br.com.tech.restauranteapi.pedidos.dominio.portas.interfaces.PedidosServicePort;
import br.com.tech.restauranteapi.utils.enums.StatusEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pedidos")
@AllArgsConstructor
public class PedidosController {

    private final PedidosServicePort pedidosService;

    @Operation(summary = "Realizar checkout / criar pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping("/checkout")
    public ResponseEntity<PedidoResponseDto> realizarCheckout(@RequestBody @Valid CriarPedidoDto criarPedidoDto) {
        PedidoResponseDto response = pedidosService.realizarCheckout(criarPedidoDto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Listar pedidos")
    @GetMapping("/listarPedidos")
    public ResponseEntity<List<PedidosDto>> listarPedidos(
            @RequestParam(required = false) StatusEnum status,
            @RequestParam(required = false) Integer clienteId
    ) {
        List<PedidosDto> pedidos = pedidosService.listarPedidos(status, clienteId);
        return ResponseEntity.ok(pedidos);
    }
}
