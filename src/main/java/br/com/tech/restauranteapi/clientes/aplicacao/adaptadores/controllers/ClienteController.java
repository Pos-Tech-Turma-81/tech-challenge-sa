package br.com.tech.restauranteapi.clientes.aplicacao.adaptadores.controllers;


import br.com.tech.restauranteapi.clientes.dominio.dtos.ClienteDTO;
import br.com.tech.restauranteapi.clientes.dominio.portas.interfaces.ClienteServicePort;
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
public class ClienteController {

    private final ClienteServicePort port;

    @Operation(summary = "Cadastrar cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping()
    public ResponseEntity<ClienteDTO> cadastrar(@RequestBody @Valid ClienteDTO cliente) {
        ClienteDTO clienteResponse = port.cadastrar(cliente);

        return ResponseEntity.status(HttpStatus.CREATED).body(clienteResponse);
    }

    @Operation(summary = "Buscar cliente por CPF")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "400", description = "Cliente não encontrado")
    })
    @GetMapping("/{cpf}")
    public ResponseEntity<ClienteDTO> getCliente(@PathVariable String cpf) {
        ClienteDTO cliente = port.getCliente(cpf);
        return ResponseEntity.ok(cliente);
    }
}