package br.com.tech.restauranteapi.pagamentos.dominio.dtos;

import lombok.Data;

import java.util.List;

@Data
public class MercadoPagoCheckoutRequest
{
    private String title;
    private String description;
    private String notification_url;
    private String external_reference;
    private double total_amount;
    private List<MercadoPagoItem> items;
}

