package br.com.tech.restauranteapi.presenter;

import br.com.tech.restauranteapi.domain.Cliente;
import br.com.tech.restauranteapi.entity.ClienteEntity;
import br.com.tech.restauranteapi.controller.dtos.ClienteDTO;

public class ClientePresenter {

    public static ClienteDTO toDto(Cliente cliente) {
        return new ClienteDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getTelefone(),
                cliente.getCpf(),
                cliente.getEndereco()
        );
    }

    public static ClienteEntity toEntity(Cliente cliente) {
        return new ClienteEntity(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getTelefone(),
                cliente.getCpf(),
                cliente.getEndereco()
        );
    }

    public static Cliente toDomain(ClienteDTO dto) {
        return Cliente.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .email(dto.getEmail())
                .telefone(dto.getTelefone())
                .cpf(dto.getCpf())
                .endereco(dto.getEndereco())
                .build();
    }

    public static Cliente toDomain(ClienteEntity entity) {
        return Cliente.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .email(entity.getEmail())
                .telefone(entity.getTelefone())
                .cpf(entity.getCpf())
                .endereco(entity.getEndereco())
                .build();
    }
}