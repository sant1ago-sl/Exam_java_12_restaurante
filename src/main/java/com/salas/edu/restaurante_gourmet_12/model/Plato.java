package com.salas.edu.restaurante_gourmet_12.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "platos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plato {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlato;
    
    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String nombre;
    
    @NotBlank(message = "El tipo es obligatorio")
    @Column(nullable = false)
    private String tipo; // ENTRADA, FONDO, POSTRE, BEBIDA
    
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", message = "El precio debe ser mayor a 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;
    
    @Column(length = 500)
    private String descripcion;
    
    @Column(nullable = false)
    private Boolean estado = true;
    
    // Alias para compatibilidad
    public Long getId() {
        return idPlato;
    }
    
    public void setId(Long id) {
        this.idPlato = id;
    }
}
