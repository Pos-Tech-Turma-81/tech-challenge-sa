package br.com.tech.restauranteapi.exceptions;

public class AlreadyExistsException extends RuntimeException {

    public AlreadyExistsException(String mensagem) { super(mensagem); }
}
