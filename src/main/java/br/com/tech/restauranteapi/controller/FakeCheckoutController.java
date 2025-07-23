package br.com.tech.restauranteapi.controller;

import br.com.tech.restauranteapi.controller.dtos.*;
import br.com.tech.restauranteapi.usecase.FakeCheckoutUsecase;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FakeCheckoutController {

    private final FakeCheckoutUsecase usecase;

    public CheckoutResponseDTO iniciarCheckout(CheckoutRequestDTO request) {

        CheckoutResponseDTO response = usecase.iniciarCheckout(request);

        return new CheckoutResponseDTO(response.getQr_data());
    }
}
