package com.salas.edu.restaurante_gourmet_12.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mesas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mesa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMesa;
    
    @NotNull(message = "El número de mesa es obligatorio")
    @Column(unique = true, nullable = false)
    private Integer numero;
    
    @Min(value = 1, message = "La capacidad debe ser al menos 1")
    @Column(nullable = false)
    private Integer capacidad;
    
    @NotNull(message = "El estado es obligatorio")
    @Column(nullable = false)
    private String estado = "DISPONIBLE"; // DISPONIBLE, OCUPADA, RESERVADA, MANTENIMIENTO
    
    // Alias para compatibilidad
    public Long getId() {
        return idMesa;
    }
    
    public void setId(Long id) {
        this.idMesa = id;
    }
}
