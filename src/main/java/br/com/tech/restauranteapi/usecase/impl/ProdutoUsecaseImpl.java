package br.com.tech.restauranteapi.usecase.impl;

import br.com.tech.restauranteapi.domain.Produto;
import br.com.tech.restauranteapi.usecase.ProdutoUsecase;
import br.com.tech.restauranteapi.gateway.ProdutoGateway;
import br.com.tech.restauranteapi.utils.enums.CategoriaEnum;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ProdutoUsecaseImpl implements ProdutoUsecase {

    private final ProdutoGateway gateway;


    @Override
    public Produto salvar(Produto produto) {
        produto.setId(null);

        return gateway.salvar(produto);
    }


    @Override
    public Produto alterar(Produto produto) {
        gateway.buscarPorId(produto.getId());

        return gateway.salvar(produto);
    }

    @Override
    public Page<Produto> buscarPorCategoria(CategoriaEnum categoria, Pageable page) {
        return gateway.buscarPorCategoria(categoria, page);
    }

    @Override
    public void remover(Integer produtoId) {
        gateway.remover(produtoId);
    }
}
