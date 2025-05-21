package br.com.tech.restauranteapi.produtos.aplicacao.adaptadores.controllers;

import br.com.tech.restauranteapi.produtos.dominio.dtos.ProdutoDto;
import br.com.tech.restauranteapi.produtos.dominio.portas.interfaces.ProdutoServicePort;
import br.com.tech.restauranteapi.produtos.fixture.StringUtils;
import br.com.tech.restauranteapi.utils.enums.CategoriaEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProdutoController.class)
class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProdutoServicePort produtoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveSalvarProdutoComSucesso() throws Exception {
        Integer id = 1;
        String nomeProduto = "Água";
        String descricao = StringUtils.text(20);
        BigDecimal preco = new BigDecimal(3);
        ProdutoDto dto = new ProdutoDto(null, nomeProduto, CategoriaEnum.BEBIBA, preco, descricao, null);
        ProdutoDto salvo = new ProdutoDto(id, nomeProduto, CategoriaEnum.BEBIBA, preco, descricao, null);

        Mockito.when(produtoService.salvar(Mockito.any())).thenReturn(salvo);

        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.nome", is(nomeProduto)));
    }

    @Test
    void deveAlterarProdutoComSucesso() throws Exception {
        Integer id = 2;
        String nomeProduto = "Café";
        String descricao = StringUtils.text(20);
        BigDecimal preco = new BigDecimal(5);
        ProdutoDto dto = new ProdutoDto(id, nomeProduto, CategoriaEnum.BEBIBA, preco, descricao, null);

        Mockito.when(produtoService.alterar(Mockito.any())).thenReturn(dto);

        mockMvc.perform(put("/produtos/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.nome", is(nomeProduto)));
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

        mockMvc.perform(get("/produtos")
                        .param("nome_categoria", CategoriaEnum.LANCHE.toString())
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].nome", is(nomeProduto)));
    }

    @Test
    void deveRemoverProdutoComSucesso() throws Exception {
        mockMvc.perform(delete("/produtos/5"))
                .andExpect(status().isNoContent());

        Mockito.verify(produtoService).remover(5);
    }
}
