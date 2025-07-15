package br.com.tech.restauranteapi.controller;

import br.com.tech.restauranteapi.domain.Produto;
import br.com.tech.restauranteapi.controller.dtos.ProdutoDto;
import br.com.tech.restauranteapi.exceptions.NotFoundException;
import br.com.tech.restauranteapi.utils.enums.CategoriaEnum;
import br.com.tech.restauranteapi.usecase.ProdutoUsecase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ProdutoControllerTest {

    private final ProdutoController produtoController;
    private final ProdutoUsecase usecase;

    public ProdutoControllerTest() {
        this.usecase = Mockito.mock(ProdutoUsecase.class);
        this.produtoController = new ProdutoController(usecase);
    }

    @Test
    @DisplayName("Deve cadastrar novo produto com sucesso")
    void deveCadastrarNovoProdutoComSucesso() {
        ProdutoDto produtoDto = ProdutoDto.builder()
                .nome("Produto Teste")
                .descricao("Descrição Teste")
                .categoria(CategoriaEnum.BEBIDA)
                .preco(BigDecimal.valueOf(10.99))
                .build();

        Produto produtoMock = Produto.builderProduto(produtoDto);
        Produto produtoResponseMock = Produto.builderProduto(produtoDto);
        produtoResponseMock.setId(1);

        when(usecase.salvar(produtoMock)).thenReturn(produtoResponseMock);

        ResponseEntity<ProdutoDto> response = produtoController.salvar(produtoDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(produtoResponseMock.toProdutoDto(), response.getBody());
        verify(usecase).salvar(produtoMock);
    }

    @Test
    @DisplayName("Deve alterar produto com sucesso")
    void deveAlterarProdutoComSucesso() {
        Integer produtoId = 1;
        ProdutoDto produtoDto = ProdutoDto.builder()
                .id(produtoId)
                .nome("Produto Alterado")
                .descricao("Descrição Alterada")
                .categoria(CategoriaEnum.BEBIDA)
                .preco(BigDecimal.valueOf(15.99))
                .build();

        Produto produtoMock = Produto.builderProduto(produtoDto);
        Produto produtoResponseMock = Produto.builderProduto(produtoDto);

        when(usecase.alterar(produtoMock)).thenReturn(produtoResponseMock);

        ResponseEntity<ProdutoDto> response = produtoController.salvar(produtoId, produtoDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(produtoResponseMock.toProdutoDto(), response.getBody());
        verify(usecase).alterar(produtoMock);
    }

    @Test
    @DisplayName("Deve buscar produtos por categoria com sucesso")
    void deveBuscarProdutosPorCategoriaComSucesso() {
        String nomeCategoria = "BEBIDA";
        Pageable pageable = PageRequest.of(0, 10);
        Produto produtoMock = Produto.builder()
                .id(1)
                .nome("Produto Teste")
                .descricao("Descrição Teste")
                .categoria(CategoriaEnum.BEBIDA)
                .preco(BigDecimal.valueOf(10.99))
                .build();

        Page<Produto> produtosMock = new PageImpl<>(List.of(produtoMock));

        when(usecase.buscarPorCategoria(CategoriaEnum.BEBIDA, pageable)).thenReturn(produtosMock);

        ResponseEntity<Page<ProdutoDto>> response = produtoController.buscar(nomeCategoria, pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getTotalElements());
        assertEquals(produtoMock.toProdutoDto(), response.getBody().getContent().get(0));
        verify(usecase).buscarPorCategoria(CategoriaEnum.BEBIDA, pageable);
    }

    @Test
    @DisplayName("Deve retornar erro ao buscar produtos com categoria inválida")
    void deveRetornarErroAoBuscarProdutosComCategoriaInvalida() {
        String nomeCategoria = "INVALIDA";
        Pageable pageable = PageRequest.of(0, 10);

        assertThrows(NotFoundException.class, () -> produtoController.buscar(nomeCategoria, pageable));
    }

    @Test
    @DisplayName("Deve remover produto com sucesso")
    void deveRemoverProdutoComSucesso() {
        Integer produtoId = 1;

        doNothing().when(usecase).remover(produtoId);

        ResponseEntity<Void> response = produtoController.remover(produtoId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(usecase).remover(produtoId);
    }
}