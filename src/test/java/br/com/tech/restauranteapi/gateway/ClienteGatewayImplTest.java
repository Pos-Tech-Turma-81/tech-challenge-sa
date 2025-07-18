package br.com.tech.restauranteapi.gateway;

import br.com.tech.restauranteapi.domain.Cliente;
import br.com.tech.restauranteapi.entity.ClienteEntity;
import br.com.tech.restauranteapi.exceptions.NotFoundException;
import br.com.tech.restauranteapi.gateway.impl.ClienteGatewayImpl;
import br.com.tech.restauranteapi.presenter.ClientePresenter;
import br.com.tech.restauranteapi.repository.SpringClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.MockedStatic;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteGatewayImplTest {

    @Mock
    private SpringClienteRepository repository;

    @InjectMocks
    private ClienteGatewayImpl gateway;

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
            Cliente cliente = mock(Cliente.class);
            ClienteEntity clienteEntity = mock(ClienteEntity.class);
            Cliente expectedCliente = mock(Cliente.class);

            try (MockedStatic<ClientePresenter> presenterMock = mockStatic(ClientePresenter.class)) {
                presenterMock.when(() -> ClientePresenter.toEntity(cliente)).thenReturn(clienteEntity);
                when(repository.save(clienteEntity)).thenReturn(clienteEntity);
                presenterMock.when(() -> ClientePresenter.toDomain(clienteEntity)).thenReturn(expectedCliente);

                Cliente result = gateway.cadastrar(cliente);

                assertEquals(expectedCliente, result);
                verify(repository).save(clienteEntity);
            }
        }
    }

    @Nested
    @DisplayName("Obter cliente por CPF")
    class ObterClientePorCpf {

        @Test
        @DisplayName("Deve retornar cliente quando encontrado")
        void deveRetornarClienteQuandoEncontrado() {
            String cpf = "12345678900";
            ClienteEntity clienteEntity = mock(ClienteEntity.class);
            Cliente expectedCliente = mock(Cliente.class);

            try (MockedStatic<ClientePresenter> presenterMock = mockStatic(ClientePresenter.class)) {
                when(repository.getCliente(cpf)).thenReturn(Optional.of(clienteEntity));
                presenterMock.when(() -> ClientePresenter.toDomain(clienteEntity)).thenReturn(expectedCliente);

                Optional<Cliente> result = gateway.getCliente(cpf);

                assertTrue(result.isPresent());
                assertEquals(expectedCliente, result.get());
            }
        }

        @Test
        @DisplayName("Deve retornar vazio quando cliente não encontrado")
        void deveRetornarVazioQuandoClienteNaoEncontrado() {
            String cpf = "12345678900";

            when(repository.getCliente(cpf)).thenReturn(Optional.empty());

            Optional<Cliente> result = gateway.getCliente(cpf);

            assertFalse(result.isPresent());
        }
    }

    @Nested
    @DisplayName("Buscar cliente por ID")
    class BuscarClientePorId {

        @Test
        @DisplayName("Deve retornar cliente quando encontrado")
        void deveRetornarClienteQuandoEncontrado() {
            Integer id = 1;
            ClienteEntity clienteEntity = mock(ClienteEntity.class);
            Cliente expectedCliente = mock(Cliente.class);

            try (MockedStatic<ClientePresenter> presenterMock = mockStatic(ClientePresenter.class)) {
                when(repository.findById(id)).thenReturn(Optional.of(clienteEntity));
                presenterMock.when(() -> ClientePresenter.toDomain(clienteEntity)).thenReturn(expectedCliente);

                Cliente result = gateway.buscarPorId(id);

                assertEquals(expectedCliente, result);
            }
        }

        @Test
        @DisplayName("Deve lançar exceção quando cliente não encontrado")
        void deveLancarExcecaoQuandoClienteNaoEncontrado() {
            Integer id = 1;

            when(repository.findById(id)).thenReturn(Optional.empty());

            NotFoundException exception = assertThrows(NotFoundException.class, () -> gateway.buscarPorId(id));
            assertEquals("Nao existe cliente com o id 1.", exception.getMessage());
        }
    }
}