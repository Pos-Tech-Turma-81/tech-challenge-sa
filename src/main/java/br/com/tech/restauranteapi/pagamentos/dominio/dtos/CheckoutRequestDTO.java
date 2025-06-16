package br.com.tech.restauranteapi.pagamentos.dominio.dtos;

import lombok.Data;

@Data
public class CheckoutRequestDTO {
    private String pedidoId;
    private Double valor;
}
