package br.com.tech.restauranteapi.usecase;

import br.com.tech.restauranteapi.controller.dtos.CheckoutRequestDTO;
import br.com.tech.restauranteapi.controller.dtos.CheckoutResponseDTO;

public interface FakeCheckoutUsecase {
    CheckoutResponseDTO iniciarCheckout(CheckoutRequestDTO checkoutRequestDTO);
}
