package br.com.tech.restauranteapi.gateway;

import br.com.tech.restauranteapi.exceptions.NotFoundException;
import br.com.tech.restauranteapi.gateway.domain.Produto;
import br.com.tech.restauranteapi.gateway.entity.ProdutoEntity;
import br.com.tech.restauranteapi.gateway.impl.ProdutoGatewayImpl;
import br.com.tech.restauranteapi.repository.SpringProdutoRepository;
import br.com.tech.restauranteapi.utils.enums.CategoriaEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProdutoGatewayTest {

    @Mock
    private SpringProdutoRepository springProdutoRepository;

    @InjectMocks
    private ProdutoGatewayImpl gateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSalvar_Success() {
        Produto produto = mock(Produto.class);
        ProdutoEntity entity = mock(ProdutoEntity.class);
        ProdutoEntity savedEntity = mock(ProdutoEntity.class);
        Produto mappedProduto = mock(Produto.class);

        when(produto.toEntity()).thenReturn(entity);
        when(springProdutoRepository.save(entity)).thenReturn(savedEntity);
        try (MockedStatic<Produto> mockedStatic = mockStatic(Produto.class)) {
            mockedStatic.when(() -> Produto.builderProduto(savedEntity)).thenReturn(mappedProduto);

            Produto result = gateway.salvar(produto);

            assertNotNull(result);
            assertEquals(mappedProduto, result);
            verify(produto).toEntity();
            verify(springProdutoRepository).save(entity);
            mockedStatic.verify(() -> Produto.builderProduto(savedEntity));
        }
    }

    @Test
    void testBuscarPorCategoria_Success() {
        CategoriaEnum categoria = CategoriaEnum.BEBIDA;
        ProdutoEntity entity1 = mock(ProdutoEntity.class);
        ProdutoEntity entity2 = mock(ProdutoEntity.class);
        Produto domain1 = mock(Produto.class);
        Produto domain2 = mock(Produto.class);

        Page<ProdutoEntity> entityPage = new PageImpl<>(List.of(entity1, entity2), PageRequest.of(0, 10), 2);
        when(springProdutoRepository.buscarPorCategoria(categoria, PageRequest.of(0, 10))).thenReturn(entityPage);
        when(entity1.toProdutoDomain()).thenReturn(domain1);
        when(entity2.toProdutoDomain()).thenReturn(domain2);

        Page<Produto> result = gateway.buscarPorCategoria(categoria, PageRequest.of(0, 10));

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(domain1, result.getContent().get(0));
        assertEquals(domain2, result.getContent().get(1));
        verify(springProdutoRepository).buscarPorCategoria(categoria, PageRequest.of(0, 10));
        verify(entity1).toProdutoDomain();
        verify(entity2).toProdutoDomain();
    }

    @Test
    void testBuscarPorId_Found() {
        Integer id = 7;
        ProdutoEntity entity = mock(ProdutoEntity.class);
        Produto mappedProduto = mock(Produto.class);

        when(springProdutoRepository.findById(id)).thenReturn(Optional.of(entity));
        when(entity.toProdutoDomain()).thenReturn(mappedProduto);

        Produto result = gateway.buscarPorId(id);

        assertNotNull(result);
        assertEquals(mappedProduto, result);
        verify(springProdutoRepository).findById(id);
        verify(entity).toProdutoDomain();
    }

    @Test
    void testBuscarPorId_NotFound() {
        Integer id = 999;
        when(springProdutoRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> gateway.buscarPorId(id)
        );
        assertTrue(exception.getMessage().contains(id.toString()));
        verify(springProdutoRepository).findById(id);
    }

    @Test
    void testRemover_CallsRepository() {
        Integer produtoId = 42;
        doNothing().when(springProdutoRepository).deleteById(produtoId);

        gateway.remover(produtoId);

        verify(springProdutoRepository).deleteById(produtoId);
    }
}
