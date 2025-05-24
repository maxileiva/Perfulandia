package com.example.Perfulandia.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Perfulandia.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
}
