package br.com.tech.restauranteapi.gateway.impl;

import br.com.tech.restauranteapi.exceptions.NotFoundException;
import br.com.tech.restauranteapi.gateway.domain.Produto;
import br.com.tech.restauranteapi.gateway.ProdutoGateway;
import br.com.tech.restauranteapi.gateway.entity.ProdutoEntity;
import br.com.tech.restauranteapi.repository.SpringProdutoRepository;
import br.com.tech.restauranteapi.utils.enums.CategoriaEnum;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import java.util.Optional;


import static java.lang.String.format;

@Component
@AllArgsConstructor
public class ProdutoGatewayImpl implements ProdutoGateway {

    private final SpringProdutoRepository springProdutoRepository;


    @Override
    public Produto salvar(Produto produto) {
        ProdutoEntity produtoResponse =
                this.springProdutoRepository
                        .save(produto.toEntity());

        return Produto.builderProduto(produtoResponse);
    }

    private Produto persistir(Produto produto) {
        ProdutoEntity produtoResponse =
                this.springProdutoRepository
                        .save(produto.toEntity());

        return Produto.builderProduto(produtoResponse);
    }

    @Override
    public Page<Produto> buscarPorCategoria(CategoriaEnum categoria, Pageable page) {
        Page<ProdutoEntity> produtosResponse =
                this.springProdutoRepository.buscarPorCategoria(categoria, page);

        return produtosResponse.map(ProdutoEntity::toProdutoDomain);
    }

    @Override
    public Produto buscarPorId(Integer id) {
        Optional<ProdutoEntity> produtoResponse = this.springProdutoRepository.findById(id);

        return produtoResponse.orElseThrow(() -> new NotFoundException(format(
                "Não existe produto com o id %s.", id))).toProdutoDomain();
    }

    @Override
    public void remover(Integer produtoId) {
        springProdutoRepository.deleteById(produtoId);
    }
}