package com.example.Perfulandia.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Entity
@Table(name = "Stock")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Gerente {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (length = 50, nullable = false)
    private String descripcion;

    @Column (nullable = false)
    private String concentracion;

    @Column (nullable = false)
    private String fecha_lanzamiento;

    @Column (nullable = false)
    private String cantidad;

    @Column (nullable = false)
    private BigDecimal valor;

}

