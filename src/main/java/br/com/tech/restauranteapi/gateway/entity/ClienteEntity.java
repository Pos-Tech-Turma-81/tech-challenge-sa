package br.com.tech.restauranteapi.gateway.entity;

import br.com.tech.restauranteapi.gateway.domain.Cliente;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "clientes")
@Data
@AllArgsConstructor
@Builder
public class ClienteEntity {

    @Id
    @GeneratedValue
    private Integer id;
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