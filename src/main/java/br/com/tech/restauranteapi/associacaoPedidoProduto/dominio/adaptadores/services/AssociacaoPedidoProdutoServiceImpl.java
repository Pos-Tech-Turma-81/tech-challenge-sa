package br.com.tech.restauranteapi.associacaoPedidoProduto.dominio.adaptadores.services;

import br.com.tech.restauranteapi.associacaoPedidoProduto.dominio.AssociacaoPedidoProduto;
import br.com.tech.restauranteapi.associacaoPedidoProduto.dominio.portas.interfaces.AssociacaoPedidoProdutoServicePort;
import br.com.tech.restauranteapi.associacaoPedidoProduto.infraestrutura.repositories.AssociacaoPedidoProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AssociacaoPedidoProdutoServiceImpl implements AssociacaoPedidoProdutoServicePort {
    private final AssociacaoPedidoProdutoRepository associacaoRepository;


    public List<AssociacaoPedidoProduto> salvarTodas(List<AssociacaoPedidoProduto> associacoes) {
        return associacoes.stream()
                .map(associacaoRepository::salvar)
                .toList();
    }
}
