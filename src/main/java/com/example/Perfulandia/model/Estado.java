package com.example.Perfulandia.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "Estado")
@Data
@NoArgsConstructor
@AllArgsConstructor



public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_estado;

    @Column(length = 50, nullable = false)
    private String id_pedido;

    @Column(nullable = false)
    private String rut;

    @Column(nullable = false)
    private BigDecimal Estado_Pedido;

}




