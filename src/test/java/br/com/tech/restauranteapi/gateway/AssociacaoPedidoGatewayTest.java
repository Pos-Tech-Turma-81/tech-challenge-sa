package br.com.tech.restauranteapi.gateway;

import br.com.tech.restauranteapi.gateway.domain.AssociacaoPedidoProduto;
import br.com.tech.restauranteapi.gateway.entity.AssociacaoPedidoProdutoEntity;
import br.com.tech.restauranteapi.gateway.impl.AssociacaoPedidoGatewayImpl;
import br.com.tech.restauranteapi.repository.AssociacaoPedidoProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AssociacaoPedidoGatewayTest {

    @Mock
    private AssociacaoPedidoProdutoRepository repository;

    @InjectMocks
    private AssociacaoPedidoGatewayImpl gateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSalvar_Success() {
        AssociacaoPedidoProduto domain = mock(AssociacaoPedidoProduto.class);
        AssociacaoPedidoProdutoEntity entity = mock(AssociacaoPedidoProdutoEntity.class);
        AssociacaoPedidoProdutoEntity savedEntity = mock(AssociacaoPedidoProdutoEntity.class);
        AssociacaoPedidoProduto mappedDomain = mock(AssociacaoPedidoProduto.class);

        when(domain.toEntity()).thenReturn(entity);
        when(repository.save(entity)).thenReturn(savedEntity);

        try (MockedStatic<AssociacaoPedidoProduto> mockedStatic = mockStatic(AssociacaoPedidoProduto.class)) {
            mockedStatic.when(() -> AssociacaoPedidoProduto.builderAssociacao(savedEntity)).thenReturn(mappedDomain);

            AssociacaoPedidoProduto result = gateway.salvar(domain);

            assertNotNull(result);
            assertEquals(mappedDomain, result);
            verify(domain).toEntity();
            verify(repository).save(entity);
            mockedStatic.verify(() -> AssociacaoPedidoProduto.builderAssociacao(savedEntity));
        }
    }
}
