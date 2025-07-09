package br.com.tech.restauranteapi.controller.dtos;
import br.com.tech.restauranteapi.gateway.domain.ProdutoPedido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoPedidoDto {
    private Integer produtoId;
    private Integer quantidade;

    public ProdutoPedido toDomain(){
        return ProdutoPedido
                .builder()
                .produtoId(this.produtoId)
                .quantidade(this.quantidade)
                .build();
    }
}
