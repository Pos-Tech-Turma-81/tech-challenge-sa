package br.com.tech.restauranteapi.pedidos.aplicacao.adaptadores.controllers;

import br.com.tech.restauranteapi.pedidos.dominio.dtos.CriarPedidoDto;
import br.com.tech.restauranteapi.pedidos.dominio.dtos.PedidoResponseDto;
import br.com.tech.restauranteapi.pedidos.dominio.dtos.PedidoDto;
import br.com.tech.restauranteapi.pedidos.dominio.portas.interfaces.PedidosServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@AllArgsConstructor
@Transactional
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

    @Operation(summary = "Listar pedidos na fila (status AGUARDANDO)")
    @GetMapping("/fila")
    public ResponseEntity<List<PedidoDto>> listarFilaPedidos(@PageableDefault(size = 10) Pageable pageable) {
        List<PedidoDto> pedidos = pedidosService.listarFilaPedidos(pageable);
        return ResponseEntity.ok(pedidos);
    }

}
