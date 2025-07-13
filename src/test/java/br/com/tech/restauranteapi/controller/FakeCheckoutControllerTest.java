package br.com.tech.restauranteapi.controller;

import br.com.tech.restauranteapi.controller.dtos.CheckoutRequestDTO;
import br.com.tech.restauranteapi.controller.dtos.CheckoutResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class FakeCheckoutControllerTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private FakeCheckoutController controller;

    private String mpAccessToken = "fake-token";
    private String userId = "12345";
    private String externalPosId = "67890";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIniciarCheckout_Success() {
        CheckoutRequestDTO requestDTO = new CheckoutRequestDTO();
        requestDTO.setPedidoId("pedido123");

        CheckoutResponseDTO mockResponseBody = new CheckoutResponseDTO("fake-qr-data");
        ResponseEntity<CheckoutResponseDTO> mockResponse = new ResponseEntity<>(mockResponseBody, HttpStatus.OK);

        when(controller.iniciarCheckout(requestDTO))
                .thenReturn(mockResponse);

        ResponseEntity<CheckoutResponseDTO> response = controller.iniciarCheckout(requestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("fake-qr-data", response.getBody().getQr_data());
    }
}
