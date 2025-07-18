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

    public ProdutoDto(Produto produto) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.categoria = produto.getCategoria();
        this.preco = produto.getPreco();
        this.descricao = produto.getDescricao();
        if (Objects.nonNull(produto.getImagem())) {
            this.imagem = Base64.getEncoder().encodeToString(produto.getImagem());
        }
    }
}
