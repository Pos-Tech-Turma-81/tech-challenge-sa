package br.com.tech.restauranteapi.pedidos.dominio.dtos;
import br.com.tech.restauranteapi.utils.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoResponseDto {
    private Integer pedidoId;
    private Integer clienteId;
    private StatusEnum status;
    private Timestamp dataHora;
    private List<ProdutoPedidoResponseDto> produtos;
}
