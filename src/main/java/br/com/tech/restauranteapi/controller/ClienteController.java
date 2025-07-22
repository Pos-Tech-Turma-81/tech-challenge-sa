package br.com.tech.restauranteapi.controller;

import br.com.tech.restauranteapi.controller.dtos.ClienteDTO;
import br.com.tech.restauranteapi.presenter.ClientePresenter;
import br.com.tech.restauranteapi.usecase.ClienteUsecase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ClienteController {

    private final ClienteUsecase usecase;

    public ClienteDTO cadastrar(ClienteDTO clienteDto) {
        var cliente = ClientePresenter.toDomain(clienteDto);
        var clienteResponse = usecase.cadastrar(cliente);
        return ClientePresenter.toDto(clienteResponse);
    }

    public ClienteDTO getCliente(String cpf) {
        var clienteResponse = usecase.getCliente(cpf);
        return ClientePresenter.toDto(clienteResponse);
    }
}