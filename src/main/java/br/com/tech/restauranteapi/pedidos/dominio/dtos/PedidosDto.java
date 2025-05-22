package br.com.tech.restauranteapi.pedidos.dominio.dtos;

import br.com.tech.restauranteapi.associacaoPedidoProduto.dominio.AssociacaoPedidoProduto;
import br.com.tech.restauranteapi.pedidos.dominio.Pedidos;
import br.com.tech.restauranteapi.clientes.dominio.dtos.ClienteDTO;
import br.com.tech.restauranteapi.utils.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidosDto {

    private Integer id;
    private ClienteDTO cliente;
    private StatusEnum status;
    private Timestamp dataHoraInclusaoPedido;
    private List<AssociacaoPedidoProduto> associacoes;


    public static PedidosDto builderPedidos(Pedidos pedidos){

        return PedidosDto
                .builder()
                .id(pedidos.getId())
                .cliente(pedidos.getCliente() != null ? pedidos.getCliente().toClienteDTO() : null)
                .status(pedidos.getStatus())
                .dataHoraInclusaoPedido(pedidos.getDataHoraInclusaoPedido())
                .associacoes(pedidos.getAssociacoes())
                .build();

    }
}
