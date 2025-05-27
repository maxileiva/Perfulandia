package com.example.Perfulandia.repository;

import com.example.Perfulandia.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
