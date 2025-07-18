package br.com.tech.restauranteapi.gateway;

import br.com.tech.restauranteapi.domain.AssociacaoPedidoProduto;
import br.com.tech.restauranteapi.entity.AssociacaoPedidoProdutoEntity;
import br.com.tech.restauranteapi.gateway.impl.AssociacaoPedidoGatewayImpl;
import br.com.tech.restauranteapi.presenter.AssociacaoPedidoProdutoPresenter;
import br.com.tech.restauranteapi.repository.AssociacaoPedidoProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AssociacaoPedidoGatewayImplTest {

    @Mock
    private AssociacaoPedidoProdutoRepository repository;

    @InjectMocks
    private AssociacaoPedidoGatewayImpl gateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("Salvar associacao")
    class SalvarAssociacao {

        @Test
        @DisplayName("Deve salvar associacao com sucesso")
        void deveSalvarAssociacaoComSucesso() {
            AssociacaoPedidoProduto associacao = mock(AssociacaoPedidoProduto.class);
            AssociacaoPedidoProdutoEntity entity = mock(AssociacaoPedidoProdutoEntity.class);
            AssociacaoPedidoProdutoEntity savedEntity = mock(AssociacaoPedidoProdutoEntity.class);

            try (MockedStatic<AssociacaoPedidoProdutoPresenter> presenterMock = mockStatic(AssociacaoPedidoProdutoPresenter.class)) {
                presenterMock.when(() -> AssociacaoPedidoProdutoPresenter.toEntity(associacao)).thenReturn(entity);
                when(repository.save(entity)).thenReturn(savedEntity);
                presenterMock.when(() -> AssociacaoPedidoProdutoPresenter.fromToDomain(savedEntity)).thenReturn(mock(AssociacaoPedidoProduto.class));

                assertDoesNotThrow(() -> gateway.salvar(associacao));
                verify(repository).save(entity);
            }
        }
    }
}