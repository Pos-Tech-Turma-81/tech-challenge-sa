package br.com.tech.restauranteapi.clientes.aplicacao.adaptadores.controllers;

import br.com.tech.restauranteapi.controller.dtos.ClienteDTO;
import br.com.tech.restauranteapi.gateway.ClienteGateway;
import br.com.tech.restauranteapi.controller.ClienteController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {

    @Mock
    private ClienteGateway servicePort;

    @InjectMocks
    private ClienteController controller;

    private ClienteDTO mockCliente;

    @BeforeEach
    void setUp() {
        mockCliente = new ClienteDTO();
        mockCliente.setNome("Fulano");
        mockCliente.setCpf("12345678900");
        mockCliente.setEmail("fulano@email.com");
        mockCliente.setTelefone("11999999999");
    }

    @Test
    void deveCadastrarClienteERetornarNoContent() {
        // Arrange
        when(servicePort.cadastrar(any())).thenReturn(mockCliente);
        // Act
        ResponseEntity<ClienteDTO> response = controller.cadastrar(mockCliente);

        // Assert
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(mockCliente.getId(), response.getBody().getId());
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
