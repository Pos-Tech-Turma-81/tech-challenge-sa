package br.com.tech.restauranteapi.associacaoPedidoProduto.infraestrutura.repositories;

import br.com.tech.restauranteapi.associacaoPedidoProduto.infraestrutura.entidades.AssociacaoPedidoProdutoEntity;
import br.com.tech.restauranteapi.gateway.entity.id.AssociacaoPedidoProdutoId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssociacaoPedidoProdutoJpaRepository extends JpaRepository<AssociacaoPedidoProdutoEntity, AssociacaoPedidoProdutoId> {
}
