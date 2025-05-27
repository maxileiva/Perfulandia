package com.example.Perfulandia.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Table(name = "Factura")
@Data
@NoArgsConstructor
@AllArgsConstructor



public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_factura;

    @Column(nullable = false)
    private String id_pedido;

    @Column(nullable = false)
    private String rut;

    @Column(nullable = false)
    private String fecha_pedido;

    @Column(nullable = false)
    private String cantidad_perfumes;

    @Column(nullable = false)
    private String valor_total;

}



