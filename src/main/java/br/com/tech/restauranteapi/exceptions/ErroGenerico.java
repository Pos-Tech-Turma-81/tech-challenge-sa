package br.com.tech.restauranteapi.exceptions;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ErroGenerico {

    private String mensagem;
}
