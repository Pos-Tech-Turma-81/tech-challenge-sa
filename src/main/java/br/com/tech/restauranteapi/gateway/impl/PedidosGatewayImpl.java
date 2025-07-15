package br.com.tech.restauranteapi.gateway.impl;

import br.com.tech.restauranteapi.domain.Pedido;
import br.com.tech.restauranteapi.gateway.PedidosGateway;
import br.com.tech.restauranteapi.entity.PedidoEntity;
import br.com.tech.restauranteapi.repository.SpringPedidoRepository;
import br.com.tech.restauranteapi.utils.enums.StatusEnum;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PedidosGatewayImpl implements PedidosGateway {

    private final SpringPedidoRepository repository;

    @Override
    @Transactional
    public Pedido salvar(Pedido pedidos) {
        PedidoEntity pedidoResponse =
                this.repository
                        .save(pedidos.toEntity());

        return pedidoResponse.toPedidosDomain();
    }

    @Override
    public Page<Pedido> listarFilaPedidos(Pageable page) {
        Page<PedidoEntity> pedidosResponse =
                this.repository.getByPedidosPreparacao(StatusEnum.EM_PREPARACAO, page);

        return pedidosResponse.map(PedidoEntity::toPedidosDomain);
    }

    @Override
    public Pedido buscarPorId(Integer id) {
        return repository.findById(id)
                .map(PedidoEntity::toPedidosDomain)
                .orElse(null);
    }

    @Override
    @Transactional
    public Pedido atualizar(Pedido pedido) {
        PedidoEntity entity = pedido.toEntity();
        PedidoEntity atualizado = repository.save(entity);
        return atualizado.toPedidosDomain();
    }
}
