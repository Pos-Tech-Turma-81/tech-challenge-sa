package br.com.tech.restauranteapi.pedidos.dominio;

import br.com.tech.restauranteapi.associacaoPedidoProduto.dominio.AssociacaoPedidoProduto;
import br.com.tech.restauranteapi.associacaoPedidoProduto.infraestrutura.entidades.AssociacaoPedidoProdutoEntity;
import br.com.tech.restauranteapi.clientes.dominio.dtos.ClienteEntity;
import br.com.tech.restauranteapi.pedidos.dominio.dtos.PedidosDto;
import br.com.tech.restauranteapi.pedidos.infraestrutura.entidades.PedidosEntity;
import br.com.tech.restauranteapi.clientes.dominio.Cliente;
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
public class Pedidos {

    private Integer id;
    private Cliente cliente;
    private String status;
    private Timestamp dataHoraInclusaoPedido;
    private List<AssociacaoPedidoProduto> associacoes;

    public static Pedidos builderPedidos(PedidosDto pedidosDto){

        return Pedidos
                .builder()
                .id(pedidosDto.getId())
                .cliente(
                        pedidosDto.getCliente() != null
                                ? new Cliente(
                                pedidosDto.getCliente().getId(),
                                pedidosDto.getCliente().getNome(),
                                pedidosDto.getCliente().getEmail(),
                                pedidosDto.getCliente().getTelefone(),
                                pedidosDto.getCliente().getCpf(),
                                pedidosDto.getCliente().getEndereco()
                        )
                                : null
                )
                .status(pedidosDto.getStatus())
                .dataHoraInclusaoPedido(pedidosDto.getDataHoraInclusaoPedido())
                .associacoes(pedidosDto.getAssociacoes())
                .build();

    }

    public static Pedidos builderPedidos(PedidosEntity pedidosEntity){

        return Pedidos
                .builder()
                .id(pedidosEntity.getId())
                .cliente(
                        pedidosEntity.getClientId() != null
                                ? pedidosEntity.getClientId().toCliente()
                                : null
                )
                .status(pedidosEntity.getStatus())
                .dataHoraInclusaoPedido(pedidosEntity.getDataHoraInclusaoPedido())
                .associacoes(
                        pedidosEntity.getAssociacoes() != null
                                ? pedidosEntity.getAssociacoes().stream()
                                .map(AssociacaoPedidoProduto::builderAssociacao)
                                .toList()
                                : null
                )
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
                                ? new ClienteEntity(this.cliente)
                                : null
                )
                .status(this.status)
                .dataHoraInclusaoPedido(this.dataHoraInclusaoPedido)
                .build();
    }
}
