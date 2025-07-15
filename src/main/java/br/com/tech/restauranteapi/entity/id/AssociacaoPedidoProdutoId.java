package br.com.tech.restauranteapi.entity.id;

import br.com.tech.restauranteapi.entity.ProdutoEntity;
import br.com.tech.restauranteapi.entity.PedidoEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class AssociacaoPedidoProdutoId implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", referencedColumnName = "id")
    private PedidoEntity pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produtos_id", referencedColumnName = "id")
    private ProdutoEntity produto;
}
