package br.com.tech.restauranteapi.utils.enums;

import br.com.tech.restauranteapi.exceptions.NotFoundException;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

import static java.lang.String.format;

public enum StatusEnum {
    AGUARDANDO,
    CONFIRMADO,
    CANCELADO,
    ENTREGUE;

    @JsonCreator
    public static StatusEnum from(String value) {
        return Arrays.stream(StatusEnum.values())
                .filter(status -> status.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(
                        format("StatusPedido inválido: %s", value))
                );
    }
}
