package br.com.tech.restauranteapi.produtos.dominio;

import br.com.tech.restauranteapi.produtos.dominio.dtos.ProdutoDto;
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

    public static ProdutoBuilder builderProduto(ProdutoDto produtoDto){

        return Produto
                .builder()
                .categoria(produtoDto.getCategoria())
                .id(produtoDto.getId())
                .descricao(produtoDto.getDescricao())
                .imagem(produtoDto.getImagem())
                .nome(produtoDto.getNome())
                .preco(produtoDto.getPreco());

    }

    public ProdutoDto toProdutoDto(){
        return ProdutoDto.builderProduto(this).build();
    }
}
