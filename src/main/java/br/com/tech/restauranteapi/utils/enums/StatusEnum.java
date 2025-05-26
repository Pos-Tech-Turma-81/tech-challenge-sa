package br.com.tech.restauranteapi.utils.enums;

import br.com.tech.restauranteapi.exceptions.NotFoundException;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

import static java.lang.String.format;

public enum StatusEnum {
    RECEBIDO,
    EM_PREPARACAO,
    PRONTO,
    FINALIZADO;


    @JsonCreator
    public static StatusEnum obterPorNome(String nomeStatus){

        return Arrays.stream(StatusEnum.values())
                .filter(value -> value.toString().equalsIgnoreCase(nomeStatus))
                .findAny()
                .orElseThrow(() -> new NotFoundException(format("Nao existe status com o nome %s", nomeStatus)));
    }
}
