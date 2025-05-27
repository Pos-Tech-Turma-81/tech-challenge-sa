package br.com.tech.restauranteapi.produtos.dominio.adaptadores.services;

import br.com.tech.restauranteapi.produtos.dominio.Produto;
import br.com.tech.restauranteapi.produtos.dominio.dtos.ProdutoDto;
import br.com.tech.restauranteapi.produtos.dominio.portas.repositories.ProdutoRepositoryPort;
import br.com.tech.restauranteapi.produtos.fixture.StringUtils;
import br.com.tech.restauranteapi.utils.enums.CategoriaEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceImplTest {

    @InjectMocks
    private ProdutoServiceImpl produtoService;

    @Mock
    private ProdutoRepositoryPort produtoRepository;


    @Test
    void deveSalvarProdutoComSucesso() {
        Integer id = 1;
        String nomeProduto = "X-Burger";
        String descricao = StringUtils.text(20);
        BigDecimal preco = new BigDecimal(30);
        ProdutoDto dto = new ProdutoDto(id, nomeProduto, CategoriaEnum.LANCHE, preco, descricao, null);
        Produto produto = Produto.builderProduto(dto);
        Produto produtoSalvo = Produto.builderProduto(dto);
        produtoSalvo.setId(1); // Simula ID gerado

        when(produtoRepository.salvar(any(Produto.class))).thenReturn(produtoSalvo);

        ProdutoDto resultado = produtoService.salvar(dto);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals(nomeProduto, resultado.getNome());
        assertEquals(descricao, resultado.getDescricao());
        assertEquals(preco, resultado.getPreco());
        verify(produtoRepository).salvar(any());
    }

    @Test
    void deveAlterarProdutoComSucesso() {
        Integer id = 5;
        String nomeProduto = "Suco";
        String descricao = StringUtils.text(20);
        BigDecimal preco = new BigDecimal(8);
        ProdutoDto dto = new ProdutoDto(id, nomeProduto, CategoriaEnum.BEBIDA, preco, descricao, null);
        Produto produto = Produto.builderProduto(dto);

        when(produtoRepository.buscarPorId(dto.getId())).thenReturn(produto);
        when(produtoRepository.salvar(any())).thenReturn(produto);

        ProdutoDto resultado = produtoService.alterar(dto);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals(nomeProduto, resultado.getNome());
        assertEquals(descricao, resultado.getDescricao());
        assertEquals(preco, resultado.getPreco());
        verify(produtoRepository).buscarPorId(5);
        verify(produtoRepository).salvar(any());
    }

    @Test
    void deveBuscarProdutosPorCategoria() {
        Integer id = 1;
        String nomeProduto = "Pizza";
        String descricao = StringUtils.text(20);
        BigDecimal preco = new BigDecimal(8);
        Produto produto = new Produto(id, nomeProduto, CategoriaEnum.LANCHE, preco, descricao, null);
        Page<Produto> page = new PageImpl<>(List.of(produto));
        Pageable pageable = PageRequest.of(0, 5);

        when(produtoRepository.buscarPorCategoria(CategoriaEnum.LANCHE, pageable)).thenReturn(page);

        Page<ProdutoDto> resultado = produtoService.buscarPorCategoria(CategoriaEnum.LANCHE, pageable);

        assertEquals(1, resultado.getTotalElements());
        assertEquals(nomeProduto, resultado.getContent().get(0).getNome());
        assertEquals(descricao, resultado.getContent().get(0).getDescricao());
        assertEquals(preco, resultado.getContent().get(0).getPreco());
        verify(produtoRepository).buscarPorCategoria(CategoriaEnum.LANCHE, pageable);
    }

    @Test
    void deveRemoverProduto() {
        produtoService.remover(7);
        verify(produtoRepository).remover(7);
    }
}
