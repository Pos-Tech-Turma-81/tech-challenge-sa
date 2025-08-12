package br.com.tech.restauranteapi.controller;

import br.com.tech.restauranteapi.controller.dtos.ClienteDTO;
import br.com.tech.restauranteapi.presenter.ClientePresenter;
import br.com.tech.restauranteapi.usecase.ClienteUsecase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Component
@AllArgsConstructor
@Service
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