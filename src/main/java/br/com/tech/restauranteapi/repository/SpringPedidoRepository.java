package br.com.tech.restauranteapi.repository;

import br.com.tech.restauranteapi.entity.PedidoEntity;
import br.com.tech.restauranteapi.utils.enums.StatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SpringPedidoRepository extends JpaRepository<PedidoEntity, Integer> {

    @Query("SELECT p FROM PedidoEntity p where p.status = :status ORDER BY p.dataHoraInclusaoPedido ASC")
    Page<PedidoEntity> getByPedidosPreparacao(StatusEnum status, Pageable page);

    @Query("SELECT p FROM PedidoEntity p LEFT JOIN FETCH p.associacoes a LEFT JOIN FETCH p.clientId c WHERE p.id = :id")
    Optional<PedidoEntity> findByIdWithAssociacoesAndCliente(@Param("id") Integer id);
}
