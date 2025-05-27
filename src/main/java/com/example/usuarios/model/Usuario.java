package com.example.usuarios.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    private String nombre;
    private String apellido;
    private String correo;
    private String rol; // Ej: ADMIN, GERENTE, CLIENTE
    private String contraseña; // Puedes luego aplicar encriptación
}
