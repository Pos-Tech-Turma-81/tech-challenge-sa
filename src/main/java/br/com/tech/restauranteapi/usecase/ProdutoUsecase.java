package br.com.tech.restauranteapi.usecase;

import br.com.tech.restauranteapi.controller.dtos.ProdutoDto;
import br.com.tech.restauranteapi.gateway.domain.Produto;
import br.com.tech.restauranteapi.utils.enums.CategoriaEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProdutoUsecase {

    Produto salvar(Produto produto);
    Produto alterar(Produto produto);
    Page<Produto> buscarPorCategoria(CategoriaEnum categoria, Pageable page);

    void remover(Integer produtoId);
}
