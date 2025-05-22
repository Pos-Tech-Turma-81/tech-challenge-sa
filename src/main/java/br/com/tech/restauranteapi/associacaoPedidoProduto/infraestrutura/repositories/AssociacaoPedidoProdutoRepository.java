package br.com.tech.restauranteapi.associacaoPedidoProduto.infraestrutura.repositories;

import br.com.tech.restauranteapi.associacaoPedidoProduto.dominio.AssociacaoPedidoProduto;
import br.com.tech.restauranteapi.associacaoPedidoProduto.dominio.portas.repositories.AssociacaoPedidoProdutoRepositoryPort;
import br.com.tech.restauranteapi.associacaoPedidoProduto.infraestrutura.entidades.AssociacaoPedidoProdutoEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;


public class AssociacaoPedidoProdutoRepository implements AssociacaoPedidoProdutoRepositoryPort {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public AssociacaoPedidoProduto salvar(AssociacaoPedidoProduto associacao) {
        AssociacaoPedidoProdutoEntity entity = associacao.toEntity();
        entity = entityManager.merge(entity);
        return AssociacaoPedidoProduto.builderAssociacao(entity);
    }

}
