package br.com.tech.restauranteapi.pagamentos.dominio.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.context.properties.bind.Name;

@Data
@AllArgsConstructor
public class CheckoutResponseDTO {
    private String qr_data;
}
