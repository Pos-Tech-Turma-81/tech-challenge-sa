package br.com.tech.restauranteapi.gateway.impl;

import br.com.tech.restauranteapi.exceptions.NotFoundException;
import br.com.tech.restauranteapi.gateway.domain.Cliente;
import br.com.tech.restauranteapi.controller.dtos.ClienteDTO;
import br.com.tech.restauranteapi.gateway.ClienteGateway;
import br.com.tech.restauranteapi.gateway.entity.ClienteEntity;
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

        ClienteEntity clienteResponse = repository.save(new ClienteEntity(cliente));

        return clienteResponse.toCliente();
    }

    @Override
    public Optional<Cliente> getCliente(String cpf) {
        Optional<ClienteEntity> cliente =  repository.getCliente(cpf);


        return cliente.isPresent() ? Optional.of(cliente.get().toCliente()) : Optional.empty();
    }

    @Override
    public Cliente buscarPorId(Integer id) {
        Optional<ClienteEntity> cliente = this.repository.findById(id);

        return cliente.orElseThrow(() -> new NotFoundException(format("Nao existe cliente com o id %s.", id))).toCliente();
    }
}
