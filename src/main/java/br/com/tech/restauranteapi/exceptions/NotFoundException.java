package br.com.tech.restauranteapi.exceptions;

public class NotFoundException  extends RuntimeException {

    public NotFoundException(String mensagem) { super(mensagem); }
}
