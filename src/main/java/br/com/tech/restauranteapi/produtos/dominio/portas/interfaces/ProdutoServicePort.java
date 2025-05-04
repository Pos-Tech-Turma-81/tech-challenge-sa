package br.com.tech.restauranteapi.produtos.dominio.portas.interfaces;

import br.com.tech.restauranteapi.produtos.dominio.Produto;
import br.com.tech.restauranteapi.produtos.dominio.dtos.ProdutoDto;
import br.com.tech.restauranteapi.utils.enums.CategoriaEnum;

import java.util.List;
import java.util.UUID;

public interface ProdutoServicePort {

    Produto salvar(ProdutoDto produto);
    List<ProdutoDto> buscarPorCategoria(CategoriaEnum categoria);
    void remover(UUID produtoId);
}
