package br.com.tech.restauranteapi.controller.dtos;
import br.com.tech.restauranteapi.domain.CriarPedido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CriarPedidoDto {
    private Integer clienteId;
    private List<ProdutoPedidoDto> produtos;

    public CriarPedido toDomain(){
        return CriarPedido
                .builder()
                .clienteId(this.clienteId)
                .produtos(this.produtos.stream().map(ProdutoPedidoDto::toDomain).collect(Collectors.toList()))
                .build();
    }
}
