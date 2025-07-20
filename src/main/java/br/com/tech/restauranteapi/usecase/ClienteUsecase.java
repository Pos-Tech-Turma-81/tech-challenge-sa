package br.com.tech.restauranteapi.usecase;

import br.com.tech.restauranteapi.domain.Cliente;

public interface ClienteUsecase {
    Cliente cadastrar(Cliente clienteDto);
    Cliente getCliente(String cpf);
}
