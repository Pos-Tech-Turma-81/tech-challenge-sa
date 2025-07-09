package br.com.tech.restauranteapi.controller.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CheckoutResponseDTO {
    private String qr_data;
}
