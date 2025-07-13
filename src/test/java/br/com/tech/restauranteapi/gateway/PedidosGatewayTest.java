package br.com.tech.restauranteapi.gateway;

import br.com.tech.restauranteapi.gateway.domain.Pedido;
import br.com.tech.restauranteapi.gateway.entity.PedidoEntity;
import br.com.tech.restauranteapi.gateway.impl.PedidosGatewayImpl;
import br.com.tech.restauranteapi.repository.SpringPedidoRepository;
import br.com.tech.restauranteapi.utils.enums.StatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PedidosGatewayTest {

    @Mock
    private SpringPedidoRepository repository;

    @InjectMocks
    private PedidosGatewayImpl gateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSalvar_Success() {
        // Arrange
        Pedido pedido = mock(Pedido.class);
        PedidoEntity entity = mock(PedidoEntity.class);
        PedidoEntity savedEntity = mock(PedidoEntity.class);
        Pedido mappedPedido = mock(Pedido.class);

        when(pedido.toEntity()).thenReturn(entity);
        when(repository.save(entity)).thenReturn(savedEntity);
        when(savedEntity.toPedidosDomain()).thenReturn(mappedPedido);

        // Act
        Pedido result = gateway.salvar(pedido);

        // Assert
        assertNotNull(result);
        assertEquals(mappedPedido, result);
        verify(pedido).toEntity();
        verify(repository).save(entity);
        verify(savedEntity).toPedidosDomain();
    }

    @Test
    void testListarFilaPedidos_Success() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        PedidoEntity entity1 = mock(PedidoEntity.class);
        PedidoEntity entity2 = mock(PedidoEntity.class);
        Pedido domain1 = mock(Pedido.class);
        Pedido domain2 = mock(Pedido.class);

        Page<PedidoEntity> entityPage = new PageImpl<>(List.of(entity1, entity2), pageable, 2);

        when(repository.getByPedidosPreparacao(StatusEnum.EM_PREPARACAO, pageable)).thenReturn(entityPage);
        when(entity1.toPedidosDomain()).thenReturn(domain1);
        when(entity2.toPedidosDomain()).thenReturn(domain2);

        // Act
        Page<Pedido> result = gateway.listarFilaPedidos(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(domain1, result.getContent().get(0));
        assertEquals(domain2, result.getContent().get(1));
        verify(repository).getByPedidosPreparacao(StatusEnum.EM_PREPARACAO, pageable);
        verify(entity1).toPedidosDomain();
        verify(entity2).toPedidosDomain();
    }
}
