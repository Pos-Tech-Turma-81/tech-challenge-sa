package br.com.tech.restauranteapi.produtos.dominio.portas.interfaces;

import br.com.tech.restauranteapi.produtos.dominio.Produto;
import br.com.tech.restauranteapi.produtos.dominio.dtos.ProdutoDto;
import br.com.tech.restauranteapi.utils.enums.CategoriaEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ProdutoServicePort {

    ProdutoDto salvar(ProdutoDto produto);
    ProdutoDto alterar(ProdutoDto produto);
    Page<ProdutoDto> buscarPorCategoria(CategoriaEnum categoria, Pageable page);
    void remover(Integer produtoId);
}
