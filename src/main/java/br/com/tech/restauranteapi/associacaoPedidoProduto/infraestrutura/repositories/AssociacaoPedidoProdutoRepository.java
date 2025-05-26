package br.com.tech.restauranteapi.associacaoPedidoProduto.infraestrutura.repositories;

import br.com.tech.restauranteapi.associacaoPedidoProduto.dominio.AssociacaoPedidoProduto;
import br.com.tech.restauranteapi.associacaoPedidoProduto.dominio.portas.repositories.AssociacaoPedidoProdutoRepositoryPort;
import br.com.tech.restauranteapi.associacaoPedidoProduto.infraestrutura.entidades.AssociacaoPedidoProdutoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AssociacaoPedidoProdutoRepository implements AssociacaoPedidoProdutoRepositoryPort {

    private final AssociacaoPedidoProdutoJpaRepository repository;

    @Override
    public AssociacaoPedidoProduto salvar(AssociacaoPedidoProduto associacao) {
        AssociacaoPedidoProdutoEntity assEntity = associacao.toEntity();
        AssociacaoPedidoProdutoEntity ass = repository.save(assEntity);

        return AssociacaoPedidoProduto.builderAssociacao(ass);
    }

}
