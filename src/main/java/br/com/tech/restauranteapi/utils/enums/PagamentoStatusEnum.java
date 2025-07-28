package br.com.tech.restauranteapi.utils.enums;

import br.com.tech.restauranteapi.exceptions.NotFoundException;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

import static java.lang.String.format;

public enum PagamentoStatusEnum {
    RECUSADO,
    EM_PROCESSAMENTO,
    APROVADO;

    @JsonCreator
    public static PagamentoStatusEnum obterPorNome(String nomeStatus){

        return Arrays.stream(PagamentoStatusEnum.values())
                .filter(value -> value.toString().equalsIgnoreCase(nomeStatus))
                .findAny()
                .orElseThrow(() -> new NotFoundException(format("Nao existe status com o nome %s", nomeStatus)));
    }
}
