package br.com.tech.restauranteapi.controller.dtos;

import br.com.tech.restauranteapi.domain.AssociacaoProduto;
import br.com.tech.restauranteapi.domain.Pedido;
import br.com.tech.restauranteapi.utils.enums.StatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDto {

    private Integer id;
    private ClienteDTO cliente;
    private StatusEnum status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataHoraInclusaoPedido;
    private List<AssociacaoProduto> associacoes;

}
