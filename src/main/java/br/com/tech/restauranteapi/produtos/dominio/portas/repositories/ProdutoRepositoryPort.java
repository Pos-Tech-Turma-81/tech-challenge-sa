package br.com.tech.restauranteapi.produtos.dominio.portas.repositories;

import br.com.tech.restauranteapi.produtos.dominio.Produto;

import java.util.List;

public interface ProdutoRepositoryPort {

    List<Produto> buscarPorCategoria();
}
