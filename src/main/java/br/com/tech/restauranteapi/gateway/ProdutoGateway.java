package br.com.tech.restauranteapi.gateway;

import br.com.tech.restauranteapi.domain.Produto;
import br.com.tech.restauranteapi.utils.enums.CategoriaEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProdutoGateway {

    Produto salvar(Produto produto);
    Page<Produto> buscarPorCategoria(CategoriaEnum categoria, Pageable page);
    Produto buscarPorId(Integer id);
    void remover(Integer produtoId);
}
