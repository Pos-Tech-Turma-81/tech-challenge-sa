package br.com.tech.restauranteapi.produtos.dominio.dtos;

import br.com.tech.restauranteapi.produtos.dominio.Produto;
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
public class ProdutoDto {

    private Integer id;
    private String nome;
    private CategoriaEnum categoria;
    private BigDecimal preco;
    private String descricao;
    private byte[] imagem;

    public static ProdutoDtoBuilder builderProduto(Produto produto){

        return ProdutoDto
                .builder()
                .categoria(produto.getCategoria())
                .id(produto.getId())
                .descricao(produto.getDescricao())
                .imagem(produto.getImagem())
                .nome(produto.getNome())
                .preco(produto.getPreco());

    }
}
