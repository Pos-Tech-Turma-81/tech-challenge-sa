package br.com.tech.restauranteapi.api;

import br.com.tech.restauranteapi.controller.ClienteController;
import br.com.tech.restauranteapi.controller.dtos.ClienteDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
@AllArgsConstructor
public class ClienteApi {

    private final ClienteController controller;

    @Operation(summary = "Cadastrar cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping()
    public ResponseEntity<ClienteDTO> cadastrar(@RequestBody @Valid ClienteDTO clienteDto) {
        var response = controller.cadastrar(clienteDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response.getBody());
    }

    @Operation(summary = "Buscar cliente por CPF")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "400", description = "Cliente não encontrado")
    })
    @GetMapping("/{cpf}")
    public ResponseEntity<ClienteDTO> getCliente(@PathVariable String cpf) {
        var response = controller.getCliente(cpf);
        return ResponseEntity.ok(response.getBody());
    }
}