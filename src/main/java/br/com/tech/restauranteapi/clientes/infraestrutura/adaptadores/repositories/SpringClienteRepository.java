package br.com.tech.restauranteapi.clientes.infraestrutura.adaptadores.repositories;

import br.com.tech.restauranteapi.clientes.dominio.dtos.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringClienteRepository extends JpaRepository<ClienteEntity, Integer> {
    @Query("SELECT x FROM ClienteEntity x WHERE x.cpf = :cpf")
    Optional<ClienteEntity> getCliente(String cpf);
}
