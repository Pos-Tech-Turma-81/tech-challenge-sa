package br.com.tech.restauranteapi.presenter;

import br.com.tech.restauranteapi.domain.AssociacaoPedidoProduto;
import br.com.tech.restauranteapi.domain.Pedido;
import br.com.tech.restauranteapi.domain.Produto;
import br.com.tech.restauranteapi.entity.AssociacaoPedidoProdutoEntity;
import br.com.tech.restauranteapi.entity.id.AssociacaoPedidoProdutoId;
import br.com.tech.restauranteapi.controller.dtos.AssociacaoPedidoProdutoDto;


public class AssociacaoPedidoProdutoPresenter {

    public static AssociacaoPedidoProdutoDto toDto(AssociacaoPedidoProduto domain) {
        if (domain == null) return null;
        return new AssociacaoPedidoProdutoDto(
                domain.getPedido() != null ? domain.getPedido().getId() : null,
                domain.getProduto() != null ? domain.getProduto().getId() : null,
                domain.getQuantidade(),
                domain.getPreco()
        );
    }

    public static AssociacaoPedidoProduto toDomain(AssociacaoPedidoProdutoDto dto, Pedido pedido, Produto produto) {
        if (dto == null) return null;
        return AssociacaoPedidoProduto.builder()
                .pedido(pedido)
                .produto(produto)
                .quantidade(dto.getQuantidade())
                .preco(dto.getPreco())
                .build();
    }

    public static AssociacaoPedidoProduto fromToDomain(AssociacaoPedidoProdutoEntity entity) {
        if (entity == null) return null;
        Pedido pedido = entity.getId() != null && entity.getId().getPedido() != null
                ? PedidoPresenter.toDomain(entity.getId().getPedido())
                : null;
        Produto produto = entity.getId() != null && entity.getId().getProduto() != null
                ? ProdutoPresenter.toDomain(entity.getId().getProduto())
                : null;

        return AssociacaoPedidoProduto.builder()
                .pedido(pedido)
                .produto(produto)
                .quantidade(entity.getQuantidade())
                .preco(entity.getPreco())
                .build();
    }

    public static AssociacaoPedidoProdutoEntity toEntity(AssociacaoPedidoProduto domain) {
        if (domain == null) return null;
        return AssociacaoPedidoProdutoEntity.builder()
                .id(new AssociacaoPedidoProdutoId(
                        domain.getPedido() != null ? PedidoPresenter.toEntity(domain.getPedido()) : null,
                        domain.getProduto() != null ? ProdutoPresenter.toEntity(domain.getProduto()) : null
                ))
                .quantidade(domain.getQuantidade())
                .preco(domain.getPreco())
                .build();
    }
}