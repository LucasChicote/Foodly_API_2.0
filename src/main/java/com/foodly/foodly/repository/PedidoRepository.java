package com.foodly.foodly.repository;

import com.foodly.foodly.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByClienteIdOrderByCriadoEmDesc(Long clienteId);

    List<Pedido> findByRestauranteIdOrderByCriadoEmDesc(Long restauranteId);
}