package br.com.tech.restauranteapi.controller;

import br.com.tech.restauranteapi.controller.dtos.ClienteDTO;
import br.com.tech.restauranteapi.presenter.ClientePresenter;
import br.com.tech.restauranteapi.usecase.ClienteUsecase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
public class ClienteController {

    private final ClienteUsecase usecase;

    public ResponseEntity<ClienteDTO> cadastrar(ClienteDTO clienteDto) {
        var cliente = ClientePresenter.toDomain(clienteDto);
        var clienteResponse = usecase.cadastrar(cliente);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ClientePresenter.toDto(clienteResponse));
    }

    public ResponseEntity<ClienteDTO> getCliente(String cpf) {
        var clienteResponse = usecase.getCliente(cpf);
        return ResponseEntity.ok(ClientePresenter.toDto(clienteResponse));
    }
}