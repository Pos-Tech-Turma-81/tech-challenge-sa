package br.com.tech.restauranteapi.api;

import br.com.tech.restauranteapi.controller.ClienteController;
import br.com.tech.restauranteapi.controller.dtos.ClienteDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClienteApiTest {

    @Mock
    private ClienteController clienteController;

    @InjectMocks
    private ClienteApi clienteApi;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("Cadastrar cliente via API")
    class CadastrarClienteApi {

        @Test
        @DisplayName("Deve retornar CREATED ao cadastrar cliente")
        void deveRetornarCreatedAoCadastrarCliente() {
            ClienteDTO clienteDto = ClienteDTO.builder()
                    .id(1)
                    .nome("João")
                    .email("joao@email.com")
                    .telefone("123456789")
                    .cpf("12345678900")
                    .endereco("Rua A")
                    .build();

            when(clienteController.cadastrar(any(ClienteDTO.class))).thenReturn(clienteDto);

            ResponseEntity<ClienteDTO> response = clienteApi.cadastrar(clienteDto);

            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertEquals(clienteDto.getCpf(), response.getBody().getCpf());
            verify(clienteController).cadastrar(any(ClienteDTO.class));
        }
    }

    @Nested
    @DisplayName("Buscar cliente via API")
    class BuscarClienteApi {

        @Test
        @DisplayName("Deve retornar OK ao buscar cliente")
        void deveRetornarOkAoBuscarCliente() {
            String cpf = "12345678900";
            ClienteDTO clienteDto = ClienteDTO.builder()
                    .id(1)
                    .nome("João")
                    .email("joao@email.com")
                    .telefone("123456789")
                    .cpf(cpf)
                    .endereco("Rua A")
                    .build();

            when(clienteController.getCliente(cpf)).thenReturn(clienteDto);

            ResponseEntity<ClienteDTO> response = clienteApi.getCliente(cpf);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(cpf, response.getBody().getCpf());
            verify(clienteController).getCliente(cpf);
        }
    }
}