package br.com.tech.restauranteapi.pedidos.dominio.adaptadores.services;

import br.com.tech.restauranteapi.associacaoPedidoProduto.dominio.AssociacaoPedidoProduto;
import br.com.tech.restauranteapi.associacaoPedidoProduto.dominio.AssociacaoProduto;
import br.com.tech.restauranteapi.associacaoPedidoProduto.dominio.portas.interfaces.AssociacaoPedidoProdutoServicePort;
import br.com.tech.restauranteapi.clientes.dominio.portas.repositories.ClienteRepositoryPort;
import br.com.tech.restauranteapi.pedidos.dominio.Pedido;
import br.com.tech.restauranteapi.pedidos.dominio.dtos.*;
import br.com.tech.restauranteapi.pedidos.dominio.portas.interfaces.PedidosServicePort;
import br.com.tech.restauranteapi.pedidos.infraestrutura.repositories.PedidosRepository;
import br.com.tech.restauranteapi.produtos.dominio.Produto;
import br.com.tech.restauranteapi.produtos.dominio.portas.repositories.ProdutoRepositoryPort;
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
public class PedidosServiceImpl implements PedidosServicePort {

    private final PedidosRepository pedidosRepository;
    private final ClienteRepositoryPort clienteRepository;
    private final ProdutoRepositoryPort produtosPort;
    private final AssociacaoPedidoProdutoServicePort associacaoPedidoProdutoService;

    @Override
    public PedidoResponseDto realizarCheckout(CriarPedidoDto dto) {
        Pedido pedido = new Pedido();

        if(Objects.nonNull(dto.getClienteId())){
            pedido.setCliente(clienteRepository.findById(dto.getClienteId()));
        }
        pedido.setStatus(StatusEnum.EM_PREPARACAO);

        // Salva o pedido primeiro para ter o ID
        Pedido pedidoSalvo = pedidosRepository.salvar(pedido);

        List<AssociacaoPedidoProduto> associacoes = agruparProdutos(dto, pedidoSalvo);

        List<AssociacaoPedidoProduto> associcaoProdutos = associacaoPedidoProdutoService.salvarTodas(associacoes);
        pedidoSalvo.setAssociacoes(associcaoProdutos.stream().map(AssociacaoProduto::builderAssociacao).collect(Collectors.toList()));

        // Mapear resposta
        List<ProdutoPedidoResponseDto> produtos = pedidoSalvo.getAssociacoes().stream()
                .map(assoc -> ProdutoPedidoResponseDto.builder()
                        .produtoId(assoc.getProduto().getId())
                        .nomeProduto(assoc.getProduto() != null ? assoc.getProduto().getNome() : "Produto não encontrado")
                        .quantidade(assoc.getQuantidade())
                        .preco(assoc.getPreco())
                        .build())
                .toList();

        return PedidoResponseDto.builder()
                .pedidoId(pedidoSalvo.getId())
                .clienteId(pedidoSalvo.getCliente() != null ? pedidoSalvo.getCliente().getId() : null)
                .status(pedidoSalvo.getStatus())
                .dataHora(pedidoSalvo.getDataHoraInclusaoPedido())
                .produtos(produtos)
                .build();
    }

    @Override
    public Page<PedidoDto> listarFilaPedidos(Pageable pageable) {
        return pedidosRepository.listarFilaPedidos(pageable)
                .map(Pedido::toPedidosDto);
    }

    private List<AssociacaoPedidoProduto> agruparProdutos(CriarPedidoDto dto, Pedido pedidoSalvo){
         Map<Integer, Integer> quantidadePorProduto = dto.getProdutos().stream()
                .collect(Collectors.groupingBy(
                        ProdutoPedidoDto::getProdutoId,
                        Collectors.summingInt(ProdutoPedidoDto::getQuantidade)
                ));

        return quantidadePorProduto.entrySet().stream()
                .map(entry -> {
                    Integer produtoId = entry.getKey();
                    Integer quantidadeTotal = entry.getValue();

                    Produto produto = produtosPort.buscarPorId(produtoId);

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
