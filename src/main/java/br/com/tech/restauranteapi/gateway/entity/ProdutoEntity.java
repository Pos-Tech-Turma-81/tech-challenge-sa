package br.com.tech.restauranteapi.gateway.entity;

import br.com.tech.restauranteapi.gateway.domain.Produto;
import br.com.tech.restauranteapi.utils.enums.CategoriaEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Produtos")
public class ProdutoEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ou AUTO, SEQUENCE, etc.
    private Integer id;

    @Column(name = "nome")
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria")
    private CategoriaEnum categoria;

    @Column(name = "preco")
    private BigDecimal preco;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "imagem")
    private byte[] imagem;

    public Produto toProdutoDomain(){
        return Produto.builderProduto(this);
    }
}
