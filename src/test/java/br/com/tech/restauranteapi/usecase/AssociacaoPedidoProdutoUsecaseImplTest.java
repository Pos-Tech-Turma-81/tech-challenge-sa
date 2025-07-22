package br.com.tech.restauranteapi.usecase;

import br.com.tech.restauranteapi.domain.AssociacaoPedidoProduto;
import br.com.tech.restauranteapi.domain.Pedido;
import br.com.tech.restauranteapi.domain.Produto;
import br.com.tech.restauranteapi.gateway.impl.AssociacaoPedidoGatewayImpl;
import br.com.tech.restauranteapi.usecase.impl.AssociacaoPedidoProdutoUsecaseImpl;
import br.com.tech.restauranteapi.utils.enums.CategoriaEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AssociacaoPedidoProdutoUsecaseImplTest {

    @Mock
    private AssociacaoPedidoGatewayImpl gateway;

    @InjectMocks
    private AssociacaoPedidoProdutoUsecaseImpl usecase;

    private Pedido pedido;
    private Produto produto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pedido = new Pedido(1, null, null, LocalDateTime.now(), List.of());
        produto = new Produto(
                1,
                "Produto Teste",
                CategoriaEnum.BEBIDA,
                BigDecimal.TEN,
                "Descrição teste",
                null
        );
    }

    @Test
    @DisplayName("Deve salvar todas as associações com sucesso")
    void shouldSaveAllAssociationsSuccessfully() {
        List<AssociacaoPedidoProduto> inputAssociacoes = List.of(
                new AssociacaoPedidoProduto(2, pedido, BigDecimal.TEN, produto)
        );
        List<AssociacaoPedidoProduto> savedAssociacoes = List.of(
                new AssociacaoPedidoProduto(2, pedido, BigDecimal.TEN, produto)
        );

        when(gateway.salvar(any(AssociacaoPedidoProduto.class)))
                .thenReturn(savedAssociacoes.get(0));

        List<AssociacaoPedidoProduto> result = usecase.salvarTodas(inputAssociacoes);

        assertEquals(savedAssociacoes, result);
        verify(gateway, times(1)).salvar(any(AssociacaoPedidoProduto.class));
    }

    @Test
    @DisplayName("Deve lidar com lista vazia de associações")
    void shouldHandleEmptyListOfAssociations() {
        List<AssociacaoPedidoProduto> inputAssociacoes = List.of();

        List<AssociacaoPedidoProduto> result = usecase.salvarTodas(inputAssociacoes);

        assertEquals(inputAssociacoes, result);
        verifyNoInteractions(gateway);
    }

    @Test
    @DisplayName("Deve lidar com associação nula na lista")
    void shouldHandleNullAssociationInTheList() {
        List<AssociacaoPedidoProduto> inputAssociacoes = java.util.Arrays.asList(
                new AssociacaoPedidoProduto(2, pedido, BigDecimal.TEN, produto),
                null
        );

        when(gateway.salvar(any(AssociacaoPedidoProduto.class)))
                .thenReturn(new AssociacaoPedidoProduto(2, pedido, BigDecimal.TEN, produto));

        List<AssociacaoPedidoProduto> result = usecase.salvarTodas(
                inputAssociacoes.stream().filter(a -> a != null).toList()
        );

        assertEquals(1, result.size());
        verify(gateway, times(1)).salvar(any(AssociacaoPedidoProduto.class));
    }
}
