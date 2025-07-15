package br.com.tech.restauranteapi.usecase;

import br.com.tech.restauranteapi.domain.Cliente;
import br.com.tech.restauranteapi.gateway.ClienteGateway;
import br.com.tech.restauranteapi.usecase.impl.ClienteUsecaseImpl;
import br.com.tech.restauranteapi.exceptions.AlreadyExistsException;
import br.com.tech.restauranteapi.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve cadastrar cliente com sucesso")
    void shouldRegisterClientSuccessfully() {
        Cliente cliente = new Cliente(1, "12345678900", "João Silva", "joao@email.com", "11999999999", "Rua A");

        when(gateway.getCliente(cliente.getCpf())).thenReturn(Optional.empty());
        when(gateway.cadastrar(cliente)).thenReturn(cliente);

        Cliente result = usecase.cadastrar(cliente);

        assertEquals(cliente, result);
        verify(gateway, times(1)).getCliente(cliente.getCpf());
        verify(gateway, times(1)).cadastrar(cliente);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar cadastrar cliente já existente")
    void shouldThrowExceptionWhenRegisteringDuplicateClient() {
        Cliente cliente = new Cliente(1, "12345678900", "João Silva", "joao@email.com", "11999999999", "Rua A");

        when(gateway.getCliente(cliente.getCpf())).thenReturn(Optional.of(cliente));

        assertThrows(AlreadyExistsException.class, () -> usecase.cadastrar(cliente));
        verify(gateway, times(1)).getCliente(cliente.getCpf());
        verify(gateway, never()).cadastrar(cliente);
    }

    @Test
    @DisplayName("Deve retornar cliente com sucesso")
    void shouldReturnClientSuccessfully() {
        Cliente cliente = new Cliente(1, "12345678900", "João Silva", "joao@email.com", "11999999999", "Rua A");

        when(gateway.getCliente(cliente.getCpf())).thenReturn(Optional.of(cliente));

        Cliente result = usecase.getCliente(cliente.getCpf());

        assertEquals(cliente, result);
        verify(gateway, times(1)).getCliente(cliente.getCpf());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar cliente inexistente")
    void shouldThrowExceptionWhenClientNotFound() {
        String cpf = "12345678900";

        when(gateway.getCliente(cpf)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> usecase.getCliente(cpf));
        verify(gateway, times(1)).getCliente(cpf);
    }
}