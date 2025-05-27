package com.example.Perfulandia.repository;

import com.example.Perfulandia.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
