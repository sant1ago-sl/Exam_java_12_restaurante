package com.salas.edu.restaurante_gourmet_12.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    
    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Column(unique = true, nullable = false)
    private String nombreUsuario;
    
    @NotBlank(message = "La contraseña es obligatoria")
    @Column(nullable = false)
    private String contrasena;
    
    @NotBlank(message = "El rol es obligatorio")
    @Column(nullable = false)
    private String rol; // ADMIN, MOZO, COCINERO, CAJERO
    
    @Column(nullable = false)
    private Boolean estado = true;
    
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
}
