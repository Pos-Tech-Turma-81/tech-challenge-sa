package br.com.tech.restauranteapi.gateway;

import br.com.tech.restauranteapi.controller.dtos.CheckoutRequestDTO;
import br.com.tech.restauranteapi.controller.dtos.CheckoutResponseDTO;
import br.com.tech.restauranteapi.controller.dtos.ClienteDTO;
import br.com.tech.restauranteapi.controller.dtos.MercadoPagoCheckoutRequest;
import br.com.tech.restauranteapi.domain.Cliente;

import java.util.Optional;

public interface FakeCheckoutGateway {
    CheckoutResponseDTO iniciarCheckout(MercadoPagoCheckoutRequest mercadoPagoCheckoutRequest);
}
