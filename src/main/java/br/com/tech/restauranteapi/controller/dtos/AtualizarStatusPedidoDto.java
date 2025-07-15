package br.com.tech.restauranteapi.controller.dtos;

import br.com.tech.restauranteapi.utils.enums.StatusEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AtualizarStatusPedidoDto {
    @NotNull
    private StatusEnum status;
}
