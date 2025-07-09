package br.com.tech.restauranteapi.controller;

import br.com.tech.restauranteapi.controller.dtos.CheckoutRequestDTO;
import br.com.tech.restauranteapi.controller.dtos.MercadoPagoCheckoutRequest;
import br.com.tech.restauranteapi.controller.dtos.CheckoutResponseDTO;
import br.com.tech.restauranteapi.controller.dtos.MercadoPagoItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/checkout")
public class FakeCheckoutController {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${mercadopago.token}")
    private String mpAccessToken;

    @Value("${mercadopago.userId}")
    private String userId;

    @Value("${mercadopago.externalPosId}")
    private String externalPosId;

    @PostMapping
    public ResponseEntity<CheckoutResponseDTO> iniciarCheckout(@RequestBody CheckoutRequestDTO request) {

        MercadoPagoCheckoutRequest mp = new MercadoPagoCheckoutRequest();
        mp.setTitle("Pedido " + request.getPedidoId());
        mp.setNotification_url("https://www.yourserver.com/notifications");
        mp.setExternal_reference(request.getPedidoId());
        mp.setDescription("Descrição");
        mp.setTotal_amount(25.01);

        MercadoPagoItem mp_item = new MercadoPagoItem();
        mp_item.setCategory("marketplace");
        mp_item.setDescription("AA");
        mp_item.setQuantity(1);
        mp_item.setTitle("Produto");
        mp_item.setSku_number("123");
        mp_item.setUnit_measure("unit");
        mp_item.setUnit_price(25.01);
        mp_item.setTotal_amount(25.01);
        mp.setItems(List.of(new MercadoPagoItem[]{mp_item}));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(mpAccessToken);

        HttpEntity<MercadoPagoCheckoutRequest> entity = new HttpEntity<>(mp, headers);

        String url = "https://api.mercadopago.com/instore/orders/qr/seller/collectors/"+userId+"/pos/"+externalPosId+"/qrs";
        ResponseEntity<CheckoutResponseDTO> response = restTemplate
                .postForEntity(url, entity, CheckoutResponseDTO.class);

        String qrData = response.getBody().getQr_data();
        return ResponseEntity.ok(new CheckoutResponseDTO(qrData));
    }
}
