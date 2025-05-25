package br.com.tech.restauranteapi.pedidos.infraestrutura.repositories;

import br.com.tech.restauranteapi.pedidos.infraestrutura.entidades.PedidosEntity;
import br.com.tech.restauranteapi.utils.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface SpringPedidosRepository extends JpaRepository<PedidosEntity, Integer> {

    @Query("""
    SELECT p FROM PedidosEntity p
    WHERE (:status IS NULL OR p.status = :status)
      AND (:clienteId IS NULL OR p.clientId.id = :clienteId)
    ORDER BY p.dataHoraInclusaoPedido ASC
""")
    List<PedidosEntity> listarPedidos(
            @Param("status") StatusEnum status,
            @Param("clienteId") Integer clienteId
    );
}
