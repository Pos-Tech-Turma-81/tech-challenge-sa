package br.com.tech.restauranteapi.gateway.entity;

import br.com.tech.restauranteapi.gateway.domain.Pedido;
import br.com.tech.restauranteapi.utils.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "Pedidos")
public class PedidoEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", referencedColumnName = "id", nullable = true)
    private ClienteEntity clientId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusEnum status;

    @Column(name = "data_hora_inclusao_pedido")
    @CreatedDate
    private LocalDateTime dataHoraInclusaoPedido;

    @OneToMany(mappedBy = "id.pedido", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<AssociacaoPedidoProdutoEntity> associacoes;

    public Pedido toPedidosDomain(){
        return Pedido.builderPedidos(this);
    }
}

