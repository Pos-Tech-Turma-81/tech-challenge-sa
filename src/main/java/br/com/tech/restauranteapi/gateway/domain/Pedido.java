package br.com.tech.restauranteapi.gateway.domain;

import br.com.tech.restauranteapi.associacaoPedidoProduto.dominio.AssociacaoProduto;
import br.com.tech.restauranteapi.gateway.entity.ClienteEntity;
import br.com.tech.restauranteapi.controller.dtos.PedidoDto;
import br.com.tech.restauranteapi.gateway.entity.PedidoEntity;
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
public class Pedido {

    private Integer id;
    private Cliente cliente;
    private StatusEnum status;
    private LocalDateTime dataHoraInclusaoPedido;
    private List<AssociacaoProduto> associacoes;

    public static Pedido builderPedidos(PedidoDto pedidoDto){

        return Pedido
                .builder()
                .id(pedidoDto.getId())
                .cliente(
                        pedidoDto.getCliente() != null
                                ? new Cliente(
                                pedidoDto.getCliente().getId(),
                                pedidoDto.getCliente().getNome(),
                                pedidoDto.getCliente().getEmail(),
                                pedidoDto.getCliente().getTelefone(),
                                pedidoDto.getCliente().getCpf(),
                                pedidoDto.getCliente().getEndereco()
                        )
                                : null
                )
                .status(pedidoDto.getStatus())
                .dataHoraInclusaoPedido(pedidoDto.getDataHoraInclusaoPedido())
                .associacoes(
                        pedidoDto.getAssociacoes()
                )
                .build();

    }

    public static Pedido builderPedidos(PedidoEntity pedidoEntity){

        return Pedido
                .builder()
                .id(pedidoEntity.getId())
                .cliente(
                        pedidoEntity.getClientId() != null
                                ? pedidoEntity.getClientId().toCliente()
                                : null
                )
                .status(pedidoEntity.getStatus())
                .dataHoraInclusaoPedido(pedidoEntity.getDataHoraInclusaoPedido())
                .associacoes(
                        pedidoEntity.getAssociacoes() != null
                                ? pedidoEntity.getAssociacoes().stream()
                                .map(AssociacaoProduto::builderAssociacao)
                                .toList()
                                : null
                )
                .build();

    }

    public PedidoDto toPedidosDto(){return PedidoDto.builderPedidos(this);
    }

    public PedidoEntity toEntity(){
        return PedidoEntity
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
