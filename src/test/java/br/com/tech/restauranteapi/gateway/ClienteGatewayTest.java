package br.com.tech.restauranteapi.gateway;

import br.com.tech.restauranteapi.Fixtures;
import br.com.tech.restauranteapi.exceptions.NotFoundException;
import br.com.tech.restauranteapi.gateway.domain.Cliente;
import br.com.tech.restauranteapi.gateway.entity.ClienteEntity;
import br.com.tech.restauranteapi.gateway.impl.ClienteGatewayImpl;
import br.com.tech.restauranteapi.repository.SpringClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteGatewayTest {

    @Mock
    private SpringClienteRepository repository;

    @InjectMocks
    private ClienteGatewayImpl gateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCadastrar_Success() {
        ClienteEntity savedEntity = mock(ClienteEntity.class);

        when(repository.save(any(ClienteEntity.class))).thenReturn(savedEntity);
        when(savedEntity.toCliente()).thenReturn(Fixtures.mockCliente);

        Cliente result = gateway.cadastrar(Fixtures.mockCliente);

        assertNotNull(result);
        assertEquals(Fixtures.mockCliente, result);
        verify(repository).save(any(ClienteEntity.class));
        verify(savedEntity).toCliente();
    }

    @Test
    void testGetCliente_Found() {
        String cpf = "12345678900";
        ClienteEntity entity = mock(ClienteEntity.class);
        Cliente mappedCliente = Fixtures.mockCliente;

        when(repository.getCliente(cpf)).thenReturn(Optional.of(entity));
        when(entity.toCliente()).thenReturn(mappedCliente);

        Optional<Cliente> result = gateway.getCliente(cpf);

        assertTrue(result.isPresent());
        assertEquals(mappedCliente, result.get());
        verify(repository).getCliente(cpf);
        verify(entity).toCliente();
    }

    @Test
    void testGetCliente_NotFound() {
        String cpf = "00000000000";
        when(repository.getCliente(cpf)).thenReturn(Optional.empty());

        Optional<Cliente> result = gateway.getCliente(cpf);

        assertFalse(result.isPresent());
        verify(repository).getCliente(cpf);
    }

    @Test
    void testBuscarPorId_Found() {
        Integer id = 5;
        ClienteEntity entity = mock(ClienteEntity.class);
        Cliente mappedCliente = Fixtures.mockCliente;

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(entity.toCliente()).thenReturn(mappedCliente);

        Cliente result = gateway.buscarPorId(id);

        assertNotNull(result);
        assertEquals(mappedCliente, result);
        verify(repository).findById(id);
        verify(entity).toCliente();
    }

    @Test
    void testBuscarPorId_NotFound() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> gateway.buscarPorId(99)
        );
        assertTrue(exception.getMessage().contains("99"));
        verify(repository).findById(99);
    }
}
