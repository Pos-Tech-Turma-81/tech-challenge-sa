package br.com.tech.restauranteapi.pedidos.dominio;

import br.com.tech.restauranteapi.pedidos.dominio.dtos.PedidosDto;
import br.com.tech.restauranteapi.pedidos.infraestrutura.entidades.PedidosEntity;
import br.com.tech.restauranteapi.clientes.dominio.Cliente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pedidos {

    private Integer id;
    private Cliente cliente;
    private String status;
    private Timestamp dataHoraInclusaoPedido;

    public static Pedidos builderPedidos(PedidosDto pedidosDto){

        return Pedidos
                .builder()
                .id(pedidosDto.getId())
                .cliente(Cliente.fromDto(pedidosDto.getClientId()))
                .status(pedidosDto.getStatus())
                .dataHoraInclusaoPedido(pedidosDto.getDataHoraInclusaoPedido())
                .build();

    }

    public static Pedidos builderPedidos(PedidosEntity pedidosEntity){

        return Pedidos
                .builder()
                .id(pedidosEntity.getId())
                .cliente(
                        pedidosEntity.getClientId() != null
                                ? pedidosEntity.getClientId().toDomain()
                                : null
                )
                .status(pedidosEntity.getStatus())
                .dataHoraInclusaoPedido(pedidosEntity.getDataHoraInclusaoPedido())
                .build();

    }

    public PedidosDto toPedidosDto(){return PedidosDto.builderPedidos(this);
    }

    public PedidosEntity toEntity(){
        return PedidosEntity
                .builder()
                .id(this.id)
                .clientId(
                        this.cliente != null
                                ? this.cliente.toEntity()
                                : null
                )
                .status(this.status)
                .dataHoraInclusaoPedido(this.dataHoraInclusaoPedido)
                .build();
    }
}
