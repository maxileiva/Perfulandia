package com.example.Perfulandia.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

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
    private String Descripcion;

    @Column (nullable = false)
    private String concentracion;

    @Column (nullable = false)
    private String fecha_lanzamiento;

    @Column (nullable = false)
    private String Cantidad;

    @Column (nullable = false)
    private BigDecimal valor;

}
