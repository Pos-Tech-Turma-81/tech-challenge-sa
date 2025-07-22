package br.com.tech.restauranteapi.gateway.impl;

import br.com.tech.restauranteapi.controller.dtos.CheckoutResponseDTO;
import br.com.tech.restauranteapi.controller.dtos.MercadoPagoCheckoutRequest;
import br.com.tech.restauranteapi.gateway.FakeCheckoutGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class FakeCheckoutGatewayImpl implements FakeCheckoutGateway {

    @Value("${mercadopago.token}")
    private String mpAccessToken;

    @Value("${mercadopago.userId}")
    private String userId;

    @Value("${mercadopago.externalPosId}")
    private String externalPosId;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public CheckoutResponseDTO iniciarCheckout(MercadoPagoCheckoutRequest mercadoPagoCheckoutRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(mpAccessToken);

        HttpEntity<MercadoPagoCheckoutRequest> entity = new HttpEntity<>(mercadoPagoCheckoutRequest, headers);

        String url = "https://api.mercadopago.com/instore/orders/qr/seller/collectors/"+userId+"/pos/"+externalPosId+"/qrs";
        ResponseEntity<CheckoutResponseDTO> response = restTemplate
                .postForEntity(url, entity, CheckoutResponseDTO.class);

        assert response.getBody() != null;
        String qrData = response.getBody().getQr_data();
        return ResponseEntity.ok(new CheckoutResponseDTO(qrData)).getBody();
    }
}
