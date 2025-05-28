package br.com.tech.restauranteapi.clientes.infraestrutura.repositories;

import br.com.tech.restauranteapi.clientes.dominio.Cliente;
import br.com.tech.restauranteapi.clientes.dominio.dtos.ClienteEntity;
import br.com.tech.restauranteapi.clientes.infraestrutura.adaptadores.repositories.ClienteRepository;
import br.com.tech.restauranteapi.clientes.infraestrutura.adaptadores.repositories.SpringClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteRepositoryTest {

    @InjectMocks
    private ClienteRepository repository;

    @Mock
    private SpringClienteRepository spring;
    Cliente cliente = new Cliente(1, "João", "joao@email.com", "11999999999", "12345678900", "Rua A");


    @Test
    void deveSalvarClienteComSucesso() {
        ClienteEntity entity = new ClienteEntity(cliente);
        when(spring.save(any())).thenReturn(entity);
        Cliente clienteResponse = repository.cadastrar(cliente);


        assertEquals(entity.getNome(), clienteResponse.getNome());
        assertEquals(entity.getCpf(), clienteResponse.getCpf());
    }

    @Test
    void deveRetornarClientePorCpf() {
        ClienteEntity entity = new ClienteEntity(cliente);
        when(spring.getCliente("12345678900")).thenReturn(Optional.of(entity));

        Cliente cliente = repository.getCliente("12345678900");

        assertNotNull(cliente);
        assertEquals("João", cliente.getNome());
        assertEquals("12345678900", cliente.getCpf());
    }
}
