package com.foodly.foodly.repository;

import com.foodly.foodly.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

    List<Restaurante> findByDonoId(Long donoId);
}