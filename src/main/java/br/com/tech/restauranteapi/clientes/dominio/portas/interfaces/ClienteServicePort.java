package br.com.tech.restauranteapi.clientes.dominio.portas.interfaces;

import br.com.tech.restauranteapi.clientes.dominio.Cliente;
import br.com.tech.restauranteapi.clientes.dominio.dtos.ClienteDTO;

public interface ClienteServicePort {
    void cadastrar(ClienteDTO clienteDto);
    ClienteDTO getCliente(String cpf);
}
