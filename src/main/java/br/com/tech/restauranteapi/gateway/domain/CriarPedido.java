package br.com.tech.restauranteapi.gateway.domain;
import br.com.tech.restauranteapi.controller.dtos.ProdutoPedidoDto;
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
