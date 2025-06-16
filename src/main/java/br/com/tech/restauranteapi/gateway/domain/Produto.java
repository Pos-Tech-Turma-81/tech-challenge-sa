package br.com.tech.restauranteapi.gateway.domain;

import br.com.tech.restauranteapi.controller.dtos.ProdutoDto;
import br.com.tech.restauranteapi.gateway.entity.ProdutoEntity;
import br.com.tech.restauranteapi.utils.enums.CategoriaEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Produto {

    private Integer id;
    private String nome;
    private CategoriaEnum categoria;
    private BigDecimal preco;
    private String descricao;
    private byte[] imagem;

    public static Produto builderProduto(ProdutoDto produtoDto){

        return Produto
                .builder()
                .categoria(produtoDto.getCategoria())
                .id(produtoDto.getId())
                .descricao(produtoDto.getDescricao())
                .imagem(Objects.nonNull(produtoDto.getImagem()) ? Base64.getDecoder().decode(produtoDto.getImagem()) : null)
                .nome(produtoDto.getNome())
                .preco(produtoDto.getPreco())
                .build();

    }

    public static Produto builderProduto(ProdutoEntity produtoEntity){

        return Produto
                .builder()
                .categoria(produtoEntity.getCategoria())
                .id(produtoEntity.getId())
                .descricao(produtoEntity.getDescricao())
                .imagem(produtoEntity.getImagem())
                .nome(produtoEntity.getNome())
                .preco(produtoEntity.getPreco())
                .build();

    }

    public ProdutoDto toProdutoDto(){
        return ProdutoDto.builderProduto(this);
    }


    public ProdutoEntity toEntity(){
        return ProdutoEntity
                .builder()
                .id(this.id)
                .nome(this.nome)
                .preco(this.preco)
                .categoria(this.categoria)
                .descricao(this.descricao)
                .imagem(this.imagem)
                .build();
    }
}
