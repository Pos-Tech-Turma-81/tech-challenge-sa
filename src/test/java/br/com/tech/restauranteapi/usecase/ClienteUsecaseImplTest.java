package br.com.tech.restauranteapi.usecase;

import br.com.tech.restauranteapi.exceptions.AlreadyExistsException;
import br.com.tech.restauranteapi.exceptions.NotFoundException;
import br.com.tech.restauranteapi.gateway.ClienteGateway;
import br.com.tech.restauranteapi.gateway.domain.Cliente;
import br.com.tech.restauranteapi.usecase.impl.ClienteUsecaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteUsecaseImplTest {

    @Mock
    private ClienteGateway gateway;

    @InjectMocks
    private ClienteUsecaseImpl usecase;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cliente = new Cliente(1, "Maria", "maria@email.com", "123456789", "12345678900", "Rua X, 123");
    }

    @Test
    void testCadastrar_Success() {
        when(gateway.getCliente(cliente.getCpf())).thenReturn(Optional.empty());
        when(gateway.cadastrar(cliente)).thenReturn(cliente);

        Cliente result = usecase.cadastrar(cliente);

        assertNotNull(result);
        assertEquals(cliente, result);
        verify(gateway).getCliente(cliente.getCpf());
        verify(gateway).cadastrar(cliente);
    }

    @Test
    void testCadastrar_AlreadyExists() {
        when(gateway.getCliente(cliente.getCpf())).thenReturn(Optional.of(cliente));

        AlreadyExistsException exception = assertThrows(
                AlreadyExistsException.class,
                () -> usecase.cadastrar(cliente)
        );
        assertTrue(exception.getMessage().contains(cliente.getCpf()));
        verify(gateway).getCliente(cliente.getCpf());
        verify(gateway, never()).cadastrar(any());
    }

    @Test
    void testGetCliente_Success() {
        when(gateway.getCliente("12345678900")).thenReturn(Optional.of(cliente));

        Cliente result = usecase.getCliente("12345678900");

        assertNotNull(result);
        assertEquals(cliente, result);
        verify(gateway).getCliente("12345678900");
    }

    @Test
    void testGetCliente_NotFound() {
        String cpf = "00000000000";
        when(gateway.getCliente(cpf)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> usecase.getCliente(cpf)
        );
        assertTrue(exception.getMessage().contains(cpf));
        verify(gateway).getCliente(cpf);
    }
}
