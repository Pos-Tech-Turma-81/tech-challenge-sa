package br.com.tech.restauranteapi.associacaoPedidoProduto.infraestrutura.repositories;

import br.com.tech.restauranteapi.associacaoPedidoProduto.infraestrutura.entidades.AssociacaoPedidoProdutoEntity;
import br.com.tech.restauranteapi.pedidos.infraestrutura.entidades.AssociacaoPedidoProdutoId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssociacaoPedidoProdutoJpaRepository extends JpaRepository<AssociacaoPedidoProdutoEntity, AssociacaoPedidoProdutoId> {
    List<AssociacaoPedidoProdutoEntity> findByIdPedidoId(Integer pedidoId);
}
