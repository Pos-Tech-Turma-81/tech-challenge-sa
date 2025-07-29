package br.com.tech.restauranteapi.controller;

import br.com.tech.restauranteapi.controller.dtos.*;
import br.com.tech.restauranteapi.domain.Pedido;
import br.com.tech.restauranteapi.presenter.CriarPedidoPresenter;
import br.com.tech.restauranteapi.presenter.PedidoPresenter;
import br.com.tech.restauranteapi.usecase.PedidosUsecase;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@Transactional
public class PedidosController {

    private final PedidosUsecase pedidosService;

    public PedidoResponseDto criarPedido(CriarPedidoDto criarPedidoDto) {
        Pedido response = pedidosService.criarPedido(
                CriarPedidoPresenter.toDomain(criarPedidoDto)
        );
        return PedidoPresenter.toResponseDto(response);
    }

    public Page<PedidoDto> listarFilaPedidos(Pageable pageable) {
        Page<Pedido> pedidos = pedidosService.listarFilaPedidos(pageable);
        return pedidos.map(PedidoPresenter::toDto);
    }

    public PedidoResponseDto atualizarStatus(Integer pedidoId, AtualizarStatusPedidoDto dto) {
        Pedido pedidoAtualizado = pedidosService.atualizarStatus(pedidoId, dto.getStatus());
        return PedidoPresenter.toResponseDto(pedidoAtualizado);
    }
}