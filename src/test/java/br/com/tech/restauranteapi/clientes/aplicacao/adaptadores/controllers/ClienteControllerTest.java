package br.com.tech.restauranteapi.clientes.aplicacao.adaptadores.controllers;

import br.com.tech.restauranteapi.clientes.dominio.dtos.ClienteDTO;
import br.com.tech.restauranteapi.clientes.dominio.portas.interfaces.ClienteServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteControllerTest {

    @Mock
    private ClienteServicePort servicePort;

    @InjectMocks
    private ClienteController controller;

    private ClienteDTO mockCliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockCliente = new ClienteDTO();
        mockCliente.setNome("Fulano");
        mockCliente.setCpf("12345678900");
        mockCliente.setEmail("fulano@email.com");
        mockCliente.setTelefone("11999999999");
    }

    @Test
    void deveCadastrarClienteERetornarNoContent() {
        // Arrange
        doNothing().when(servicePort).cadastrar(any(ClienteDTO.class));

        // Act
        ResponseEntity<Void> response = controller.cadastrar(mockCliente);

        // Assert
        assertEquals(204, response.getStatusCodeValue());
        verify(servicePort, times(1)).cadastrar(mockCliente);
    }

    @Test
    void deveBuscarClientePorCpfERetornarOk() {
        // Arrange
        when(servicePort.getCliente("12345678900")).thenReturn(mockCliente);

        // Act
        ResponseEntity<ClienteDTO> response = controller.getCliente("12345678900");

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Fulano", response.getBody().getNome());
        verify(servicePort, times(1)).getCliente("12345678900");
    }
}
