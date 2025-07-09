package br.com.tech.restauranteapi.controller;

import br.com.tech.restauranteapi.controller.dtos.CriarPedidoDto;
import br.com.tech.restauranteapi.controller.dtos.PedidoResponseDto;
import br.com.tech.restauranteapi.controller.dtos.PedidoDto;
import br.com.tech.restauranteapi.controller.dtos.ProdutoPedidoResponseDto;
import br.com.tech.restauranteapi.gateway.domain.Pedido;
import br.com.tech.restauranteapi.usecase.PedidosUsecase;
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
        Pedido response = pedidosService.realizarCheckout(criarPedidoDto.toDomain());

        // Mapear resposta
        List<ProdutoPedidoResponseDto> produtos = response.getAssociacoes().stream()
                .map(assoc -> ProdutoPedidoResponseDto.builder()
                        .produtoId(assoc.getProduto().getId())
                        .nomeProduto(assoc.getProduto() != null ? assoc.getProduto().getNome() : "Produto não encontrado")
                        .quantidade(assoc.getQuantidade())
                        .preco(assoc.getPreco())
                        .build())
                .toList();

        PedidoResponseDto responseDto = PedidoResponseDto.builder()
                .pedidoId(response.getId())
                .clienteId(response.getCliente() != null ? response.getCliente().getId() : null)
                .status(response.getStatus())
                .dataHora(response.getDataHoraInclusaoPedido())
                .produtos(produtos)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @Operation(summary = "Listar pedidos na fila (status AGUARDANDO)")
    @GetMapping("/fila")
    public ResponseEntity<Page<PedidoDto>> listarFilaPedidos(@PageableDefault(size = 10) Pageable pageable) {
        Page<Pedido> pedidos = pedidosService.listarFilaPedidos(pageable);
        return ResponseEntity.ok(pedidos
                .map(Pedido::toPedidosDto));
    }
}
