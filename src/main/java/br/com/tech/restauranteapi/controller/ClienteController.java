package br.com.tech.restauranteapi.controller;


import br.com.tech.restauranteapi.controller.dtos.ClienteDTO;
import br.com.tech.restauranteapi.gateway.domain.Cliente;
import br.com.tech.restauranteapi.usecase.ClienteUsecase;
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

    private final ClienteUsecase usecase;

    @Operation(summary = "Cadastrar cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping()
    public ResponseEntity<ClienteDTO> cadastrar(@RequestBody @Valid ClienteDTO clienteDto) {
        Cliente clienteResponse = usecase.cadastrar(new Cliente(clienteDto.getId(),
                clienteDto.getNome(),
                clienteDto.getEmail(),
                clienteDto.getTelefone(),
                clienteDto.getCpf(),
                clienteDto.getEndereco()));

        return ResponseEntity.status(HttpStatus.CREATED).body(ClienteDTO
                .builder()
                .id(clienteResponse.getId())
                .cpf(clienteResponse.getCpf())
                .nome(clienteResponse.getNome())
                .email(clienteResponse.getEmail())
                .endereco(clienteResponse.getEndereco())
                .telefone(clienteResponse.getTelefone())
                .build());
    }

    @Operation(summary = "Buscar cliente por CPF")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "400", description = "Cliente não encontrado")
    })
    @GetMapping("/{cpf}")
    public ResponseEntity<ClienteDTO> getCliente(@PathVariable String cpf) {
        Cliente clienteResponse = usecase.getCliente(cpf);
        return ResponseEntity.ok(ClienteDTO
                .builder()
                .id(clienteResponse.getId())
                .cpf(clienteResponse.getCpf())
                .nome(clienteResponse.getNome())
                .email(clienteResponse.getEmail())
                .endereco(clienteResponse.getEndereco())
                .telefone(clienteResponse.getTelefone())
                .build());
    }
}