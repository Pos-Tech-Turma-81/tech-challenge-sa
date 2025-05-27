package br.com.tech.restauranteapi.pedidos.dominio.dtos;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoPedidoResponseDto {
    private Integer produtoId;
    private String nomeProduto;
    private Integer quantidade;
    private BigDecimal preco;
}
