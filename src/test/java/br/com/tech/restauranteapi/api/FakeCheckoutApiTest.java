package br.com.tech.restauranteapi.api;

import br.com.tech.restauranteapi.controller.FakeCheckoutController;
import br.com.tech.restauranteapi.controller.dtos.CheckoutRequestDTO;
import br.com.tech.restauranteapi.controller.dtos.CheckoutResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class FakeCheckoutApiTest {

    @Mock
    private FakeCheckoutController controller;

    @InjectMocks
    private FakeCheckoutApi api;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void iniciarCheckout_ShouldReturnOkResponse_WithControllerResponse() {
        CheckoutRequestDTO request = new CheckoutRequestDTO();
        CheckoutResponseDTO response = new CheckoutResponseDTO("fake_qr_data");
        when(controller.iniciarCheckout(request)).thenReturn(response);

        ResponseEntity<CheckoutResponseDTO> result = api.iniciarCheckout(request);

        assertThat(result.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.OK);
        assertThat(result.getBody()).isSameAs(response);
        verify(controller, times(1)).iniciarCheckout(request);
    }

    @Test
    void iniciarCheckout_ShouldDelegateToController_Once() {
        CheckoutRequestDTO request = mock(CheckoutRequestDTO.class);
        when(controller.iniciarCheckout(any())).thenReturn(new CheckoutResponseDTO("fake_qr_data"));

        api.iniciarCheckout(request);

        verify(controller, times(1)).iniciarCheckout(request);
    }
}
