package br.com.tech.restauranteapi.produtos.dominio.portas.repositories;

import br.com.tech.restauranteapi.produtos.dominio.Produto;
import br.com.tech.restauranteapi.utils.enums.CategoriaEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ProdutoRepositoryPort {

    Produto salvar(Produto produto);
    Page<Produto> buscarPorCategoria(CategoriaEnum categoria, Pageable page);
    void remover(Integer produtoId);
}
