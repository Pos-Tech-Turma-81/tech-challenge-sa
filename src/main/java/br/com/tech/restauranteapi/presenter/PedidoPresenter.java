package br.com.tech.restauranteapi.presenter;

import br.com.tech.restauranteapi.controller.dtos.PedidoResponseDto;
import br.com.tech.restauranteapi.controller.dtos.ProdutoPedidoResponseDto;
import br.com.tech.restauranteapi.domain.AssociacaoProduto;
import br.com.tech.restauranteapi.domain.Pedido;
import br.com.tech.restauranteapi.entity.PedidoEntity;
import br.com.tech.restauranteapi.controller.dtos.PedidoDto;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PedidoPresenter {

    public static PedidoDto toDto(Pedido pedido) {
        return PedidoDto.builder()
                .id(pedido.getId())
                .cliente(pedido.getCliente() != null ? ClientePresenter.toDto(pedido.getCliente()) : null)
                .status(pedido.getStatus())
                .dataHoraInclusaoPedido(pedido.getDataHoraInclusaoPedido())
                .associacoes(pedido.getAssociacoes())
                .build();
    }

    public static Pedido toDomain(PedidoEntity entity) {
        return Pedido.builder()
                .id(entity.getId())
                .cliente(entity.getClientId() != null ? ClientePresenter.toDomain(entity.getClientId()) : null)
                .status(entity.getStatus())
                .dataHoraInclusaoPedido(entity.getDataHoraInclusaoPedido())
                .associacoes(
                        entity.getAssociacoes() != null
                                ? AssociacaoProdutoPresenter.toDomainListFromEntity(entity.getAssociacoes())
                                : Collections.emptyList()
                )
                .build();
    }

    public static PedidoEntity toEntity(Pedido pedido) {
        return PedidoEntity.builder()
                .id(pedido.getId())
                .clientId(pedido.getCliente() != null ? ClientePresenter.toEntity(pedido.getCliente()) : null)
                .status(pedido.getStatus())
                .dataHoraInclusaoPedido(pedido.getDataHoraInclusaoPedido())
                .build();
    }

    public static PedidoResponseDto toResponseDto(Pedido pedido) {
        List<AssociacaoProduto> associacoes = pedido.getAssociacoes();
        if (associacoes == null) {
            associacoes = java.util.Collections.emptyList();
        }
        List<ProdutoPedidoResponseDto> produtos = associacoes.stream()
                .map(assoc -> ProdutoPedidoResponseDto.builder()
                        .produtoId(assoc.getProduto() != null ? assoc.getProduto().getId() : null)
                        .nomeProduto(assoc.getProduto() != null ? assoc.getProduto().getNome() : "Produto não encontrado")
                        .quantidade(assoc.getQuantidade())
                        .preco(assoc.getPreco())
                        .build())
                .collect(Collectors.toList());

        return PedidoResponseDto.builder()
                .pedidoId(pedido.getId())
                .clienteId(pedido.getCliente() != null ? pedido.getCliente().getId() : null)
                .status(pedido.getStatus())
                .dataHora(pedido.getDataHoraInclusaoPedido())
                .produtos(produtos)
                .build();
    }

}