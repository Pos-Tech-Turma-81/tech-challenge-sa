package br.com.tech.restauranteapi.api;

import br.com.tech.restauranteapi.controller.dtos.PagamentoWebhookDto;
import br.com.tech.restauranteapi.utils.enums.PagamentoStatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PagamentoWebhookApiTest {

    private PagamentoWebhookApi api;

    @BeforeEach
    void setUp() {
        api = new PagamentoWebhookApi();
    }

    @Test
    void shouldReturnAprovadoWhenStatusIsAPROVADO() {
        PagamentoWebhookDto dto = new PagamentoWebhookDto(123, PagamentoStatusEnum.APROVADO);

        ResponseEntity<String> response = api.receberStatus(dto);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("Pagamento aprovado");
    }

    @Test
    void shouldReturnRecusadoWhenStatusIsRECUSADO() {
        PagamentoWebhookDto dto = new PagamentoWebhookDto(456, PagamentoStatusEnum.RECUSADO);

        ResponseEntity<String> response = api.receberStatus(dto);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("Pagamento recusado");
    }

    @Test
    void shouldReturnEmProcessamentoWhenStatusIsEmProcessamento() {
        PagamentoWebhookDto dto = new PagamentoWebhookDto(789, PagamentoStatusEnum.EM_PROCESSAMENTO);

        ResponseEntity<String> response = api.receberStatus(dto);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("Pagamento em processamento");
    }
}
