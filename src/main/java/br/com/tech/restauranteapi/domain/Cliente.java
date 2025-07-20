package br.com.tech.restauranteapi.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Cliente {
    private Integer id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private String endereco;

    public Cliente(Integer id, String nome, String email, String telefone, String cpf, String endereco) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.cpf = cpf;
        this.endereco = endereco;
    }
}