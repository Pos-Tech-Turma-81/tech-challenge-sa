package br.com.tech.restauranteapi.produtos.dominio.adaptadores.services;

import br.com.tech.restauranteapi.produtos.dominio.Produto;
import br.com.tech.restauranteapi.produtos.dominio.dtos.ProdutoDto;
import br.com.tech.restauranteapi.produtos.dominio.portas.interfaces.ProdutoServicePort;
import br.com.tech.restauranteapi.produtos.dominio.portas.repositories.ProdutoRepositoryPort;
import br.com.tech.restauranteapi.utils.enums.CategoriaEnum;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ProdutoServiceImpl implements ProdutoServicePort {

    private final ProdutoRepositoryPort produtoRepository;


    @Override
    public Produto salvar(ProdutoDto produtoDto) {
        Produto produto = Produto.builderProduto(produtoDto).build();
        return produtoRepository.salvar(produto);
    }

    @Override
    public List<ProdutoDto> buscarPorCategoria(CategoriaEnum categoria) {
        List<Produto> produtos =
                produtoRepository.buscarPorCategoria(categoria);

        return produtos.stream().map(Produto::toProdutoDto)
                .collect(Collectors.toList());
    }

    @Override
    public void remover(UUID produtoId) {
        produtoRepository.remover(produtoId);
    }
}
