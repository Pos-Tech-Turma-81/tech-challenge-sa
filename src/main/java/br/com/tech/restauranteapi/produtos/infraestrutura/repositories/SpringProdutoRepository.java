package br.com.tech.restauranteapi.produtos.infraestrutura.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import br.com.tech.restauranteapi.produtos.infraestrutura.entidades.ProdutoEntity;

import java.util.UUID;

@Component
public interface SpringProdutoRepository extends JpaRepository<ProdutoEntity, UUID> {
}
