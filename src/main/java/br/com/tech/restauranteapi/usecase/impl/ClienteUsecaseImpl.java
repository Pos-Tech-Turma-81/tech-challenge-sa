package br.com.tech.restauranteapi.usecase.impl;

import br.com.tech.restauranteapi.exceptions.AlreadyExistsException;
import br.com.tech.restauranteapi.exceptions.NotFoundException;
import br.com.tech.restauranteapi.gateway.ClienteGateway;
import br.com.tech.restauranteapi.gateway.domain.Cliente;
import br.com.tech.restauranteapi.gateway.entity.ClienteEntity;
import br.com.tech.restauranteapi.usecase.ClienteUsecase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.lang.String.format;

@Component
@AllArgsConstructor
public class ClienteUsecaseImpl implements ClienteUsecase {

    public final ClienteGateway gateway;

    @Override
    public Cliente cadastrar(Cliente cliente) {

        boolean duplicate = gateway.getCliente(cliente.getCpf()).isPresent();
        if (duplicate) {
            throw new AlreadyExistsException(format(
                    "Cliente já cadastrado. CPF: %s.", cliente.getCpf()));
        }

        return gateway.cadastrar(cliente);

    }

    @Override
    public Cliente getCliente(String cpf) {
        Optional<Cliente> cliente = gateway.getCliente(cpf);

        return cliente.orElseThrow(() -> new NotFoundException(format(
                "Cliente não encontrado. CPF: %s.", cpf)));
    }
}
