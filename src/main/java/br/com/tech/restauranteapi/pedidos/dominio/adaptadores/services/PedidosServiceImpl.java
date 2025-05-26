package br.com.tech.restauranteapi.pedidos.dominio.adaptadores.services;

import br.com.tech.restauranteapi.associacaoPedidoProduto.dominio.AssociacaoPedidoProduto;
import br.com.tech.restauranteapi.associacaoPedidoProduto.dominio.portas.interfaces.AssociacaoPedidoProdutoServicePort;
import br.com.tech.restauranteapi.clientes.dominio.portas.repositories.ClienteRepositoryPort;
import br.com.tech.restauranteapi.pedidos.dominio.Pedido;
import br.com.tech.restauranteapi.pedidos.dominio.dtos.CriarPedidoDto;
import br.com.tech.restauranteapi.pedidos.dominio.dtos.PedidoResponseDto;
import br.com.tech.restauranteapi.pedidos.dominio.dtos.PedidoDto;
import br.com.tech.restauranteapi.pedidos.dominio.dtos.ProdutoPedidoResponseDto;
import br.com.tech.restauranteapi.pedidos.dominio.portas.interfaces.PedidosServicePort;
import br.com.tech.restauranteapi.pedidos.infraestrutura.repositories.PedidosRepository;
import br.com.tech.restauranteapi.utils.enums.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PedidosServiceImpl implements PedidosServicePort {

    private final PedidosRepository pedidosRepository;
    private final ClienteRepositoryPort clienteRepository;
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
    public List<PedidoDto> listarFilaPedidos() {
        return pedidosRepository.listarFilaPedidos()
                .stream()
                .filter(p -> StatusEnum.EM_PREPARACAO.equals(p.getStatus()))
                .map(Pedido::toPedidosDto)
                .toList();
    }
}
