package br.com.tech.restauranteapi.clientes.infraestrutura.adaptadores.repositories;

import br.com.tech.restauranteapi.clientes.dominio.Cliente;
import br.com.tech.restauranteapi.clientes.dominio.dtos.ClienteEntity;
import br.com.tech.restauranteapi.clientes.dominio.portas.repositories.ClienteRepositoryPort;

public class ClienteRepository implements ClienteRepositoryPort {
    private final SpringClienteRepository spring;

    public ClienteRepository(SpringClienteRepository spring) {
        this.spring = spring;
    }

    @Override
    public void cadastrar(Cliente cliente) {
        this.spring.save(new ClienteEntity(cliente));
    }
}
