package com.example.Perfulandia.repository;
import com.example.Perfulandia.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientePRepository extends JpaRepository<Pedido, Integer> {
}
