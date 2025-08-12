package br.com.tech.restauranteapi.api;

import br.com.tech.restauranteapi.controller.ProdutoController;
import br.com.tech.restauranteapi.controller.dtos.ProdutoDto;
import br.com.tech.restauranteapi.utils.enums.CategoriaEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProdutoApiTest {

    @Mock
    private ProdutoController produtoController;

    @InjectMocks
    private ProdutoApi produtoApi;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve retornar CREATED ao cadastrar produto")
    void deveRetornarCreatedAoCadastrarProduto() {
        ProdutoDto produtoDto = ProdutoDto.builder()
                .nome("Produto Teste")
                .descricao("Descrição")
                .categoria(CategoriaEnum.BEBIDA)
                .preco(BigDecimal.valueOf(10.0))
                .build();

        ProdutoDto produtoDtoResponse = ProdutoDto.builder()
                .id(1)
                .nome("Produto Teste")
                .descricao("Descrição")
                .categoria(CategoriaEnum.BEBIDA)
                .preco(BigDecimal.valueOf(10.0))
                .build();

        when(produtoController.salvar(produtoDto)).thenReturn(produtoDtoResponse);

        ResponseEntity<ProdutoDto> response = produtoApi.salvar(produtoDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(produtoDtoResponse, response.getBody());
        verify(produtoController).salvar(produtoDto);
    }

    @Test
    @DisplayName("Deve retornar CREATED ao alterar produto")
    void deveRetornarCreatedAoAlterarProduto() {
        Integer id = 1;
        ProdutoDto produtoDto = ProdutoDto.builder()
                .nome("Produto Alterado")
                .descricao("Nova Desc")
                .categoria(CategoriaEnum.BEBIDA)
                .preco(BigDecimal.valueOf(12.0))
                .build();

        ProdutoDto produtoDtoResponse = ProdutoDto.builder()
                .id(id)
                .nome("Produto Alterado")
                .descricao("Nova Desc")
                .categoria(CategoriaEnum.BEBIDA)
                .preco(BigDecimal.valueOf(12.0))
                .build();

        when(produtoController.salvar(id, produtoDto)).thenReturn(produtoDtoResponse);

        ResponseEntity<ProdutoDto> response = produtoApi.salvar(id, produtoDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(produtoDtoResponse, response.getBody());
        verify(produtoController).salvar(id, produtoDto);
    }

    @Test
    @DisplayName("Deve retornar OK ao buscar produtos")
    void deveRetornarOkAoBuscarProdutos() {
        String nomeCategoria = "BEBIDA";
        Pageable pageable = PageRequest.of(0, 10);

        ProdutoDto produtoDto = ProdutoDto.builder()
                .id(1)
                .nome("Produto Teste")
                .descricao("Descrição")
                .categoria(CategoriaEnum.BEBIDA)
                .preco(BigDecimal.valueOf(10.0))
                .build();

        Page<ProdutoDto> page = new PageImpl<>(List.of(produtoDto));

        when(produtoController.buscar(nomeCategoria, pageable)).thenReturn(page);

        ResponseEntity<Page<ProdutoDto>> response = produtoApi.buscar(nomeCategoria, pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getTotalElements());
        verify(produtoController).buscar(nomeCategoria, pageable);
    }

    @Test
    @DisplayName("Deve retornar NO_CONTENT ao remover produto")
    void deveRetornarNoContentAoRemoverProduto() {
        Integer id = 1;

        doNothing().when(produtoController).remover(id);

        ResponseEntity<Void> response = produtoApi.remover(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(produtoController).remover(id);
    }
}