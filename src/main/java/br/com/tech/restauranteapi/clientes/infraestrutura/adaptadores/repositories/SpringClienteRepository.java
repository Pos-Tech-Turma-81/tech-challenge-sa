package br.com.tech.restauranteapi.clientes.infraestrutura.adaptadores.repositories;

import br.com.tech.restauranteapi.clientes.dominio.dtos.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringClienteRepository extends JpaRepository<ClienteEntity, UUID> {
}
