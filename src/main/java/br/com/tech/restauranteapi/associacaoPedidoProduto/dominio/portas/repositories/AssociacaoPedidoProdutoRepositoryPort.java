package br.com.tech.restauranteapi.associacaoPedidoProduto.dominio.portas.repositories;

import br.com.tech.restauranteapi.associacaoPedidoProduto.dominio.AssociacaoPedidoProduto;
import java.util.List;

public interface AssociacaoPedidoProdutoRepositoryPort {
    AssociacaoPedidoProduto salvar(AssociacaoPedidoProduto associacao);
}
