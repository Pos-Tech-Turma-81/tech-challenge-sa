package br.com.tech.restauranteapi.pedidos.dominio.dtos;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CriarPedidoDto {
    private Integer clienteId;
    private List<ProdutoPedidoDto> produtos;
}
