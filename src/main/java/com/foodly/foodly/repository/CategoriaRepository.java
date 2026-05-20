package com.foodly.foodly.repository;

import com.foodly.foodly.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    boolean existsByNome(String nome);

    Optional<Categoria> findByNome(String nome);

    List<Categoria> findByTipo(String tipo);
}
