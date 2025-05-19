package br.com.tech.restauranteapi.pedidos.infraestrutura.entidades;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class AssociacaoPedidoProdutoId implements Serializable {
    private Integer pedidoId;
    private Integer produtosId;
}
