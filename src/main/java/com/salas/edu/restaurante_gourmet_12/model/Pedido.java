package com.salas.edu.restaurante_gourmet_12.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;
    
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name = "id_mesa", nullable = false)
    private Mesa mesa;
    
    @Column(nullable = false)
    private LocalDateTime fechaHora = LocalDateTime.now();
    
    @Column(nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();
    
    @Column(nullable = false)
    private String estado = "PENDIENTE"; // PENDIENTE, EN_PREPARACION, LISTO, SERVIDO, CERRADO
    
    private String observaciones;
    
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @lombok.ToString.Exclude
    private List<DetallePedido> detalles = new ArrayList<>();
    
    // Alias para compatibilidad
    public Long getId() {
        return idPedido;
    }
    
    public void setId(Long id) {
        this.idPedido = id;
    }
}
