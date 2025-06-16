package br.com.tech.restauranteapi.pagamentos.dominio.dtos;

import lombok.Data;

@Data
public class PagamentoDTO {
    private String pedidoId;
    private String status; // "aprovado" ou "recusado"
}
