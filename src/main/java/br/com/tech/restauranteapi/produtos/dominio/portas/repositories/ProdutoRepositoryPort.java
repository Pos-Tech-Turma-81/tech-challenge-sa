package br.com.tech.restauranteapi.produtos.dominio.portas.repositories;

import br.com.tech.restauranteapi.produtos.dominio.Produto;
import br.com.tech.restauranteapi.utils.enums.CategoriaEnum;

import java.util.List;
import java.util.UUID;

public interface ProdutoRepositoryPort {

    Produto salvar(Produto produto);
    List<Produto> buscarPorCategoria(CategoriaEnum categoria);
    void remover(UUID produtoId);
}
