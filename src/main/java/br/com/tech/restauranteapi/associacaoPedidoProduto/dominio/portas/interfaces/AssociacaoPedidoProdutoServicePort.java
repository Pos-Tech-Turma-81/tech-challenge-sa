package br.com.tech.restauranteapi.associacaoPedidoProduto.dominio.portas.interfaces;

import br.com.tech.restauranteapi.associacaoPedidoProduto.dominio.AssociacaoPedidoProduto;

import java.util.List;

public interface AssociacaoPedidoProdutoServicePort {
    AssociacaoPedidoProduto salvar(AssociacaoPedidoProduto associacao);
}
