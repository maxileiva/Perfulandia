package com.example.Perfulandia.repository;
import com.example.Perfulandia.model.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GerenteRDVRepository extends JpaRepository<DetallePedido, Integer> {
}
