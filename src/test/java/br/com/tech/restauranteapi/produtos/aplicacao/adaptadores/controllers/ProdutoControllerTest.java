package br.com.tech.restauranteapi.produtos.aplicacao.adaptadores.controllers;

import br.com.tech.restauranteapi.produtos.dominio.adaptadores.services.ProdutoServiceImpl;
import br.com.tech.restauranteapi.produtos.dominio.dtos.ProdutoDto;
import br.com.tech.restauranteapi.produtos.dominio.portas.interfaces.ProdutoServicePort;
import br.com.tech.restauranteapi.produtos.dominio.portas.repositories.ProdutoRepositoryPort;
import br.com.tech.restauranteapi.produtos.fixture.StringUtils;
import br.com.tech.restauranteapi.utils.enums.CategoriaEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ProdutoControllerTest {
    @InjectMocks
    private ProdutoController controller;
    @Mock
    private ProdutoServicePort produtoService;

    @Test
    void deveSalvarProdutoComSucesso() throws Exception {
        Integer id = 1;
        String nomeProduto = "Água";
        String descricao = StringUtils.text(20);
        BigDecimal preco = new BigDecimal(3);
        ProdutoDto dto = new ProdutoDto(null, nomeProduto, CategoriaEnum.BEBIDA, preco, descricao, null);
        ProdutoDto salvo = new ProdutoDto(id, nomeProduto, CategoriaEnum.BEBIDA, preco, descricao, null);

        Mockito.when(produtoService.salvar(Mockito.any())).thenReturn(salvo);

        ResponseEntity<ProdutoDto> response = controller.salvar(dto);

        assertEquals(id, response.getBody().getId());
        assertEquals(nomeProduto, response.getBody().getNome());

    }

    @Test
    void deveAlterarProdutoComSucesso() throws Exception {
        Integer id = 2;
        String nomeProduto = "Café";
        String descricao = StringUtils.text(20);
        BigDecimal preco = new BigDecimal(5);
        ProdutoDto dto = new ProdutoDto(id, nomeProduto, CategoriaEnum.BEBIDA, preco, descricao, null);

        Mockito.when(produtoService.alterar(Mockito.any())).thenReturn(dto);

        ResponseEntity<ProdutoDto> response = controller.salvar(2, dto);

        assertEquals(id, response.getBody().getId());
        assertEquals(nomeProduto, response.getBody().getNome());
    }

    @Test
    void deveBuscarProdutosPorCategoria() throws Exception {
        Integer id = 2;
        String nomeProduto = "Pizza";
        String descricao = StringUtils.text(20);
        BigDecimal preco = new BigDecimal(30.0);

        ProdutoDto produto = new ProdutoDto(id,nomeProduto, CategoriaEnum.LANCHE, preco, descricao, null);
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProdutoDto> page = new PageImpl<>(List.of(produto), pageable, 1);

        Mockito.when(produtoService.buscarPorCategoria(CategoriaEnum.LANCHE, pageable))
                .thenReturn(page);

        ResponseEntity<Page<ProdutoDto>> response = controller.buscar(CategoriaEnum.LANCHE.toString(), PageRequest.of(0, 10));

        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getTotalElements());
        assertEquals(id, response.getBody().getContent().get(0).getId());
        assertEquals(nomeProduto, response.getBody().getContent().get(0).getNome());
    }

    @Test
    void deveRemoverProdutoComSucesso() throws Exception {

        controller.remover(5);

        Mockito.verify(produtoService).remover(5);
    }
}
