package br.com.tech.restauranteapi.clientes.dominio.adaptadores.services;

import br.com.tech.restauranteapi.clientes.dominio.Cliente;
import br.com.tech.restauranteapi.clientes.dominio.dtos.ClienteDTO;
import br.com.tech.restauranteapi.clientes.dominio.portas.repositories.ClienteRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

    @InjectMocks
    private ClienteServiceImpl service;

    @Mock
    private ClienteRepositoryPort repository;

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
