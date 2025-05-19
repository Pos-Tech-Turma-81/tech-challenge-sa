package br.com.tech.restauranteapi.pedidos.infraestrutura.entidades;

import br.com.tech.restauranteapi.pedidos.dominio.Pedidos;
import br.com.tech.restauranteapi.clientes.dominio.dtos.ClienteEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Pedidos")
public class PedidosEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", referencedColumnName = "id", nullable = true)
    private ClienteEntity clientId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private String status;

    @Column(name = "data_hora_inclusao_pedido")
    private Timestamp dataHoraInclusaoPedido;

    public Pedidos toPedidosDomain(){
        return Pedidos.builderPedidos(this);
    }
}

