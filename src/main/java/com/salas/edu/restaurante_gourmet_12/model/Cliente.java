package com.salas.edu.restaurante_gourmet_12.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;
    
    @NotBlank(message = "El DNI es obligatorio")
    @Column(unique = true, nullable = false, length = 8)
    private String dni;
    
    @NotBlank(message = "Los nombres son obligatorios")
    @Column(nullable = false)
    private String nombres;
    
    @NotBlank(message = "Los apellidos son obligatorios")
    @Column(nullable = false)
    private String apellidos;
    
    private String telefono;
    private String correo;
    
    @Column(nullable = false)
    private Boolean estado = true;
    
    // Método helper para obtener nombre completo
    @Transient
    public String getNombre() {
        return nombres + " " + apellidos;
    }
    
    // Alias para compatibilidad
    public Long getId() {
        return idCliente;
    }
    
    public void setId(Long id) {
        this.idCliente = id;
    }
}
