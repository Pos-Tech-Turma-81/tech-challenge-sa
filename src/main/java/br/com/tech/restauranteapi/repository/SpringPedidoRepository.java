package br.com.tech.restauranteapi.repository;

import br.com.tech.restauranteapi.gateway.entity.PedidoEntity;
import br.com.tech.restauranteapi.utils.enums.StatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SpringPedidoRepository extends JpaRepository<PedidoEntity, Integer> {

    @Query("SELECT p FROM PedidoEntity p where p.status = :status ORDER BY p.dataHoraInclusaoPedido ASC")
    Page<PedidoEntity> getByPedidosPreparacao(StatusEnum status, Pageable page);
}
