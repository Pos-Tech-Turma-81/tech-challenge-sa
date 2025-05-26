package br.com.tech.restauranteapi.pedidos.infraestrutura.repositories;

import br.com.tech.restauranteapi.pedidos.dominio.Pedido;
import br.com.tech.restauranteapi.pedidos.dominio.portas.repositories.PedidosRepositoryPort;
import br.com.tech.restauranteapi.pedidos.infraestrutura.entidades.PedidoEntity;
import br.com.tech.restauranteapi.utils.enums.StatusEnum;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PedidosRepository implements PedidosRepositoryPort {

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
}
