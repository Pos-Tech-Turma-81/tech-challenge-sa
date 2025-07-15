package br.com.tech.restauranteapi.domain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CriarPedido {
    private Integer clienteId;
    private List<ProdutoPedido> produtos;
}
