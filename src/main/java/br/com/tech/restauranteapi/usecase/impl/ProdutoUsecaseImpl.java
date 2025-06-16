package br.com.tech.restauranteapi.usecase.impl;

import br.com.tech.restauranteapi.gateway.domain.Produto;
import br.com.tech.restauranteapi.controller.dtos.ProdutoDto;
import br.com.tech.restauranteapi.usecase.ProdutoUsecase;
import br.com.tech.restauranteapi.gateway.ProdutoGateway;
import br.com.tech.restauranteapi.utils.enums.CategoriaEnum;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ProdutoUsecaseImpl implements ProdutoUsecase {

    private final ProdutoGateway produtoRepository;


    @Override
    public Produto salvar(Produto produto) {
        produto.setId(null);

        return produtoRepository.salvar(produto);
    }


    @Override
    public Produto alterar(Produto produto) {
        produtoRepository.buscarPorId(produto.getId());

        return produtoRepository.salvar(produto);
    }

    @Override
    public Page<Produto> buscarPorCategoria(CategoriaEnum categoria, Pageable page) {
        return produtoRepository.buscarPorCategoria(categoria, page);
    }

    @Override
    public void remover(Integer produtoId) {
        produtoRepository.remover(produtoId);
    }
}
