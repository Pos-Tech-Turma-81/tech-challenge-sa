package br.com.tech.restauranteapi.clientes.dominio.adaptadores.services;

import br.com.tech.restauranteapi.clientes.dominio.Cliente;
import br.com.tech.restauranteapi.clientes.dominio.dtos.ClienteDTO;
import br.com.tech.restauranteapi.clientes.dominio.portas.interfaces.ClienteServicePort;
import br.com.tech.restauranteapi.clientes.dominio.portas.repositories.ClienteRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class ClienteServiceImpl implements ClienteServicePort {
    private final ClienteRepositoryPort repository;

    public ClienteServiceImpl(ClienteRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public ClienteDTO cadastrar(ClienteDTO clienteDto) {
        Cliente cliente = new Cliente(clienteDto.getId(),
                clienteDto.getNome(),
                clienteDto.getEmail(),
                clienteDto.getTelefone(),
                clienteDto.getCpf(),
                clienteDto.getEndereco());
        Cliente clienteResponse = repository.cadastrar(cliente);

        return ClienteDTO
                .builder()
                .id(clienteResponse.getId())
                .cpf(clienteResponse.getCpf())
                .nome(clienteResponse.getNome())
                .email(clienteResponse.getEmail())
                .endereco(clienteResponse.getEndereco())
                .telefone(clienteResponse.getTelefone())
                .build();
    }

    @Override
    public ClienteDTO getCliente(String cpf) {
        Cliente cliente =  repository.getCliente(cpf);
        return new ClienteDTO(cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getTelefone(),
                cliente.getCpf(),
                cliente.getEndereco());
    }
}
