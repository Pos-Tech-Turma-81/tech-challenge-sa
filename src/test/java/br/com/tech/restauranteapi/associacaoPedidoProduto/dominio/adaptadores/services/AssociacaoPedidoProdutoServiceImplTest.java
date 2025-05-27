package br.com.tech.restauranteapi.associacaoPedidoProduto.dominio.adaptadores.services;

import br.com.tech.restauranteapi.associacaoPedidoProduto.dominio.AssociacaoPedidoProduto;
import br.com.tech.restauranteapi.associacaoPedidoProduto.infraestrutura.repositories.AssociacaoPedidoProdutoRepository;
import br.com.tech.restauranteapi.pedidos.dominio.Pedido;
import br.com.tech.restauranteapi.produtos.dominio.Produto;
import br.com.tech.restauranteapi.utils.enums.CategoriaEnum;
import br.com.tech.restauranteapi.utils.enums.StatusEnum;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssociacaoPedidoProdutoServiceImplTest {

    @InjectMocks
    private AssociacaoPedidoProdutoServiceImpl service;

    @Mock
    private AssociacaoPedidoProdutoRepository associacaoRepository;


    @Test
    void deveSalvarTodasAsAssociacoes() {
        Pedido pedido = Pedido.builder().id(2).dataHoraInclusaoPedido(LocalDateTime.now()).status(StatusEnum.EM_PREPARACAO).build();
        Produto produto1 = Produto.builder().id(1).preco(BigDecimal.TEN).nome("X-burger").categoria(CategoriaEnum.LANCHE).build();
        Produto produto2 = Produto.builder().id(2).preco(BigDecimal.TEN).nome("Refrigerante").categoria(CategoriaEnum.BEBIDA).build();
        Produto produto3 = Produto.builder().id(2).preco(new BigDecimal(12)).nome("Batata frita").categoria(CategoriaEnum.ACOMPANHAMENTO).build();
        // Dado
        AssociacaoPedidoProduto associacao1 = new AssociacaoPedidoProduto(2, pedido, new BigDecimal(20), produto1);
        AssociacaoPedidoProduto associacao2 = new AssociacaoPedidoProduto(1, pedido, BigDecimal.TEN, produto2);
        AssociacaoPedidoProduto associacao3 = new AssociacaoPedidoProduto(1, pedido, new BigDecimal(12), produto3);
        List<AssociacaoPedidoProduto> associacoes = List.of(associacao1, associacao2, associacao3);

        when(associacaoRepository.salvar(associacao1)).thenReturn(associacao1);
        when(associacaoRepository.salvar(associacao2)).thenReturn(associacao2);
        when(associacaoRepository.salvar(associacao3)).thenReturn(associacao3);

        // Quando
        List<AssociacaoPedidoProduto> resultado = service.salvarTodas(associacoes);

        // Então
        verify(associacaoRepository).salvar(associacao1);
        verify(associacaoRepository).salvar(associacao2);
        verify(associacaoRepository).salvar(associacao3);

        assertThat(resultado).hasSize(3);
        assertThat(resultado).containsExactly(associacao1, associacao2, associacao3);
    }


}