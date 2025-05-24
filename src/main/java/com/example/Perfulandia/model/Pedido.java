package com.example.Perfulandia.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "Pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor



public class Pedido {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id_pedido;

    @Column (length = 50, nullable = false)
    private String rutc;

    @Column (nullable = false)
    private String fecha_pedido;

    @Column (nullable = false)
    private String neto;

    @Column (nullable = false)
    private String iva;

    @Column (nullable = false)
    private BigDecimal total;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<DetallePedido> detallePedido;

    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Estado estado;

}




