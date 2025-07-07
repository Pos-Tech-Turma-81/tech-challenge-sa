package br.com.tech.restauranteapi.gateway;

import br.com.tech.restauranteapi.controller.dtos.ClienteDTO;
import br.com.tech.restauranteapi.gateway.domain.Cliente;

import java.util.Optional;

public interface ClienteGateway {
    Cliente cadastrar(Cliente clienteDto);
    Optional<Cliente> getCliente(String cpf);
    Cliente buscarPorId(Integer id);
}
