package br.com.tech.restauranteapi.api;

import br.com.tech.restauranteapi.controller.FakeCheckoutController;
import br.com.tech.restauranteapi.controller.dtos.*;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
@AllArgsConstructor
public class FakeCheckoutApi {

    private final FakeCheckoutController controller;

    @PostMapping("/checkout")
    public ResponseEntity<CheckoutResponseDTO> iniciarCheckout(@RequestBody CheckoutRequestDTO request) {

        var response = controller.iniciarCheckout(request);

        return ResponseEntity.ok(response);
    }
}
