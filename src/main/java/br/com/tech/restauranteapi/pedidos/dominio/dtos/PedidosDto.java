package br.com.tech.restauranteapi.pedidos.dominio.dtos;

import br.com.tech.restauranteapi.pedidos.dominio.Pedidos;
import br.com.tech.restauranteapi.clientes.dominio.dtos.ClienteDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidosDto {

    private Integer id;
    private ClienteDTO cliente;
    private String status;
    private Timestamp dataHoraInclusaoPedido;

    public static PedidosDto builderPedidos(Pedidos pedidos){

        return PedidosDto
                .builder()
                .id(pedidos.getId())
                .cliente(pedidos.getCliente() != null ? pedidos.getCliente().toDto() : null)
                .status(pedidos.getStatus())
                .dataHoraInclusaoPedido(pedidos.getDataHoraInclusaoPedido())
                .build();

    }
}
