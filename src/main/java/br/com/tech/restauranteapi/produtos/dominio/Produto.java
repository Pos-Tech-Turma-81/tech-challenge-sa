package br.com.tech.restauranteapi.produtos.dominio;

import br.com.tech.restauranteapi.produtos.dominio.dtos.ProdutoDto;
import br.com.tech.restauranteapi.produtos.infraestrutura.entidades.ProdutoEntity;
import br.com.tech.restauranteapi.utils.enums.CategoriaEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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
                .imagem(produtoDto.getImagem())
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
