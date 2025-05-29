package com.example.Perfulandia.repository;

import com.example.Perfulandia.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LogisticaRepository extends JpaRepository<Estado, Integer> {
    List<Estado> findByEstadoPedido(String estadoPedido);
}