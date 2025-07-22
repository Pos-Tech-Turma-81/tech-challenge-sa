package br.com.tech.restauranteapi.controller;

import br.com.tech.restauranteapi.controller.dtos.ClienteDTO;
import br.com.tech.restauranteapi.domain.Cliente;
import br.com.tech.restauranteapi.usecase.ClienteUsecase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClienteControllerTest {

    @Mock
    private ClienteUsecase clienteUsecase;

    @InjectMocks
    private ClienteController clienteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("Cadastrar cliente")
    class CadastrarCliente {

        @Test
        @DisplayName("Deve cadastrar cliente com sucesso")
        void deveCadastrarClienteComSucesso() {
            ClienteDTO clienteDto = ClienteDTO.builder()
                    .id(1)
                    .nome("João")
                    .email("joao@email.com")
                    .telefone("123456789")
                    .cpf("12345678900")
                    .endereco("Rua A")
                    .build();

            Cliente clienteResponse = new Cliente(1, "João", "joao@email.com", "123456789", "12345678900", "Rua A");

            when(clienteUsecase.cadastrar(any(Cliente.class))).thenReturn(clienteResponse);

            ClienteDTO response = clienteController.cadastrar(clienteDto);

            assertEquals(clienteDto.getCpf(), response.getCpf());
            verify(clienteUsecase).cadastrar(any(Cliente.class));
        }
    }

    @Nested
    @DisplayName("Buscar cliente por CPF")
    class BuscarClientePorCpf {

        @Test
        @DisplayName("Deve retornar cliente com sucesso")
        void deveRetornarClienteComSucesso() {
            String cpf = "12345678900";
            Cliente clienteResponse = new Cliente(1, "João", "joao@email.com", "123456789", cpf, "Rua A");

            when(clienteUsecase.getCliente(cpf)).thenReturn(clienteResponse);

            ClienteDTO response = clienteController.getCliente(cpf);

            assertEquals(cpf, response.getCpf());
            verify(clienteUsecase).getCliente(cpf);
        }
    }
}