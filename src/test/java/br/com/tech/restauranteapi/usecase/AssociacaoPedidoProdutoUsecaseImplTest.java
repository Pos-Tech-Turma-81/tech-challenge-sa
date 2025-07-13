package br.com.tech.restauranteapi.usecase;

import br.com.tech.restauranteapi.gateway.domain.AssociacaoPedidoProduto;
import br.com.tech.restauranteapi.gateway.entity.AssociacaoPedidoProdutoEntity;
import br.com.tech.restauranteapi.gateway.impl.AssociacaoPedidoGatewayImpl;
import br.com.tech.restauranteapi.usecase.impl.AssociacaoPedidoProdutoUsecaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.tech.restauranteapi.Fixtures;

class AssociacaoPedidoProdutoUsecaseImplTest {

    @Mock
    private AssociacaoPedidoGatewayImpl gateway;

    @InjectMocks
    private AssociacaoPedidoProdutoUsecaseImpl usecase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSalvarTodas_Success() {
        AssociacaoPedidoProdutoEntity mockAssociacaoPedidoProdutoEntity2 = AssociacaoPedidoProdutoEntity.builder()
                .id(Fixtures.mockId)
                .quantidade(3)
                .preco(new BigDecimal("33"))
                .build();


        AssociacaoPedidoProduto assoc1 = AssociacaoPedidoProduto.builderAssociacao(Fixtures.mockAssociacaoPedidoProdutoEntity);
        AssociacaoPedidoProduto assoc2 = AssociacaoPedidoProduto.builderAssociacao(mockAssociacaoPedidoProdutoEntity2);

        AssociacaoPedidoProduto savedAssoc1 = new AssociacaoPedidoProduto();
        AssociacaoPedidoProduto savedAssoc2 = new AssociacaoPedidoProduto();

        when(gateway.salvar(assoc1)).thenReturn(savedAssoc1);
        when(gateway.salvar(assoc2)).thenReturn(savedAssoc2);

        List<AssociacaoPedidoProduto> result = usecase.salvarTodas(List.of(assoc1, assoc2));

        assertEquals(2, result.size());
        assertEquals(savedAssoc1, result.get(0));
        assertEquals(savedAssoc2, result.get(1));
        verify(gateway).salvar(assoc1);
        verify(gateway).salvar(assoc2);
    }

    @Test
    void testSalvarTodas_EmptyList() {
        List<AssociacaoPedidoProduto> result = usecase.salvarTodas(List.of());
        assertTrue(result.isEmpty());
        verifyNoInteractions(gateway);
    }
}
