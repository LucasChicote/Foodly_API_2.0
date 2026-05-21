package com.foodly.foodly.repository;

import com.foodly.foodly.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByCategoriaId(Long categoriaId);

    List<Produto> findByRestauranteId(Long restauranteId);

    @Query("SELECT p FROM Produto p WHERE p.isKitSustentavel = true AND p.dataExpiracao IS NOT NULL AND p.dataExpiracao < :agora")
    List<Produto> findKitsExpirados(@Param("agora") LocalDateTime agora);

    @Query("SELECT p FROM Produto p WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :termo, '%')) OR LOWER(p.descricao) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Produto> buscarPorNomeOuDescricao(@Param("termo") String termo);
}