package br.com.tech.restauranteapi.usecase.impl;

import br.com.tech.restauranteapi.domain.AssociacaoPedidoProduto;
import br.com.tech.restauranteapi.gateway.PedidosGateway;
import br.com.tech.restauranteapi.presenter.AssociacaoProdutoPresenter;
import br.com.tech.restauranteapi.usecase.AssociacaoPedidoProdutoUsecase;
import br.com.tech.restauranteapi.gateway.ClienteGateway;
import br.com.tech.restauranteapi.gateway.ProdutoGateway;
import br.com.tech.restauranteapi.domain.CriarPedido;
import br.com.tech.restauranteapi.domain.Pedido;
import br.com.tech.restauranteapi.domain.Produto;
import br.com.tech.restauranteapi.domain.ProdutoPedido;
import br.com.tech.restauranteapi.usecase.PedidosUsecase;
import br.com.tech.restauranteapi.utils.enums.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidosUsecaseImpl implements PedidosUsecase {

    private final PedidosGateway pedidosGateway;
    private final ClienteGateway clienteGateway;
    private final ProdutoGateway produtoGateway;
    private final AssociacaoPedidoProdutoUsecase associacaoPedidoProdutoUsecase;

    @Override
    public Pedido criarPedido(CriarPedido criarPedido) {
        Pedido pedido = new Pedido();

        if(Objects.nonNull(criarPedido.getClienteId())){
            pedido.setCliente(clienteGateway.buscarPorId(criarPedido.getClienteId()));
        }
        pedido.setStatus(StatusEnum.EM_PREPARACAO);

        // Salva o pedido primeiro para ter o ID
        Pedido pedidoSalvo = pedidosGateway.salvar(pedido);

        List<AssociacaoPedidoProduto> associacoes = agruparProdutos(criarPedido, pedidoSalvo);

        List<AssociacaoPedidoProduto> associcaoProdutos = associacaoPedidoProdutoUsecase.salvarTodas(associacoes);
        pedidoSalvo.setAssociacoes(
                associcaoProdutos.stream()
                        .map(AssociacaoProdutoPresenter::fromToDomain)
                        .collect(Collectors.toList())
        );
        return pedidoSalvo;
    }

    @Override
    public Page<Pedido> listarFilaPedidos(Pageable pageable) {
        return pedidosGateway.listarFilaPedidos(pageable);
    }

    @Override
    public Pedido atualizarStatus(Integer pedidoId, StatusEnum novoStatus) {
        Pedido pedido = pedidosGateway.buscarPorId(pedidoId);
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado.");
        }
        pedido.atualizarStatus(novoStatus);
        return pedidosGateway.atualizar(pedido);
    }

    private List<AssociacaoPedidoProduto> agruparProdutos(CriarPedido dto, Pedido pedidoSalvo){
         Map<Integer, Integer> quantidadePorProduto = dto.getProdutos().stream()
                .collect(Collectors.groupingBy(
                        ProdutoPedido::getProdutoId,
                        Collectors.summingInt(ProdutoPedido::getQuantidade)
                ));

        return quantidadePorProduto.entrySet().stream()
                .map(entry -> {
                    Integer produtoId = entry.getKey();
                    Integer quantidadeTotal = entry.getValue();

                    Produto produto = produtoGateway.buscarPorId(produtoId);

                    return AssociacaoPedidoProduto.builder()
                            .produto(produto)
                            .pedido(pedidoSalvo)
                            .quantidade(quantidadeTotal)
                            .preco(produto.getPreco().multiply(BigDecimal.valueOf(quantidadeTotal)))
                            .build();
                })
                .toList();
    }
}
