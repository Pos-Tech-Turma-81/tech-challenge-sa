package br.com.tech.restauranteapi.controller.dtos;

import br.com.tech.restauranteapi.domain.Produto;
import br.com.tech.restauranteapi.utils.enums.CategoriaEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDto {

    private Integer id;

    @NotNull(message = "Nome do produto é obrigatorio")
    private String nome;

    @NotNull(message = "Categoria do produto é obrigatorio")
    private CategoriaEnum categoria;

    @NotNull(message = "Preço do produto é obrigatorio")
    private BigDecimal preco;
    private String descricao;
    private String imagem;

    public static ProdutoDto builderProduto(Produto produto){

        return ProdutoDto
                .builder()
                .categoria(produto.getCategoria())
                .id(produto.getId())
                .descricao(produto.getDescricao())
                .imagem(Objects.nonNull(produto.getImagem()) ? Base64.getEncoder().encodeToString(produto.getImagem()) : null)
                .nome(produto.getNome())
                .preco(produto.getPreco())
                .build();

    }
}
