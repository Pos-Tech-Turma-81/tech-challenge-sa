package br.com.tech.restauranteapi.gateway.impl;

import br.com.tech.restauranteapi.domain.Pedido;
import br.com.tech.restauranteapi.exceptions.NotFoundException;
import br.com.tech.restauranteapi.gateway.PedidosGateway;
import br.com.tech.restauranteapi.entity.PedidoEntity;
import br.com.tech.restauranteapi.presenter.PedidoPresenter;
import br.com.tech.restauranteapi.repository.SpringPedidoRepository;
import br.com.tech.restauranteapi.utils.enums.StatusEnum;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PedidosGatewayImpl implements PedidosGateway {

    private final SpringPedidoRepository repository;

    @Override
    @Transactional
    public Pedido salvar(Pedido pedido) {
        PedidoEntity pedidoResponse =
                this.repository
                        .save(PedidoPresenter.toEntity(pedido));
        return PedidoPresenter.toDomain(pedidoResponse);
    }

    @Override
    public Page<Pedido> listarFilaPedidos(Pageable page) {
        Page<PedidoEntity> pedidosResponse =
                this.repository.getByPedidosPreparacao(StatusEnum.EM_PREPARACAO, page);

        return pedidosResponse.map(PedidoPresenter::toDomain);
    }

    @Override
    public Pedido buscarPorId(Integer id) {
        PedidoEntity entity = repository.findByIdWithAssociacoesAndCliente(id)
                .orElseThrow(() -> new NotFoundException("Pedido não encontrado com id: " + id));
        Hibernate.initialize(entity.getAssociacoes()); // Força o carregamento
        return PedidoPresenter.toDomain(entity);
    }

    @Override
    @Transactional
    public Pedido atualizar(Pedido pedido) {
        PedidoEntity entity = PedidoPresenter.toEntity(pedido);
        PedidoEntity atualizado = repository.save(entity);
        return PedidoPresenter.toDomain(atualizado);
    }
}
