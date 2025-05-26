package br.com.tech.restauranteapi.utils.enums;

import br.com.tech.restauranteapi.exceptions.NotFoundException;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;
import static java.lang.String.format;

public enum CategoriaEnum {
    LANCHE,
    ACOMPANHAMENTO,
    BEBIDA,
    SOBREMESA;

    @JsonCreator
    public static CategoriaEnum obterPorNome(String nomeCategoria){

        return Arrays.stream(CategoriaEnum.values())
                .filter(value -> value.toString().equalsIgnoreCase(nomeCategoria))
                .findAny()
                .orElseThrow(() -> new NotFoundException(format("Nao existe categoria com o nome %s", nomeCategoria)));
    }
}
