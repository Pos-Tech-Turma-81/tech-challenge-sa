package br.com.tech.restauranteapi.controller;

import br.com.tech.restauranteapi.controller.dtos.PagamentoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook/pagamento")
@Slf4j
public class PagamentoWebhookController {

    @PostMapping
    public ResponseEntity<String> receberStatus(@RequestBody PagamentoDTO payload) {
        log.info("Webhook recebido: pedidoId={}, status={}", payload.getPedidoId(), payload.getStatus());

        return switch (payload.getStatus().toLowerCase()) {
            case "aprovado" -> {
                log.info("Pagamento aprovado para o pedido {}", payload.getPedidoId());
                yield ResponseEntity.ok("Pagamento aprovado");
            }
            case "recusado" -> {
                log.info("Pagamento recusado para o pedido {}", payload.getPedidoId());
                yield ResponseEntity.ok("Pagamento recusado");
            }
            default -> {
                log.warn("Status desconhecido: {}", payload.getStatus());
                yield ResponseEntity.badRequest().body("Status inválido");
            }
        };
    }
}
