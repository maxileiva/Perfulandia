package com.example.Perfulandia.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "Cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Cliente {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (length = 9, nullable = false)
    private String rut;

    @Column (nullable = false)
    private String nombre;

    @Column (nullable = false)
    private String apellido;

    @Column (nullable = false, length = 9)
    private String telefono;

    @Column (nullable = false)
    private String correo;

    @Column (nullable = false)
    private String direccion;

    @Column (nullable = false, length = 1)
    private String rol;

}


