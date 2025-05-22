package br.com.tech.restauranteapi.clientes.dominio.portas.repositories;

import br.com.tech.restauranteapi.clientes.dominio.Cliente;

public interface ClienteRepositoryPort {
    void cadastrar(Cliente cliente);
    Cliente getCliente(String cpf);
}
