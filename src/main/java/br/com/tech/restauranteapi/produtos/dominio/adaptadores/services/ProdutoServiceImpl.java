package br.com.tech.restauranteapi.produtos.dominio.adaptadores.services;

import br.com.tech.restauranteapi.produtos.dominio.Produto;
import br.com.tech.restauranteapi.produtos.dominio.dtos.ProdutoDto;
import br.com.tech.restauranteapi.produtos.dominio.portas.interfaces.ProdutoServicePort;
import br.com.tech.restauranteapi.produtos.dominio.portas.repositories.ProdutoRepositoryPort;
import br.com.tech.restauranteapi.utils.enums.CategoriaEnum;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ProdutoServiceImpl implements ProdutoServicePort {

    private final ProdutoRepositoryPort produtoRepository;


    @Override
    public ProdutoDto salvar(ProdutoDto produtoDto) {
        produtoDto.setId(null);
        Produto produto = Produto.builderProduto(produtoDto);

        return produtoRepository.salvar(produto).toProdutoDto();
    }

    @Override
    public ProdutoDto alterar(ProdutoDto produtoDto) {
        produtoRepository.buscarPorId(produtoDto.getId());
        Produto produto = Produto.builderProduto(produtoDto);
        return produtoRepository.salvar(produto).toProdutoDto();
    }

    @Override
    public Page<ProdutoDto> buscarPorCategoria(CategoriaEnum categoria, Pageable page) {
        Page<Produto> produtos =
                produtoRepository.buscarPorCategoria(categoria, page);

        return produtos.map(Produto::toProdutoDto);
    }

    @Override
    public void remover(Integer produtoId) {
        produtoRepository.remover(produtoId);
    }
}
