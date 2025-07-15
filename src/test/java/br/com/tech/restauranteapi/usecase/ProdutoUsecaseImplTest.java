package br.com.tech.restauranteapi.usecase;

import br.com.tech.restauranteapi.domain.Produto;
import br.com.tech.restauranteapi.gateway.ProdutoGateway;
import br.com.tech.restauranteapi.usecase.impl.ProdutoUsecaseImpl;
import br.com.tech.restauranteapi.utils.enums.CategoriaEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProdutoUsecaseImplTest {

    @Mock
    private ProdutoGateway gateway;

    @InjectMocks
    private ProdutoUsecaseImpl usecase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve salvar produto com sucesso")
    void shouldSaveProductSuccessfully() {
        Produto produto = new Produto(null, "Produto Teste", CategoriaEnum.BEBIDA, BigDecimal.TEN, "Descrição", null);
        Produto savedProduto = new Produto(1, "Produto Teste", CategoriaEnum.BEBIDA, BigDecimal.TEN, "Descrição", null);

        when(gateway.salvar(produto)).thenReturn(savedProduto);

        Produto result = usecase.salvar(produto);

        assertEquals(savedProduto, result);
        verify(gateway, times(1)).salvar(produto);
    }

    @Test
    @DisplayName("Deve alterar produto com sucesso")
    void shouldUpdateProductSuccessfully() {
        Produto produto = new Produto(1, "Produto Alterado", CategoriaEnum.BEBIDA, BigDecimal.TEN, "Descrição Alterada", null);

        when(gateway.buscarPorId(1)).thenReturn(produto);
        when(gateway.salvar(produto)).thenReturn(produto);

        Produto result = usecase.alterar(produto);

        assertEquals(produto, result);
        verify(gateway, times(1)).buscarPorId(1);
        verify(gateway, times(1)).salvar(produto);
    }

    @Test
    @DisplayName("Deve buscar produtos por categoria com sucesso")
    void shouldFindProductsByCategorySuccessfully() {
        Pageable pageable = Pageable.ofSize(10);
        Page<Produto> produtosPage = mock(Page.class);

        when(gateway.buscarPorCategoria(CategoriaEnum.BEBIDA, pageable)).thenReturn(produtosPage);

        Page<Produto> result = usecase.buscarPorCategoria(CategoriaEnum.BEBIDA, pageable);

        assertEquals(produtosPage, result);
        verify(gateway, times(1)).buscarPorCategoria(CategoriaEnum.BEBIDA, pageable);
    }

    @Test
    @DisplayName("Deve remover produto com sucesso")
    void shouldRemoveProductSuccessfully() {
        doNothing().when(gateway).remover(1);

        usecase.remover(1);

        verify(gateway, times(1)).remover(1);
    }

    @Test
    @DisplayName("Deve lançar exceção ao remover produto inexistente")
    void shouldThrowExceptionWhenRemovingNonExistentProduct() {
        doThrow(new IllegalArgumentException("Produto não encontrado")).when(gateway).remover(999);

        assertThrows(IllegalArgumentException.class, () -> usecase.remover(999));
        verify(gateway, times(1)).remover(999);
    }
}