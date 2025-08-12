package br.com.tech.restauranteapi.controller;

import br.com.tech.restauranteapi.controller.dtos.ProdutoDto;
import br.com.tech.restauranteapi.domain.Produto;
import br.com.tech.restauranteapi.presenter.ProdutoPresenter;
import br.com.tech.restauranteapi.usecase.ProdutoUsecase;
import br.com.tech.restauranteapi.utils.enums.CategoriaEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProdutoControllerTest {

    private final ProdutoUsecase usecase = mock(ProdutoUsecase.class);
    private final ProdutoController controller = new ProdutoController(usecase);

    @Test
    @DisplayName("Deve salvar produto com sucesso")
    void deveSalvarProdutoComSucesso() {
        ProdutoDto produtoDto = mock(ProdutoDto.class);
        Produto produtoMock = mock(Produto.class);
        Produto produtoSalvoMock = mock(Produto.class);
        ProdutoDto produtoDtoResponse = mock(ProdutoDto.class);

        try (MockedStatic<ProdutoPresenter> presenterMock = mockStatic(ProdutoPresenter.class)) {
            presenterMock.when(() -> ProdutoPresenter.fromToDomain(produtoDto)).thenReturn(produtoMock);
            when(usecase.salvar(produtoMock)).thenReturn(produtoSalvoMock);
            presenterMock.when(() -> ProdutoPresenter.toDto(produtoSalvoMock)).thenReturn(produtoDtoResponse);

            ProdutoDto response = controller.salvar(produtoDto);

            assertEquals(produtoDtoResponse, response);
            verify(usecase).salvar(produtoMock);
        }
    }

    @Test
    @DisplayName("Deve alterar produto com sucesso")
    void deveAlterarProdutoComSucesso() {
        Integer id = 1;
        ProdutoDto produtoDto = mock(ProdutoDto.class);
        Produto produtoMock = mock(Produto.class);
        Produto produtoAlteradoMock = mock(Produto.class);
        ProdutoDto produtoDtoResponse = mock(ProdutoDto.class);

        try (MockedStatic<ProdutoPresenter> presenterMock = mockStatic(ProdutoPresenter.class)) {
            doNothing().when(produtoDto).setId(id);
            presenterMock.when(() -> ProdutoPresenter.fromToDomain(produtoDto)).thenReturn(produtoMock);
            when(usecase.alterar(produtoMock)).thenReturn(produtoAlteradoMock);
            presenterMock.when(() -> ProdutoPresenter.toDto(produtoAlteradoMock)).thenReturn(produtoDtoResponse);

            ProdutoDto response = controller.salvar(id, produtoDto);

            assertEquals(produtoDtoResponse, response);
            verify(usecase).alterar(produtoMock);
        }
    }

    @Test
    @DisplayName("Deve buscar produtos por categoria")
    void deveBuscarProdutosPorCategoria() {
        String nomeCategoria = "LANCHE";
        Pageable pageable = PageRequest.of(0, 10);
        Produto produtoMock = mock(Produto.class);
        ProdutoDto produtoDtoMock = mock(ProdutoDto.class);
        Page<Produto> produtosPage = new PageImpl<>(List.of(produtoMock));

        try (MockedStatic<CategoriaEnum> categoriaEnumMock = mockStatic(CategoriaEnum.class);
             MockedStatic<ProdutoPresenter> presenterMock = mockStatic(ProdutoPresenter.class)) {
            CategoriaEnum categoriaEnum = CategoriaEnum.LANCHE;
            categoriaEnumMock.when(() -> CategoriaEnum.obterPorNome(nomeCategoria)).thenReturn(categoriaEnum);
            when(usecase.buscarPorCategoria(categoriaEnum, pageable)).thenReturn(produtosPage);
            presenterMock.when(() -> ProdutoPresenter.toDto(produtoMock)).thenReturn(produtoDtoMock);

            Page<ProdutoDto> response = controller.buscar(nomeCategoria, pageable);

            assertEquals(1, response.getTotalElements());
            assertEquals(produtoDtoMock, response.getContent().get(0));
            verify(usecase).buscarPorCategoria(categoriaEnum, pageable);
        }
    }

    @Test
    @DisplayName("Deve remover produto")
    void deveRemoverProduto() {
        Integer id = 1;
        doNothing().when(usecase).remover(id);

        controller.remover(id);

        verify(usecase).remover(id);
    }
}