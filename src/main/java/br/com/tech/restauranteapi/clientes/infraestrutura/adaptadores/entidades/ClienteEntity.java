package br.com.tech.restauranteapi.clientes.dominio.dtos;

import br.com.tech.restauranteapi.clientes.dominio.Cliente;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "cliente")
public class ClienteEntity {

    @Id
    @GeneratedValue
    private UUID id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private String endereco;

    public ClienteEntity() {}

    public ClienteEntity(Cliente cliente) {
        this.id = cliente.getId();
        this.cpf = cliente.getCpf();
        this.endereco = cliente.getEndereco();
        this.email = cliente.getEmail();
        this.nome = cliente.getNome();
        this.telefone = cliente.getTelefone();
    }

    public Cliente toCliente() {
        return new Cliente(this.id, this.nome, this.email, this.telefone, this.cpf, this.endereco);
    }
}