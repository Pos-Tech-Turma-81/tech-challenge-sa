package br.com.tech.restauranteapi.clientes.infraestrutura.adaptadores.repositories;

import br.com.tech.restauranteapi.clientes.dominio.Cliente;
import br.com.tech.restauranteapi.clientes.dominio.dtos.ClienteEntity;
import br.com.tech.restauranteapi.clientes.dominio.portas.repositories.ClienteRepositoryPort;
import br.com.tech.restauranteapi.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ClienteRepository implements ClienteRepositoryPort {
    private final SpringClienteRepository spring;

    public ClienteRepository(SpringClienteRepository spring) {
        this.spring = spring;
    }

    @Override
    public void cadastrar(Cliente cliente) {
        this.spring.save(new ClienteEntity(cliente));
    }

    @Override
    public Cliente getCliente(String cpf) {
        return this.spring.getCliente(cpf).toCliente();
    }

    @Override
    public Cliente findById(Integer id) {
        Optional<ClienteEntity> cliente = this.spring.findById(id);

        return cliente.orElseThrow(() -> new NotFoundException(String.format("Nao existe cliente com o id %s.", id))).toCliente();
    }
}
