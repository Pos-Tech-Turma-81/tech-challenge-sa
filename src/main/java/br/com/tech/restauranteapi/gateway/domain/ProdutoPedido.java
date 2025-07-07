package br.com.tech.restauranteapi.gateway.domain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoPedido {
    private Integer produtoId;
    private Integer quantidade;
}
