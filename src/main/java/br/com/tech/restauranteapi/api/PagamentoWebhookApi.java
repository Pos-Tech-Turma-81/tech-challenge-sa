package br.com.tech.restauranteapi.api;

import br.com.tech.restauranteapi.controller.dtos.PagamentoWebhookDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook/pagamento")
@Slf4j
public class PagamentoWebhookApi {

    @PostMapping
    public ResponseEntity<String> receberStatus(@RequestBody PagamentoWebhookDto payload) {
        log.info("Webhook recebido: pedidoId={}, status={}", payload.getPedidoId(), payload.getStatus());

        return switch (payload.getStatus()) {
            case RECUSADO -> {
                log.info("Pagamento recusado para o pedido {}", payload.getPedidoId());
                yield ResponseEntity.ok("Pagamento recusado");
            }
            case EM_PROCESSAMENTO -> {
                log.info("Pagamento em processamento para o pedido {}", payload.getPedidoId());
                yield ResponseEntity.ok("Pagamento em processamento");
            }
            case APROVADO -> {
                log.info("Pagamento aprovado para o pedido {}", payload.getPedidoId());
                yield ResponseEntity.ok("Pagamento aprovado");
            }
        };
    }
}
