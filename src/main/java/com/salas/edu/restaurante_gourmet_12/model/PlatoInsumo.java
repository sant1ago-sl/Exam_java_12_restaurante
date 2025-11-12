package com.salas.edu.restaurante_gourmet_12.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "plato_insumos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlatoInsumo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlatoInsumo;
    
    @ManyToOne
    @JoinColumn(name = "id_plato", nullable = false)
    @NotNull(message = "El plato es obligatorio")
    private Plato plato;
    
    @ManyToOne
    @JoinColumn(name = "id_insumo", nullable = false)
    @NotNull(message = "El insumo es obligatorio")
    private Insumo insumo;
    
    @NotNull(message = "La cantidad es obligatoria")
    @DecimalMin(value = "0.0", message = "La cantidad debe ser mayor a 0")
    @Column(nullable = false)
    private Double cantidadUsada;
}
