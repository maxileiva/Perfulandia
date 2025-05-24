package com.example.Perfulandia.repository;

import com.example.Perfulandia.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GerentePedidoRepository extends JpaRepository<Pedido, Integer> {

    @Query("SELECT p FROM Pedido p LEFT JOIN FETCH p.detallePedido")
    List<Pedido> findAllWithDetalles();
}

