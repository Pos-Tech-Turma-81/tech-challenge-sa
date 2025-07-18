package br.com.tech.restauranteapi.presenter;

import br.com.tech.restauranteapi.domain.Produto;
import br.com.tech.restauranteapi.entity.ProdutoEntity;
import br.com.tech.restauranteapi.controller.dtos.ProdutoDto;

public class ProdutoPresenter {

    public static ProdutoDto toDto(Produto produto) {
        if (produto == null) return null;
        return new ProdutoDto(
                produto.getId(),
                produto.getNome(),
                produto.getCategoria(),
                produto.getPreco(),
                produto.getDescricao(),
                null
        );
    }

    public static Produto fromToDomain(ProdutoDto dto) {
        if (dto == null) return null;
        return Produto.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .descricao(dto.getDescricao())
                .preco(dto.getPreco())
                .categoria(dto.getCategoria())
                .build();
    }

    public static Produto toDomain(ProdutoEntity entity) {
        if (entity == null) return null;
        return Produto.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .descricao(entity.getDescricao())
                .preco(entity.getPreco())
                .categoria(entity.getCategoria())
                .build();
    }

    public static ProdutoEntity toEntity(Produto produto) {
        if (produto == null) return null;
        if (produto.getNome() == null || produto.getPreco() == null || produto.getCategoria() == null) {
            throw new IllegalArgumentException("Campos obrigatórios de Produto não podem ser nulos");
        }
        return ProdutoEntity.builder()
                .id(produto.getId())
                .nome(produto.getNome())
                .descricao(produto.getDescricao())
                .preco(produto.getPreco())
                .categoria(produto.getCategoria())
                .build();
    }
}