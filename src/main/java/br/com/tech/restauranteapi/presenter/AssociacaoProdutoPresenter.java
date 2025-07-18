package br.com.tech.restauranteapi.presenter;

import br.com.tech.restauranteapi.domain.AssociacaoPedidoProduto;
import br.com.tech.restauranteapi.domain.AssociacaoProduto;
import br.com.tech.restauranteapi.domain.Pedido;
import br.com.tech.restauranteapi.domain.Produto;
import br.com.tech.restauranteapi.controller.dtos.AssociacaoPedidoProdutoDto;
import br.com.tech.restauranteapi.entity.AssociacaoPedidoProdutoEntity;
import br.com.tech.restauranteapi.entity.id.AssociacaoPedidoProdutoId;
import br.com.tech.restauranteapi.entity.PedidoEntity;
import br.com.tech.restauranteapi.entity.ProdutoEntity;

import java.util.List;
import java.util.stream.Collectors;

public class AssociacaoProdutoPresenter {

    public static AssociacaoPedidoProdutoDto toDto(AssociacaoProduto associacao) {
        return new AssociacaoPedidoProdutoDto(
                associacao.getPedido() != null ? associacao.getPedido().getId() : null,
                associacao.getProduto() != null ? associacao.getProduto().getId() : null,
                associacao.getQuantidade(),
                associacao.getPreco()
        );
    }

    public static List<AssociacaoPedidoProdutoDto> toDtoList(List<AssociacaoProduto> associacoes) {
        return associacoes.stream()
                .map(AssociacaoProdutoPresenter::toDto)
                .collect(Collectors.toList());
    }

    public static AssociacaoProduto toDomain(AssociacaoPedidoProdutoDto dto) {
        Produto produto = new Produto(dto.getProdutoId(), null, null, null, null, null);
        Pedido pedido = dto.getPedidoId() != null ? new Pedido(dto.getPedidoId()) : null;
        return new AssociacaoProduto(
                dto.getQuantidade(),
                dto.getPreco(),
                produto,
                pedido
        );
    }

    public static List<AssociacaoProduto> toDomainList(List<AssociacaoPedidoProdutoDto> dtos) {
        return dtos.stream()
                .map(AssociacaoProdutoPresenter::toDomain)
                .collect(Collectors.toList());
    }

    public static AssociacaoProduto toDomain(AssociacaoPedidoProdutoEntity entity) {
        Integer produtoId = entity.getId() != null && entity.getId().getProduto() != null
                ? entity.getId().getProduto().getId()
                : null;
        Integer pedidoId = entity.getId() != null && entity.getId().getPedido() != null
                ? entity.getId().getPedido().getId()
                : null;

        Produto produto = new Produto(produtoId, null, null, null, null, null);
        Pedido pedido = pedidoId != null ? new Pedido(pedidoId) : null;

        return new AssociacaoProduto(
                entity.getQuantidade(),
                entity.getPreco(),
                produto,
                pedido
        );
    }

    public static List<AssociacaoProduto> toDomainListFromEntity(List<AssociacaoPedidoProdutoEntity> entities) {
        return entities.stream()
                .map(AssociacaoProdutoPresenter::toDomain)
                .collect(Collectors.toList());
    }

    public static AssociacaoPedidoProdutoEntity toEntity(AssociacaoProduto associacao) {
        PedidoEntity pedidoEntity = associacao.getPedido() != null
                ? PedidoEntity.builder().id(associacao.getPedido().getId()).build()
                : null;
        ProdutoEntity produtoEntity = associacao.getProduto() != null
                ? ProdutoEntity.builder().id(associacao.getProduto().getId()).build()
                : null;

        AssociacaoPedidoProdutoId id = new AssociacaoPedidoProdutoId(pedidoEntity, produtoEntity);

        return AssociacaoPedidoProdutoEntity.builder()
                .id(id)
                .quantidade(associacao.getQuantidade())
                .preco(associacao.getPreco())
                .build();
    }

    public static List<AssociacaoPedidoProdutoEntity> toEntityList(List<AssociacaoProduto> associacoes) {
        return associacoes.stream()
                .map(AssociacaoProdutoPresenter::toEntity)
                .collect(Collectors.toList());
    }

    public static AssociacaoProduto fromToDomain(AssociacaoPedidoProduto associacaoPedidoProduto) {
        return AssociacaoProduto.builder()
                .produto(associacaoPedidoProduto.getProduto())
                .pedido(associacaoPedidoProduto.getPedido())
                .quantidade(associacaoPedidoProduto.getQuantidade())
                .preco(associacaoPedidoProduto.getPreco())
                .build();
    }

}