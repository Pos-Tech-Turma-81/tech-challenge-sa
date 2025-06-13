package br.com.tech.restauranteapi.clientes.dominio.adaptadores.services;

import br.com.tech.restauranteapi.clientes.dominio.portas.repositories.ClienteRepositoryPort;
import br.com.tech.restauranteapi.controller.dtos.ClienteDTO;
import br.com.tech.restauranteapi.gateway.domain.Cliente;
import br.com.tech.restauranteapi.gateway.impl.ClienteGatewayImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

    @InjectMocks
    private ClienteGatewayImpl service;

    @Mock
    private ClienteRepositoryPort repository;

    @Test
    void deveCadastrarClienteComSucesso() {
        ClienteDTO dto = new ClienteDTO(1, "João", "joao@email.com", "11999999999", "12345678900", "Rua A");

        Cliente cliente = new Cliente(1, "João", "joao@email.com", "11999999999", "12345678900", "Rua A");
        when(repository.cadastrar(any())).thenReturn(cliente);

        ClienteDTO response = service.cadastrar(dto);

        assertEquals("João", response.getNome());
        assertEquals("12345678900", response.getCpf());
    }

    @Test
    void deveRetornarClienteDTOPorCpf() {
        Cliente cliente = new Cliente(1, "Maria", "maria@email.com", "11988888888", "09876543210", "Rua B");
        when(repository.getCliente("09876543210")).thenReturn(cliente);

        ClienteDTO dto = service.getCliente("09876543210");

        assertNotNull(dto);
        assertEquals("Maria", dto.getNome());
        assertEquals("09876543210", dto.getCpf());
    }
}
