package br.com.tech.restauranteapi.controller;

import br.com.tech.restauranteapi.controller.dtos.PagamentoDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class PagamentoWebhookControllerTest {

    private final PagamentoWebhookController controller = new PagamentoWebhookController();

    @Test
    void testReceberStatusAprovado() {
        PagamentoDTO payload = new PagamentoDTO();
        payload.setPedidoId("123");
        payload.setStatus("aprovado");

        ResponseEntity<String> response = controller.receberStatus(payload);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Pagamento aprovado", response.getBody());
    }

    @Test
    void testReceberStatusRecusado() {
        PagamentoDTO payload = new PagamentoDTO();
        payload.setPedidoId("456");
        payload.setStatus("recusado");

        ResponseEntity<String> response = controller.receberStatus(payload);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Pagamento recusado", response.getBody());
    }

    @Test
    void testReceberStatusDesconhecido() {
        PagamentoDTO payload = new PagamentoDTO();
        payload.setPedidoId("789");
        payload.setStatus("pendente");

        ResponseEntity<String> response = controller.receberStatus(payload);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Status inválido", response.getBody());
    }
}
