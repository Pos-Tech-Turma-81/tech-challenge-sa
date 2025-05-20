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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PedidosServiceImpl implements PedidosServicePort {

    private final PedidosRepository pedidosRepository;
    private final AssociacaoPedidoProdutoServicePort associacaoPedidoProdutoService;

    @Override
    public PedidoResponseDto realizarCheckout(CriarPedidoDto dto) {
        Pedidos pedido = new Pedidos();
        pedido.setCliente(new Cliente(dto.getClienteId(), null, null, null, null, null));
        pedido.setDataHoraInclusaoPedido(Timestamp.from(Instant.now()));
        pedido.setStatus("AGUARDANDO");

        // Salva o pedido primeiro para ter o ID
        Pedidos pedidoSalvo = pedidosRepository.salvar(pedido);

        List<AssociacaoPedidoProduto> associacoes = dto.getProdutos().stream()
                .map(produtoDto -> AssociacaoPedidoProduto.builder()
                        .pedidoId(pedidoSalvo.getId()) // seto o id do pedido salvo
                        .produtoId(produtoDto.getProdutoId())
                        .quantidade(produtoDto.getQuantidade())
                        .preco(produtoDto.getPreco())
                        .build())
                .toList();

        // Salva cada associação no banco via service
        associacoes.forEach(associacaoPedidoProdutoService::salvar);

        pedidoSalvo.setAssociacoes(associacoes);

        // Mapear resposta
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
    public List<PedidosDto> listarFilaPedidos() {
        return pedidosRepository.listarFilaPedidos()
                .stream()
                .filter(p -> "AGUARDANDO".equals(p.getStatus()))
                .map(Pedidos::toPedidosDto)
                .toList();
    }
}
