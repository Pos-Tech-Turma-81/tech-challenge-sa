package br.com.tech.restauranteapi.pedidos.dominio.dtos;

import br.com.tech.restauranteapi.associacaoPedidoProduto.dominio.AssociacaoPedidoProduto;
import br.com.tech.restauranteapi.associacaoPedidoProduto.dominio.AssociacaoProduto;
import br.com.tech.restauranteapi.pedidos.dominio.Pedido;
import br.com.tech.restauranteapi.clientes.dominio.dtos.ClienteDTO;
import br.com.tech.restauranteapi.utils.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDto {

    private Integer id;
    private ClienteDTO cliente;
    private StatusEnum status;
    private LocalDateTime dataHoraInclusaoPedido;
    private List<AssociacaoProduto> associacoes;


    public static PedidoDto builderPedidos(Pedido pedidos){

        return PedidoDto
                .builder()
                .id(pedidos.getId())
                .cliente(pedidos.getCliente() != null ? pedidos.getCliente().toClienteDTO() : null)
                .status(pedidos.getStatus())
                .dataHoraInclusaoPedido(pedidos.getDataHoraInclusaoPedido())
                .associacoes( pedidos.getAssociacoes() )
                .build();

    }
}
