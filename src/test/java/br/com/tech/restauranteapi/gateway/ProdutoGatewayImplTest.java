package br.com.tech.restauranteapi.gateway;

import br.com.tech.restauranteapi.domain.Produto;
import br.com.tech.restauranteapi.entity.ProdutoEntity;
import br.com.tech.restauranteapi.utils.enums.CategoriaEnum;
import br.com.tech.restauranteapi.exceptions.NotFoundException;
import br.com.tech.restauranteapi.gateway.impl.ProdutoGatewayImpl;
import br.com.tech.restauranteapi.repository.SpringProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProdutoGatewayImplTest {

    @Mock
    private SpringProdutoRepository springProdutoRepository;

    @InjectMocks
    private ProdutoGatewayImpl produtoGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("Salvar produto")
    class SalvarProduto {

        @Test
        @DisplayName("Deve salvar produto com sucesso")
        void deveSalvarProdutoComSucesso() {
            Produto produto = mock(Produto.class);
            ProdutoEntity produtoEntity = mock(ProdutoEntity.class);
            ProdutoEntity savedEntity = mock(ProdutoEntity.class);
            Produto expectedProduto = mock(Produto.class);

            when(produto.toEntity()).thenReturn(produtoEntity);
            when(produto.getPreco()).thenReturn(java.math.BigDecimal.valueOf(10.0));
            when(produto.getNome()).thenReturn("Produto Teste");
            when(produto.getDescricao()).thenReturn("Descrição Teste");
            when(produto.getCategoria()).thenReturn(br.com.tech.restauranteapi.utils.enums.CategoriaEnum.BEBIDA);

            when(springProdutoRepository.save(produtoEntity)).thenReturn(savedEntity);

            try (org.mockito.MockedStatic<Produto> mockedStatic = mockStatic(Produto.class)) {
                mockedStatic.when(() -> Produto.builderProduto(savedEntity)).thenReturn(expectedProduto);

                Produto result = produtoGateway.salvar(produto);

                assertEquals(expectedProduto, result);
                verify(springProdutoRepository).save(produtoEntity);
            }
        }
    }

    @Nested
    @DisplayName("Buscar produto por categoria")
    class BuscarProdutoPorCategoria {

        @Test
        @DisplayName("Deve retornar produtos por categoria com sucesso")
        void deveRetornarProdutosPorCategoriaComSucesso() {
            Pageable pageable = mock(Pageable.class);
            Page<ProdutoEntity> produtoEntities = mock(Page.class);
            Page<Produto> expectedProdutos = mock(Page.class);

            when(springProdutoRepository.buscarPorCategoria(CategoriaEnum.BEBIDA, pageable)).thenReturn(produtoEntities);
            when(produtoEntities.map(any(java.util.function.Function.class))).thenReturn(expectedProdutos);

            Page<Produto> result = produtoGateway.buscarPorCategoria(CategoriaEnum.BEBIDA, pageable);

            assertEquals(expectedProdutos, result);
        }
    }

    @Nested
    @DisplayName("Buscar produto por ID")
    class BuscarProdutoPorId {

        @Test
        @DisplayName("Deve retornar produto quando encontrado")
        void deveRetornarProdutoQuandoEncontrado() {
            Integer id = 1;
            ProdutoEntity produtoEntity = mock(ProdutoEntity.class);
            Produto expectedProduto = mock(Produto.class);

            when(springProdutoRepository.findById(id)).thenReturn(Optional.of(produtoEntity));
            when(produtoEntity.toProdutoDomain()).thenReturn(expectedProduto);

            Produto result = produtoGateway.buscarPorId(id);

            assertEquals(expectedProduto, result);
        }

        @Test
        @DisplayName("Deve lançar exceção quando produto não encontrado")
        void deveLancarExcecaoQuandoProdutoNaoEncontrado() {
            Integer id = 1;

            when(springProdutoRepository.findById(id)).thenReturn(Optional.empty());

            NotFoundException exception = assertThrows(NotFoundException.class, () -> produtoGateway.buscarPorId(id));

            assertEquals("Não existe produto com o id 1.", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Remover produto")
    class RemoverProduto {

        @Test
        @DisplayName("Deve remover produto com sucesso")
        void deveRemoverProdutoComSucesso() {
            Integer produtoId = 1;

            doNothing().when(springProdutoRepository).deleteById(produtoId);

            produtoGateway.remover(produtoId);

            verify(springProdutoRepository).deleteById(produtoId);
        }
    }
}