package br.com.tech.restauranteapi.clientes.dominio.dtos;

import lombok.Data;

@Data
public class ClienteDTO {

    private Integer id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private String endereco;

    public ClienteDTO() {}

    public ClienteDTO(Integer id, String nome, String email, String telefone, String cpf, String endereco) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.cpf = cpf;
        this.endereco = endereco;
    }
}