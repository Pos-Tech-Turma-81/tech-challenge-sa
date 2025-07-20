package br.com.tech.restauranteapi.gateway;

import br.com.tech.restauranteapi.domain.Cliente;

import java.util.Optional;

public interface ClienteGateway {
    Cliente cadastrar(Cliente clienteDto);
    Optional<Cliente> getCliente(String cpf);
    Cliente buscarPorId(Integer id);
}
