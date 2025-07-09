package br.com.tech.restauranteapi.usecase;

import br.com.tech.restauranteapi.gateway.domain.AssociacaoPedidoProduto;

import java.util.List;

public interface AssociacaoPedidoProdutoUsecase {
    public List<AssociacaoPedidoProduto> salvarTodas(List<AssociacaoPedidoProduto> associacoes);
}
