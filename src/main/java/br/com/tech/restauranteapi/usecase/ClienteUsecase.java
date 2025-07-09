package br.com.tech.restauranteapi.usecase;

import br.com.tech.restauranteapi.gateway.domain.Cliente;

public interface ClienteUsecase {
    Cliente cadastrar(Cliente clienteDto);
    Cliente getCliente(String cpf);
}
