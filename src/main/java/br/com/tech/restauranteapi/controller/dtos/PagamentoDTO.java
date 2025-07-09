package br.com.tech.restauranteapi.controller.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoDTO {
    private String pedidoId;
    private String status; // "aprovado" ou "recusado"
}
