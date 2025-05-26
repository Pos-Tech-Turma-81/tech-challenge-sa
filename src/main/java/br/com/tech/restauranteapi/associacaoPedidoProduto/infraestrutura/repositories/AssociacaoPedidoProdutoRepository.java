package br.com.tech.restauranteapi.associacaoPedidoProduto.infraestrutura.repositories;

import br.com.tech.restauranteapi.associacaoPedidoProduto.dominio.AssociacaoPedidoProduto;
import br.com.tech.restauranteapi.associacaoPedidoProduto.dominio.portas.repositories.AssociacaoPedidoProdutoRepositoryPort;
import br.com.tech.restauranteapi.associacaoPedidoProduto.infraestrutura.entidades.AssociacaoPedidoProdutoEntity;
import br.com.tech.restauranteapi.pedidos.infraestrutura.entidades.AssociacaoPedidoProdutoId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AssociacaoPedidoProdutoRepository implements AssociacaoPedidoProdutoRepositoryPort {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public AssociacaoPedidoProduto salvar(AssociacaoPedidoProduto associacao) {
        AssociacaoPedidoProdutoEntity entity = associacao.toEntity();
        if (entityManager.contains(entity) || entity.getId() != null) {
            entity = entityManager.merge(entity);
        } else {
            entityManager.persist(entity);
        }
        return AssociacaoPedidoProduto.builderAssociacao(entity);
    }

}
