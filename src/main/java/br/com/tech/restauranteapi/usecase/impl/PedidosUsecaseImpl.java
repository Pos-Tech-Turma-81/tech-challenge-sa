package br.com.tech.restauranteapi.usecase.impl;

import br.com.tech.restauranteapi.gateway.domain.AssociacaoPedidoProduto;
import br.com.tech.restauranteapi.gateway.domain.AssociacaoProduto;
import br.com.tech.restauranteapi.usecase.AssociacaoPedidoProdutoUsecase;
import br.com.tech.restauranteapi.gateway.ClienteGateway;
import br.com.tech.restauranteapi.gateway.ProdutoGateway;
import br.com.tech.restauranteapi.gateway.domain.CriarPedido;
import br.com.tech.restauranteapi.gateway.domain.Pedido;
import br.com.tech.restauranteapi.gateway.domain.Produto;
import br.com.tech.restauranteapi.gateway.domain.ProdutoPedido;
import br.com.tech.restauranteapi.gateway.impl.PedidosGatewayImpl;
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

    private final PedidosGatewayImpl pedidosRepository;
    private final ClienteGateway clienteGateway;
    private final ProdutoGateway produtoGateway;
    private final AssociacaoPedidoProdutoUsecase associacaoPedidoProdutoUsecase;

    @Override
    public Pedido realizarCheckout(CriarPedido criarPedido) {
        Pedido pedido = new Pedido();

        if(Objects.nonNull(criarPedido.getClienteId())){
            pedido.setCliente(clienteGateway.buscarPorId(criarPedido.getClienteId()));
        }
        pedido.setStatus(StatusEnum.EM_PREPARACAO);

        // Salva o pedido primeiro para ter o ID
        Pedido pedidoSalvo = pedidosRepository.salvar(pedido);

        List<AssociacaoPedidoProduto> associacoes = agruparProdutos(criarPedido, pedidoSalvo);

        List<AssociacaoPedidoProduto> associcaoProdutos = associacaoPedidoProdutoUsecase.salvarTodas(associacoes);
        pedidoSalvo.setAssociacoes(associcaoProdutos.stream().map(AssociacaoProduto::builderAssociacao).collect(Collectors.toList()));



        return pedidoSalvo;
    }

    @Override
    public Page<Pedido> listarFilaPedidos(Pageable pageable) {
        return pedidosRepository.listarFilaPedidos(pageable);
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
