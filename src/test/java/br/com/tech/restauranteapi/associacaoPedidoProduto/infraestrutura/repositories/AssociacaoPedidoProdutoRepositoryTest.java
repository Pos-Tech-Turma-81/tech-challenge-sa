package br.com.tech.restauranteapi.associacaoPedidoProduto.infraestrutura.repositories;

import br.com.tech.restauranteapi.associacaoPedidoProduto.dominio.AssociacaoPedidoProduto;
import br.com.tech.restauranteapi.associacaoPedidoProduto.infraestrutura.entidades.AssociacaoPedidoProdutoEntity;
import br.com.tech.restauranteapi.gateway.domain.Pedido;
import br.com.tech.restauranteapi.gateway.entity.id.AssociacaoPedidoProdutoId;
import br.com.tech.restauranteapi.gateway.entity.PedidoEntity;
import br.com.tech.restauranteapi.gateway.domain.Produto;
import br.com.tech.restauranteapi.gateway.entity.ProdutoEntity;
import br.com.tech.restauranteapi.utils.enums.CategoriaEnum;
import br.com.tech.restauranteapi.utils.enums.StatusEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssociacaoPedidoProdutoRepositoryTest {

    @Mock
    private AssociacaoPedidoProdutoJpaRepository jpaRepository;

    @InjectMocks
    private AssociacaoPedidoProdutoRepository repository;

    @Test
    void deveSalvarAssociacaoComSucesso() {
        Pedido pedido = Pedido.builder().id(2).dataHoraInclusaoPedido(LocalDateTime.now()).status(StatusEnum.EM_PREPARACAO).build();

        Produto produto1 = Produto.builder().id(1).preco(BigDecimal.TEN).nome("X-burger").categoria(CategoriaEnum.LANCHE).build();
        AssociacaoPedidoProduto associacao = new AssociacaoPedidoProduto(2, pedido, new BigDecimal(20), produto1);
        AssociacaoPedidoProdutoEntity entity = associacao.toEntity();

        AssociacaoPedidoProdutoEntity entitySalva = AssociacaoPedidoProdutoEntity.builder()
                .id(AssociacaoPedidoProdutoId.builder().produto(ProdutoEntity.builder().id(1).build()).pedido(PedidoEntity.builder().id(1).build()).build())
                .quantidade(2)
                .preco(BigDecimal.TEN)
                .build();

        when(jpaRepository.save(entity)).thenReturn(entitySalva);

        // Quando
        AssociacaoPedidoProduto resultado = repository.salvar(associacao);

        // Então
        verify(jpaRepository).save(entity);
        assertThat(resultado).isNotNull();
        assertThat(resultado.getProduto().getId()).isEqualTo(1);
        assertThat(resultado.getQuantidade()).isEqualTo(2);
        assertThat(resultado.getPreco()).isEqualByComparingTo(BigDecimal.TEN);
    }
}