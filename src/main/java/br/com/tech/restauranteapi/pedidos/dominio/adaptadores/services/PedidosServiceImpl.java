package br.com.tech.restauranteapi.pedidos.dominio.adaptadores.services;

import br.com.tech.restauranteapi.pedidos.dominio.Pedidos;
import br.com.tech.restauranteapi.pedidos.dominio.dtos.PedidosDto;
import br.com.tech.restauranteapi.pedidos.dominio.portas.interfaces.PedidosServicePort;
import br.com.tech.restauranteapi.pedidos.infraestrutura.repositories.PedidosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PedidosServiceImpl implements PedidosServicePort {

    private final PedidosRepository pedidosRepository;

    @Override
    public PedidosDto realizarCheckout(PedidosDto pedidoDto) {
        Pedidos pedido = Pedidos.builderPedidos(pedidoDto);
        pedido.setDataHoraInclusaoPedido(Timestamp.from(Instant.now()));
        pedido.setStatus("AGUARDANDO");
        return pedidosRepository.salvar(pedido).toPedidosDto();
    }

    @Override
    public List<PedidosDto> listarFilaPedidos() {
        return pedidosRepository.listarTodos()
                .stream()
                .filter(p -> "AGUARDANDO".equals(p.getStatus()))
                .map(Pedidos::toPedidosDto)
                .toList();
    }

    @Override
    public Optional<PedidosDto> obterProximoPedido() {
        return pedidosRepository.buscarProximoNaFila()
                .map(Pedidos::toPedidosDto);
    }

    @Override
    public void finalizarPedido(Integer pedidoId) {
        pedidosRepository.buscarPorId(pedidoId).ifPresent(pedido -> {
            pedido.setStatus("FINALIZADO");
            pedidosRepository.salvar(pedido);
        });
    }
}
