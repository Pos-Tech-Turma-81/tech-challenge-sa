package br.com.tech.restauranteapi.controller;

import br.com.tech.restauranteapi.controller.dtos.ClienteDTO;
import br.com.tech.restauranteapi.gateway.domain.Cliente;
import br.com.tech.restauranteapi.usecase.ClienteUsecase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ClienteControllerTest {

    @Mock
    private ClienteUsecase usecase;

    @InjectMocks
    private ClienteController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCadastrar_Success() {
        Cliente clienteMock = new Cliente(1, "John Doe", "john@example.com", "123456789", "12345678900", "Address");
        when(usecase.cadastrar(any(Cliente.class))).thenReturn(clienteMock);

        ClienteDTO requestDto = ClienteDTO.builder()
                .id(null)
                .nome("John Doe")
                .email("john@example.com")
                .telefone("123456789")
                .cpf("12345678900")
                .endereco("Address")
                .build();

        ResponseEntity<ClienteDTO> response = controller.cadastrar(requestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(clienteMock.getId(), response.getBody().getId());
        assertEquals(clienteMock.getNome(), response.getBody().getNome());
        assertEquals(clienteMock.getEmail(), response.getBody().getEmail());
        assertEquals(clienteMock.getTelefone(), response.getBody().getTelefone());
        assertEquals(clienteMock.getCpf(), response.getBody().getCpf());
        assertEquals(clienteMock.getEndereco(), response.getBody().getEndereco());
    }

    @Test
    void testGetCliente_Success() {
        Cliente clienteMock = new Cliente(1, "Jane Doe", "jane@example.com", "987654321", "09876543211", "Address 2");
        when(usecase.getCliente("09876543211")).thenReturn(clienteMock);

        ResponseEntity<ClienteDTO> response = controller.getCliente("09876543211");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(clienteMock.getId(), response.getBody().getId());
        assertEquals(clienteMock.getNome(), response.getBody().getNome());
        assertEquals(clienteMock.getEmail(), response.getBody().getEmail());
        assertEquals(clienteMock.getTelefone(), response.getBody().getTelefone());
        assertEquals(clienteMock.getCpf(), response.getBody().getCpf());
        assertEquals(clienteMock.getEndereco(), response.getBody().getEndereco());
    }
}
