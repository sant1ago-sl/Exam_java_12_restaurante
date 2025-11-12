package com.salas.edu.restaurante_gourmet_12.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "detalle_facturas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleFactura {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalleFactura;
    
    @ManyToOne
    @JoinColumn(name = "id_factura", nullable = false)
    private Factura factura;
    
    @Column(nullable = false)
    private String concepto;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;
}
