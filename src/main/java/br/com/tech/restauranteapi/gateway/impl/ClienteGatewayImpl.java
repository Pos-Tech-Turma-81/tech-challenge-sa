package br.com.tech.restauranteapi.gateway.impl;

import br.com.tech.restauranteapi.entity.ClienteEntity;
import br.com.tech.restauranteapi.exceptions.NotFoundException;
import br.com.tech.restauranteapi.domain.Cliente;
import br.com.tech.restauranteapi.gateway.ClienteGateway;
import br.com.tech.restauranteapi.presenter.ClientePresenter;
import br.com.tech.restauranteapi.repository.SpringClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.lang.String.format;

@Component
@AllArgsConstructor
public class ClienteGatewayImpl implements ClienteGateway {
    private final SpringClienteRepository repository;


    @Override
    public Cliente cadastrar(Cliente cliente) {
        ClienteEntity clienteEntity = ClientePresenter.toEntity(cliente);
        ClienteEntity clienteResponse = repository.save(clienteEntity);
        return ClientePresenter.toDomain(clienteResponse);
    }

    @Override
    public Optional<Cliente> getCliente(String cpf) {
        Optional<ClienteEntity> cliente = repository.getCliente(cpf);

        return cliente.map(ClientePresenter::toDomain);
    }

    @Override
    public Cliente buscarPorId(Integer id) {
        ClienteEntity cliente = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(format("Nao existe cliente com o id %s.", id)));
        return ClientePresenter.toDomain(cliente);
    }
}
