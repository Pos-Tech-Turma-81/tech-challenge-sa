package br.com.tech.restauranteapi.clientes.dominio.adaptadores.services;

import br.com.tech.restauranteapi.clientes.dominio.Cliente;
import br.com.tech.restauranteapi.clientes.dominio.dtos.ClienteDTO;
import br.com.tech.restauranteapi.clientes.dominio.portas.repositories.ClienteRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceImplTest {

    private ClienteRepositoryPort repository;
    private ClienteServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = mock(ClienteRepositoryPort.class);
        service = new ClienteServiceImpl(repository);
    }

    @Test
    void deveCadastrarClienteComSucesso() {
        ClienteDTO dto = new ClienteDTO(1, "João", "joao@email.com", "11999999999", "12345678900", "Rua A");

        service.cadastrar(dto);

        ArgumentCaptor<Cliente> captor = ArgumentCaptor.forClass(Cliente.class);
        verify(repository).cadastrar(captor.capture());

        Cliente capturado = captor.getValue();
        assertEquals("João", capturado.getNome());
        assertEquals("12345678900", capturado.getCpf());
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
