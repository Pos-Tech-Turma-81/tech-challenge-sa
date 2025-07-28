package br.com.tech.restauranteapi.controller.dtos;

import br.com.tech.restauranteapi.utils.enums.PagamentoStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoWebhookDto {
    private Integer pedidoId;
    private PagamentoStatusEnum status;
}
