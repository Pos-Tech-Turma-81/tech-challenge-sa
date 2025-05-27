package br.com.tech.restauranteapi.clientes.infraestrutura.adaptadores.repositories;

import br.com.tech.restauranteapi.clientes.dominio.Cliente;
import br.com.tech.restauranteapi.clientes.dominio.dtos.ClienteEntity;
import br.com.tech.restauranteapi.clientes.dominio.portas.repositories.ClienteRepositoryPort;
import br.com.tech.restauranteapi.exceptions.NotFoundException;
import br.com.tech.restauranteapi.exceptions.AlreadyExistsException;
import br.com.tech.restauranteapi.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.lang.String.format;

import java.util.Optional;

@Component
public class ClienteRepository implements ClienteRepositoryPort {
    private final SpringClienteRepository spring;

    public ClienteRepository(SpringClienteRepository spring) {
        this.spring = spring;
    }

    @Override
    public void cadastrar(Cliente cliente) {
        boolean duplicate = spring.getCliente(cliente.getCpf()).isPresent();
        if (duplicate) {
            throw new AlreadyExistsException(cliente.getCpf());
        }

        this.spring.save(new ClienteEntity(cliente));
    }

    @Override
    public Cliente getCliente(String cpf) {
        Optional<ClienteEntity> cliente = this.spring.getCliente(cpf);

        return cliente.orElseThrow(() -> new NotFoundException(format(
                "Cliente não encontrado. CPF: %s.", cpf))).toCliente();
    }

    @Override
    public Cliente findById(Integer id) {
        Optional<ClienteEntity> cliente = this.spring.findById(id);

        return cliente.orElseThrow(() -> new NotFoundException(String.format("Nao existe cliente com o id %s.", id))).toCliente();
    }
}
