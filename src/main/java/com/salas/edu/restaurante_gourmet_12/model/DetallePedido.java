package com.salas.edu.restaurante_gourmet_12.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "detalle_pedidos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetallePedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetallePedido;
    
    @ManyToOne
    @JoinColumn(name = "id_pedido", nullable = false)
    @lombok.ToString.Exclude
    private Pedido pedido;
    
    @ManyToOne
    @JoinColumn(name = "id_plato", nullable = false)
    @NotNull(message = "El plato es obligatorio")
    private Plato plato;
    
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    @Column(nullable = false)
    private Integer cantidad;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;
}
