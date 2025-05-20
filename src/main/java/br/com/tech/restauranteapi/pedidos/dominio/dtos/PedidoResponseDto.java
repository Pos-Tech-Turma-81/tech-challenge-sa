package br.com.tech.restauranteapi.pedidos.dominio.dtos;
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
    private String status;
    private Timestamp dataHora;
    private List<ProdutoPedidoResponseDto> produtos;
}
