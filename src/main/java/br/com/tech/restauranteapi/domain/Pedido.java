package br.com.tech.restauranteapi.domain;

import br.com.tech.restauranteapi.utils.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

    private Integer id;
    private Cliente cliente;
    private StatusEnum status;
    private LocalDateTime dataHoraInclusaoPedido;
    @Builder.Default
    private List<AssociacaoProduto> associacoes = new ArrayList<>();

    public void atualizarStatus(StatusEnum novoStatus) {
        this.status = novoStatus;
    }

    public Pedido(Integer id) {
        this.id = id;
        this.associacoes = new ArrayList<>();
    }
}