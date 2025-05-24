package com.example.Perfulandia.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Table(name = "Estado")
@Data
@NoArgsConstructor
@AllArgsConstructor



public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_estado;

    @OneToOne
    @JoinColumn(name = "id_pedido", referencedColumnName = "id_pedido")
    @JsonBackReference
    
    private Pedido pedido;

    @Column(nullable = false)
    private String rut;

    @Column(nullable = false)
    private String Estado_Pedido;

}




