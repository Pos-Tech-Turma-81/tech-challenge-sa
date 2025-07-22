package br.com.tech.restauranteapi.controller;

import br.com.tech.restauranteapi.controller.dtos.*;
import br.com.tech.restauranteapi.usecase.FakeCheckoutUsecase;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkout")
@AllArgsConstructor
public class FakeCheckoutController {

    private final FakeCheckoutUsecase usecase;

    @PostMapping()
    public ResponseEntity<CheckoutResponseDTO> iniciarCheckout(@RequestBody CheckoutRequestDTO request) {

        CheckoutResponseDTO response = usecase.iniciarCheckout(request);

        return ResponseEntity.ok(new CheckoutResponseDTO(response.getQr_data()));
    }
}
