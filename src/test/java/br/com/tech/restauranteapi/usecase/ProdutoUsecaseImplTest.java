package br.com.tech.restauranteapi.usecase;

import br.com.tech.restauranteapi.gateway.ProdutoGateway;
import br.com.tech.restauranteapi.gateway.domain.Produto;
import br.com.tech.restauranteapi.usecase.impl.ProdutoUsecaseImpl;
import br.com.tech.restauranteapi.utils.enums.CategoriaEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;

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
    void testSalvar_SetsIdToNullAndSaves() {
        Produto produto = Produto.builder()
                .id(10)
                .nome("Água")
                .categoria(CategoriaEnum.BEBIDA)
                .preco(new BigDecimal("2.50"))
                .build();

        Produto produtoSalvo = Produto.builder()
                .id(20)
                .nome("Água")
                .categoria(CategoriaEnum.BEBIDA)
                .preco(new BigDecimal("2.50"))
                .build();

        when(gateway.salvar(any(Produto.class))).thenReturn(produtoSalvo);

        Produto result = usecase.salvar(produto);

        assertNotNull(result);
        assertEquals(produtoSalvo, result);
        // The id should be set to null before saving
        ArgumentCaptor<Produto> captor = ArgumentCaptor.forClass(Produto.class);
        verify(gateway).salvar(captor.capture());
        assertNull(captor.getValue().getId());
    }

    @Test
    void testAlterar_CallsBuscarPorIdAndSaves() {
        Produto produto = Produto.builder()
                .id(15)
                .nome("Coca-Cola")
                .categoria(CategoriaEnum.BEBIDA)
                .preco(new BigDecimal("7.50"))
                .build();

        Produto produtoSalvo = Produto.builder()
                .id(15)
                .nome("Coca-Cola")
                .categoria(CategoriaEnum.BEBIDA)
                .preco(new BigDecimal("7.50"))
                .build();

        when(gateway.buscarPorId(produto.getId())).thenReturn(produto);
        when(gateway.salvar(produto)).thenReturn(produtoSalvo);

        Produto result = usecase.alterar(produto);

        assertNotNull(result);
        assertEquals(produtoSalvo, result);
        verify(gateway).buscarPorId(produto.getId());
        verify(gateway).salvar(produto);
    }

    @Test
    void testBuscarPorCategoria_ReturnsPage() {
        CategoriaEnum categoria = CategoriaEnum.BEBIDA;
        Produto produto = Produto.builder()
                .id(1)
                .nome("Água")
                .categoria(categoria)
                .preco(new BigDecimal("2.50"))
                .build();

        Page<Produto> page = new PageImpl<>(List.of(produto), PageRequest.of(0, 10), 1);
        when(gateway.buscarPorCategoria(categoria, PageRequest.of(0, 10))).thenReturn(page);

        Page<Produto> result = usecase.buscarPorCategoria(categoria, PageRequest.of(0, 10));

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(produto, result.getContent().get(0));
        verify(gateway).buscarPorCategoria(categoria, PageRequest.of(0, 10));
    }

    @Test
    void testRemover_CallsGatewayRemover() {
        Integer produtoId = 99;
        doNothing().when(gateway).remover(produtoId);

        usecase.remover(produtoId);

        verify(gateway).remover(produtoId);
    }
}
