package br.com.tech.restauranteapi.controller;

import br.com.tech.restauranteapi.controller.dtos.CheckoutRequestDTO;
import br.com.tech.restauranteapi.controller.dtos.CheckoutResponseDTO;
import br.com.tech.restauranteapi.usecase.FakeCheckoutUsecase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class FakeCheckoutControllerTest {

    @Mock
    private FakeCheckoutUsecase usecase;

    private FakeCheckoutController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new FakeCheckoutController(usecase);
    }

    @Test
    void iniciarCheckout_shouldReturnCheckoutResponseWithQrData() {
        CheckoutRequestDTO request = new CheckoutRequestDTO();
        String qrData = "fake-qr-code-12345";
        CheckoutResponseDTO fakeResponse = new CheckoutResponseDTO(qrData);

        when(usecase.iniciarCheckout(request)).thenReturn(fakeResponse);

        CheckoutResponseDTO result = controller.iniciarCheckout(request);

        assertThat(result).isNotNull();
        assertThat(result.getQr_data()).isEqualTo(qrData);
        verify(usecase, times(1)).iniciarCheckout(request);
    }
}
