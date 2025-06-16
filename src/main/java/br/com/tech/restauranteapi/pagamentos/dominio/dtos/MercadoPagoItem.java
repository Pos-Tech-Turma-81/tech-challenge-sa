package br.com.tech.restauranteapi.pagamentos.dominio.dtos;

import lombok.Data;

@Data
public class MercadoPagoItem {
    private String sku_number;
    private String category;
    private String title;
    private String description;
    private double unit_price;
    private int quantity;
    private String unit_measure;
    private double total_amount;
}
