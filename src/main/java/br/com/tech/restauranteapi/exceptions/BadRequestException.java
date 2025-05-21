package br.com.tech.restauranteapi.exceptions;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String mensagem) { super(mensagem); }
}
