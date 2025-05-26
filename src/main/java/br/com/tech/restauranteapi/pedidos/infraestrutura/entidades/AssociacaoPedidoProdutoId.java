package br.com.tech.restauranteapi.pedidos.infraestrutura.entidades;

import br.com.tech.restauranteapi.produtos.infraestrutura.entidades.ProdutoEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class AssociacaoPedidoProdutoId implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", referencedColumnName = "id")
    private PedidoEntity pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produtos_id", referencedColumnName = "id")
    private ProdutoEntity produto;
}
