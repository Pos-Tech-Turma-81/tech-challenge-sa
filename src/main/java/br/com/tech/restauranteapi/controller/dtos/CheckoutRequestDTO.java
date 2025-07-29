package br.com.tech.restauranteapi.controller.dtos;

import lombok.Data;

@Data
public class CheckoutRequestDTO {
    private Integer pedidoId;
    private Double valor;
}
