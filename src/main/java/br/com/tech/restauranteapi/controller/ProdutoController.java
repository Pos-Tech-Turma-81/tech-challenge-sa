package br.com.tech.restauranteapi.controller;

import br.com.tech.restauranteapi.controller.dtos.ProdutoDto;
import br.com.tech.restauranteapi.domain.Produto;
import br.com.tech.restauranteapi.presenter.ProdutoPresenter;
import br.com.tech.restauranteapi.usecase.ProdutoUsecase;
import br.com.tech.restauranteapi.utils.enums.CategoriaEnum;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProdutoController {

    private final ProdutoUsecase usecase;

    public ProdutoDto salvar(ProdutoDto produtoDto) {
        Produto produto = ProdutoPresenter.fromToDomain(produtoDto);
        Produto produtoResponse = usecase.salvar(produto);
        return ProdutoPresenter.toDto(produtoResponse);
    }

    public ProdutoDto salvar(Integer id, ProdutoDto produtoDto) {
        produtoDto.setId(id);
        Produto produto = ProdutoPresenter.fromToDomain(produtoDto);
        Produto produtoResponse = usecase.alterar(produto);
        return ProdutoPresenter.toDto(produtoResponse);
    }

    public Page<ProdutoDto> buscar(String nomeCategoria, Pageable pageable) {
        Page<Produto> produtosResponse =
                usecase.buscarPorCategoria(CategoriaEnum.obterPorNome(nomeCategoria), pageable);
        return produtosResponse.map(ProdutoPresenter::toDto);
    }

    public void remover(Integer id) {
        usecase.remover(id);
    }
}