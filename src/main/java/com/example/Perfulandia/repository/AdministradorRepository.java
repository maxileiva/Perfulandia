package com.example.Perfulandia.repository;

import com.example.Perfulandia.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministradorRepository extends JpaRepository<Cliente, Integer> {
}
