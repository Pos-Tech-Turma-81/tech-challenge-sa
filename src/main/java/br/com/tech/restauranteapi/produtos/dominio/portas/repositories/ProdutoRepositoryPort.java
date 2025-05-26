package br.com.tech.restauranteapi.produtos.dominio.portas.repositories;

import br.com.tech.restauranteapi.produtos.dominio.Produto;
import br.com.tech.restauranteapi.utils.enums.CategoriaEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProdutoRepositoryPort {

    Produto salvar(Produto produto);
    Page<Produto> buscarPorCategoria(CategoriaEnum categoria, Pageable page);
    Produto buscarPorId(Integer id);
    void remover(Integer produtoId);
}
