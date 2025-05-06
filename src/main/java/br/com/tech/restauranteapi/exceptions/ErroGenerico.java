package br.com.tech.restauranteapi.exceptions;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ErroGenerico {

    private String mensagem;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> erros;
}
