package com.example.Perfulandia.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "shipments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String trackingCode;

    private String destination;

    private String status; // Ej: "PENDING", "IN_TRANSIT", "DELIVERED", etc.
}
