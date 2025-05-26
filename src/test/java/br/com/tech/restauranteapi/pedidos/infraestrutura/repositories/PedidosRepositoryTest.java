package br.com.tech.restauranteapi.pedidos.infraestrutura.repositories;

import br.com.tech.restauranteapi.clientes.dominio.Cliente;
import br.com.tech.restauranteapi.clientes.dominio.dtos.ClienteEntity;
import br.com.tech.restauranteapi.pedidos.dominio.Pedido;
import br.com.tech.restauranteapi.pedidos.infraestrutura.entidades.PedidoEntity;
import br.com.tech.restauranteapi.utils.enums.StatusEnum;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PedidosRepositoryTest { private SpringPedidoRepository springPedidoRepository;
    private PedidosRepository pedidosRepository;

    @BeforeEach
    void setup() {
        springPedidoRepository = mock(SpringPedidoRepository.class);
        pedidosRepository = new PedidosRepository(springPedidoRepository);
    }

    @Test
    void deveSalvarPedidoComSucesso() {
        Pedido pedido = new Pedido(1, Cliente.builder().id(1).build(), StatusEnum.EM_PREPARACAO, LocalDateTime.now(), List.of());
        PedidoEntity entityMock = pedido.toEntity();

        when(springPedidoRepository.save(any(PedidoEntity.class))).thenReturn(entityMock);

        Pedido salvo = pedidosRepository.salvar(pedido);

        ArgumentCaptor<PedidoEntity> captor = ArgumentCaptor.forClass(PedidoEntity.class);
        verify(springPedidoRepository).save(captor.capture());

        assertThat(salvo).isNotNull();
        assertThat(salvo.getId()).isEqualTo(pedido.getId());
        assertThat(captor.getValue().getId()).isEqualTo(pedido.getId());
    }

    @Test
    void deveListarPedidosEmPreparacao() {
        Pageable pageable = PageRequest.of(0, 10);
        Pedido pedido = new Pedido(1, Cliente.builder().id(1).build(), StatusEnum.EM_PREPARACAO, LocalDateTime.now(), List.of());

        PedidoEntity pedidoEntity = new PedidoEntity(1, ClienteEntity.builder().id(1).build(), StatusEnum.EM_PREPARACAO, LocalDateTime.now(), List.of());
        Page<PedidoEntity> entityPage = new PageImpl<>(List.of(pedidoEntity), pageable, 1);

        when(springPedidoRepository.getByPedidosPreparacao(StatusEnum.EM_PREPARACAO, pageable)).thenReturn(entityPage);

        Page<Pedido> result = pedidosRepository.listarFilaPedidos(pageable);

        verify(springPedidoRepository).getByPedidosPreparacao(StatusEnum.EM_PREPARACAO, pageable);
        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0).getId()).isEqualTo(pedidoEntity.getId());
    }
}