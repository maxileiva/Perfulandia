package com.example.Perfulandia.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "Detalle_Pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor


public class DetallePedido {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer N;

    @Column (length = 50, nullable = false)
    private String id_pedido;

    @Column (length = 50, nullable = false)
    private String id_producto;

    @Column (nullable = false)
    private String Descripcion;

    @Column (nullable = false)
    private String cantidad;

    @Column (nullable = false)
    private BigDecimal valor;

}
