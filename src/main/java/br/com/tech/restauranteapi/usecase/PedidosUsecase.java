package br.com.tech.restauranteapi.usecase;

import br.com.tech.restauranteapi.domain.CriarPedido;
import br.com.tech.restauranteapi.domain.Pedido;
import br.com.tech.restauranteapi.utils.enums.StatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PedidosUsecase {
    Pedido realizarCheckout(CriarPedido dto);
    Page<Pedido> listarFilaPedidos(Pageable pageable);
    Pedido atualizarStatus(Integer pedidoId, StatusEnum novoStatus);
}
