package com.example.Perfulandia.repository;

import com.example.Perfulandia.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;



@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByrutc(String rutc);
}
