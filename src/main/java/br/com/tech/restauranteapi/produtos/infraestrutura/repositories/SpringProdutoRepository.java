package br.com.tech.restauranteapi.produtos.infraestrutura.repositories;

import br.com.tech.restauranteapi.produtos.infraestrutura.entidades.ProdutoEntity;
import br.com.tech.restauranteapi.utils.enums.CategoriaEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

@Component
public interface SpringProdutoRepository extends JpaRepository<ProdutoEntity, Integer> {

    @Query("SELECT p FROM ProdutoEntity p WHERE p.categoria = :categoria")
    Page<ProdutoEntity> buscarPorCategoria(CategoriaEnum categoria, Pageable page);
}
