package com.salas.edu.restaurante_gourmet_12.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "bitacora")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bitacora {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBitacora;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    
    @Column(nullable = false, length = 1000)
    private String accion;
    
    @Column(nullable = false)
    private LocalDateTime fechaHora = LocalDateTime.now();
    
    private String tabla;
    private String operacion; // CREATE, UPDATE, DELETE
    private String detalles;
}
