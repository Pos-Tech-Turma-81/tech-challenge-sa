package br.com.tech.restauranteapi.repository;


import br.com.tech.restauranteapi.gateway.entity.AssociacaoPedidoProdutoEntity;
import br.com.tech.restauranteapi.gateway.entity.id.AssociacaoPedidoProdutoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociacaoPedidoProdutoRepository extends JpaRepository<AssociacaoPedidoProdutoEntity, AssociacaoPedidoProdutoId> {
}