package br.com.tech.restauranteapi.produtos.infraestrutura.repositories;

import br.com.tech.restauranteapi.exceptions.NotFoundException;
import br.com.tech.restauranteapi.produtos.dominio.Produto;
import br.com.tech.restauranteapi.produtos.fixture.StringUtils;
import br.com.tech.restauranteapi.produtos.infraestrutura.entidades.ProdutoEntity;
import br.com.tech.restauranteapi.utils.enums.CategoriaEnum;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.apache.commons.text.CharacterPredicates.DIGITS;
import static org.apache.commons.text.CharacterPredicates.LETTERS;

class ProdutoRepositoryTest {

    private SpringProdutoRepository springProdutoRepository;
    private ProdutoRepository produtoRepository;

    @BeforeEach
    void setUp() {
        springProdutoRepository = mock(SpringProdutoRepository.class);
        produtoRepository = new ProdutoRepository(springProdutoRepository);
    }

    @Test
    void deveSalvarProdutoComSucesso() {
        String nomeProduto = "Café";
        String descricao = StringUtils.text(20);
        BigDecimal preco = new BigDecimal(10);
        Produto produto = new Produto(1,
                nomeProduto,
                CategoriaEnum.BEBIDA,
                preco,
                descricao,
                null);

        ProdutoEntity entity = produto.toEntity();

        when(springProdutoRepository.save(any())).thenReturn(entity);

        Produto resultado = produtoRepository.salvar(produto);

        assertNotNull(resultado);
        assertEquals(nomeProduto, resultado.getNome());
        assertEquals(preco, resultado.getPreco());
        assertEquals(descricao, resultado.getDescricao());
        verify(springProdutoRepository, times(1)).save(entity);
    }

    @Test
    void deveBuscarProdutoPorCategoria() {
        String nomeProduto = "Pizza";
        String descricao = StringUtils.text(20);
        BigDecimal preco = new BigDecimal(25);
        ProdutoEntity entity = new ProdutoEntity(1, nomeProduto, CategoriaEnum.LANCHE, preco, descricao, null);
        Page<ProdutoEntity> page = new PageImpl<>(List.of(entity));
        Pageable pageable = PageRequest.of(0, 10);

        when(springProdutoRepository.buscarPorCategoria(CategoriaEnum.LANCHE, pageable)).thenReturn(page);

        Page<Produto> resultado = produtoRepository.buscarPorCategoria(CategoriaEnum.LANCHE, pageable);

        assertEquals(1, resultado.getTotalElements());
        assertEquals(nomeProduto, resultado.getContent().get(0).getNome());
        assertEquals(preco, resultado.getContent().get(0).getPreco());
        assertEquals(descricao, resultado.getContent().get(0).getDescricao());
    }

    @Test
    void deveBuscarProdutoPorId() {
        String nomeProduto = "Refrigerante";
        String descricao = StringUtils.text(20);
        BigDecimal preco = new BigDecimal(7);
        ProdutoEntity entity = new ProdutoEntity(1, nomeProduto, CategoriaEnum.BEBIDA, preco, descricao, null);

        when(springProdutoRepository.findById(1)).thenReturn(Optional.of(entity));

        Produto produto = produtoRepository.buscarPorId(1);

        assertNotNull(produto);
        assertEquals(nomeProduto, produto.getNome());
        assertEquals(1, produto.getId());
        assertEquals(preco, produto.getPreco());
        assertEquals(CategoriaEnum.BEBIDA, produto.getCategoria());
        assertEquals(descricao, produto.getDescricao());
    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoEncontrado() {
        when(springProdutoRepository.findById(999)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> produtoRepository.buscarPorId(999));

        assertEquals("Não existe produto com o id 999.", exception.getMessage());
    }

    @Test
    void deveRemoverProdutoPorId() {
        produtoRepository.remover(1);
        verify(springProdutoRepository, times(1)).deleteById(1);
    }


}