package br.com.tech.restauranteapi.usecase.impl;

import br.com.tech.restauranteapi.controller.dtos.CheckoutRequestDTO;
import br.com.tech.restauranteapi.controller.dtos.CheckoutResponseDTO;
import br.com.tech.restauranteapi.controller.dtos.MercadoPagoCheckoutRequest;
import br.com.tech.restauranteapi.controller.dtos.MercadoPagoItem;
import br.com.tech.restauranteapi.gateway.FakeCheckoutGateway;
import br.com.tech.restauranteapi.usecase.FakeCheckoutUsecase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class FakeCheckoutUsecaseImpl implements FakeCheckoutUsecase {

    public final FakeCheckoutGateway gateway;

    @Override
    public CheckoutResponseDTO iniciarCheckout(CheckoutRequestDTO checkoutRequestDTO) {

        MercadoPagoCheckoutRequest mp = new MercadoPagoCheckoutRequest();
        mp.setTitle("Pedido " + checkoutRequestDTO.getPedidoId());
        mp.setNotification_url("https://www.yourserver.com/notifications");
        mp.setExternal_reference(checkoutRequestDTO.getPedidoId().toString());
        mp.setDescription("Descrição");
        mp.setTotal_amount(25.01);

        MercadoPagoItem mp_item = new MercadoPagoItem();
        mp_item.setCategory("marketplace");
        mp_item.setDescription("AA");
        mp_item.setQuantity(1);
        mp_item.setTitle("Produto");
        mp_item.setSku_number("123");
        mp_item.setUnit_measure("unit");
        mp_item.setUnit_price(25.01);
        mp_item.setTotal_amount(25.01);
        mp.setItems(List.of(new MercadoPagoItem[]{mp_item}));

        return gateway.iniciarCheckout(mp);
    }
}
