package br.com.tech.restauranteapi.controller;

import br.com.tech.restauranteapi.controller.dtos.ProdutoDto;
import br.com.tech.restauranteapi.gateway.domain.Produto;
import br.com.tech.restauranteapi.usecase.ProdutoUsecase;
import br.com.tech.restauranteapi.utils.enums.CategoriaEnum;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class ProdutoControllerTest {

    @Mock
    private ProdutoUsecase usecase;

    @InjectMocks
    private ProdutoController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSalvarPost_Success() {
        ProdutoDto requestDto = ProdutoDto.builder()
                .id(null)
                .nome("Produto Teste")
                .categoria(CategoriaEnum.BEBIDA)
                .preco(BigDecimal.valueOf(10.0))
                .build();

        Produto produtoMock = Produto.builder()
                .id(1)
                .nome("Produto Teste")
                .categoria(CategoriaEnum.BEBIDA)
                .preco(BigDecimal.valueOf(10.0))
                .build();

        when(usecase.salvar(any(Produto.class))).thenReturn(produtoMock);

        ResponseEntity<ProdutoDto> response = controller.salvar(requestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(produtoMock.getId(), response.getBody().getId());
        assertEquals(produtoMock.getNome(), response.getBody().getNome());
        assertEquals(produtoMock.getCategoria().name(), response.getBody().getCategoria().name());
        assertEquals(produtoMock.getPreco(), response.getBody().getPreco());
    }

    @Test
    void testSalvarPut_Success() {
        ProdutoDto requestDto = ProdutoDto.builder()
                .id(1)
                .nome("Produto Atualizado")
                .categoria(CategoriaEnum.BEBIDA)
                .preco(BigDecimal.valueOf(15.0))
                .build();

        Produto produtoMock = Produto.builder()
                .id(1)
                .nome("Produto Atualizado")
                .categoria(CategoriaEnum.BEBIDA)
                .preco(BigDecimal.valueOf(15.0))
                .build();

        when(usecase.alterar(any(Produto.class))).thenReturn(produtoMock);

        ResponseEntity<ProdutoDto> response = controller.salvar(1, requestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(produtoMock.getId(), response.getBody().getId());
        assertEquals(produtoMock.getNome(), response.getBody().getNome());
        assertEquals(produtoMock.getCategoria().name(), response.getBody().getCategoria().name());
        assertEquals(produtoMock.getPreco(), response.getBody().getPreco());
    }

    @Test
    void testBuscar_Success() {
        Produto produto1 = Produto.builder()
                .id(1)
                .nome("Produto 1")
                .categoria(CategoriaEnum.BEBIDA)
                .preco(BigDecimal.valueOf(10.0))
                .build();

        Produto produto2 = Produto.builder()
                .id(2)
                .nome("Produto 2")
                .categoria(CategoriaEnum.BEBIDA)
                .preco(BigDecimal.valueOf(20.0))
                .build();

        List<Produto> produtos = List.of(produto1, produto2);
        Page<Produto> page = new PageImpl<>(produtos, PageRequest.of(0, 10), produtos.size());

        when(usecase.buscarPorCategoria(eq(CategoriaEnum.BEBIDA), any(Pageable.class))).thenReturn(page);

        ResponseEntity<Page<ProdutoDto>> response = controller.buscar("BEBIDA", PageRequest.of(0, 10));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getTotalElements());
        assertEquals("Produto 1", response.getBody().getContent().get(0).getNome());
        assertEquals("Produto 2", response.getBody().getContent().get(1).getNome());
    }

    @Test
    void testRemover_Success() {
        ResponseEntity<Void> response = controller.remover(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
