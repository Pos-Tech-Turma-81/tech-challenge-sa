package br.com.tech.restauranteapi.usecase.impl;

import br.com.tech.restauranteapi.gateway.domain.AssociacaoPedidoProduto;
import br.com.tech.restauranteapi.gateway.impl.AssociacaoPedidoGatewayImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AssociacaoPedidoProdutoUsecaseImpl implements br.com.tech.restauranteapi.usecase.AssociacaoPedidoProdutoUsecase {
    private final AssociacaoPedidoGatewayImpl gateway;


    public List<AssociacaoPedidoProduto> salvarTodas(List<AssociacaoPedidoProduto> associacoes) {
        return associacoes.stream()
                .map(gateway::salvar)
                .toList();
    }
}
