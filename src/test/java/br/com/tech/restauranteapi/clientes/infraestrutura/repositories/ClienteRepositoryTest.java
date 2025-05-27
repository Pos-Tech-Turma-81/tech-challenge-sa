package br.com.tech.restauranteapi.clientes.infraestrutura.repositories;

import br.com.tech.restauranteapi.clientes.dominio.Cliente;
import br.com.tech.restauranteapi.clientes.dominio.dtos.ClienteEntity;
import br.com.tech.restauranteapi.clientes.infraestrutura.adaptadores.repositories.ClienteRepository;
import br.com.tech.restauranteapi.clientes.infraestrutura.adaptadores.repositories.SpringClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteRepositoryTest {

    private SpringClienteRepository spring;
    private ClienteRepository repository;
    Cliente cliente = new Cliente(1, "João", "joao@email.com", "11999999999", "12345678900", "Rua A");

    @BeforeEach
    void setUp() {
        spring = mock(SpringClienteRepository.class);
        repository = new ClienteRepository(spring);
    }

    @Test
    void deveSalvarClienteComSucesso() {

        repository.cadastrar(cliente);

        ArgumentCaptor<ClienteEntity> captor = ArgumentCaptor.forClass(ClienteEntity.class);
        verify(spring).save(captor.capture());

        ClienteEntity entity = captor.getValue();
        assertEquals("João", entity.getNome());
        assertEquals("12345678900", entity.getCpf());
    }

    /*@Test
    void deveRetornarClientePorCpf() {
        ClienteEntity entity = new ClienteEntity(cliente);
        when(spring.getCliente("12345678900")).thenReturn(entity);

        Cliente cliente = repository.getCliente("12345678900");

        assertNotNull(cliente);
        assertEquals("João", cliente.getNome());
        assertEquals("12345678900", cliente.getCpf());
    }*/
}
