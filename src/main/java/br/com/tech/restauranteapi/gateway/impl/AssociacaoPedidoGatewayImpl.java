package br.com.tech.restauranteapi.gateway.impl;

import br.com.tech.restauranteapi.gateway.AssociacaoPedidoProdutoGateway;
import br.com.tech.restauranteapi.domain.AssociacaoPedidoProduto;
import br.com.tech.restauranteapi.entity.AssociacaoPedidoProdutoEntity;
import br.com.tech.restauranteapi.presenter.AssociacaoPedidoProdutoPresenter;
import br.com.tech.restauranteapi.repository.AssociacaoPedidoProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AssociacaoPedidoGatewayImpl implements AssociacaoPedidoProdutoGateway {

    private final AssociacaoPedidoProdutoRepository repository;

    @Override
    public AssociacaoPedidoProduto salvar(AssociacaoPedidoProduto associacao) {
        AssociacaoPedidoProdutoEntity assEntity = AssociacaoPedidoProdutoPresenter.toEntity(associacao);
        AssociacaoPedidoProdutoEntity ass = repository.save(assEntity);

        return AssociacaoPedidoProdutoPresenter.fromToDomain(ass);
    }

}
