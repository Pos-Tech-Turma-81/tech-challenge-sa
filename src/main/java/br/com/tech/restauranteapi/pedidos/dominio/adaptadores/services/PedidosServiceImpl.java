package br.com.tech.restauranteapi.pedidos.dominio.adaptadores.services;

import br.com.tech.restauranteapi.associacaoPedidoProduto.dominio.AssociacaoPedidoProduto;
import br.com.tech.restauranteapi.associacaoPedidoProduto.dominio.portas.interfaces.AssociacaoPedidoProdutoServicePort;
import br.com.tech.restauranteapi.clientes.dominio.Cliente;
import br.com.tech.restauranteapi.pedidos.dominio.Pedidos;
import br.com.tech.restauranteapi.pedidos.dominio.dtos.CriarPedidoDto;
import br.com.tech.restauranteapi.pedidos.dominio.dtos.PedidoResponseDto;
import br.com.tech.restauranteapi.pedidos.dominio.dtos.PedidosDto;
import br.com.tech.restauranteapi.pedidos.dominio.dtos.ProdutoPedidoResponseDto;
import br.com.tech.restauranteapi.pedidos.dominio.portas.interfaces.PedidosServicePort;
import br.com.tech.restauranteapi.pedidos.infraestrutura.repositories.PedidosRepository;
import br.com.tech.restauranteapi.produtos.dominio.Produto;
import br.com.tech.restauranteapi.produtos.infraestrutura.repositories.ProdutoRepository;
import br.com.tech.restauranteapi.utils.enums.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PedidosServiceImpl implements PedidosServicePort {

    private final PedidosRepository pedidosRepository;
    private final ProdutoRepository produtoRepository;
    private final AssociacaoPedidoProdutoServicePort associacaoPedidoProdutoService;

    @Override
    public PedidoResponseDto realizarCheckout(CriarPedidoDto dto) {
        Pedidos pedido = new Pedidos();
        pedido.setCliente(new Cliente(dto.getClienteId(), null, null, null, null, null));
        pedido.setDataHoraInclusaoPedido(Timestamp.from(Instant.now()));
        pedido.setStatus(StatusEnum.AGUARDANDO);

        Pedidos pedidoSalvo = pedidosRepository.salvar(pedido);

        List<AssociacaoPedidoProduto> associacoes = dto.getProdutos().stream()
                .map(produtoDto -> {
                    Produto produtoCompleto = produtoRepository.buscarPorId(produtoDto.getProdutoId());

                    return AssociacaoPedidoProduto.builder()
                            .pedidoId(pedidoSalvo.getId())
                            .produtoId(produtoCompleto.getId())
                            .quantidade(produtoDto.getQuantidade())
                            .preco(produtoCompleto.getPreco())
                            .produto(produtoCompleto)
                            .build();
                })
                .toList();

        associacoes.forEach(associacaoPedidoProdutoService::salvar);

        pedidoSalvo.setAssociacoes(associacoes);

        List<ProdutoPedidoResponseDto> produtos = associacoes.stream()
                .map(assoc -> ProdutoPedidoResponseDto.builder()
                        .produtoId(assoc.getProdutoId())
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
    public List<PedidosDto> listarPedidos(StatusEnum status, Integer clienteId) {
        return pedidosRepository.listarPedidos(status, clienteId)
                .stream()
                .map(Pedidos::toPedidosDto)
                .toList();
    }
}
