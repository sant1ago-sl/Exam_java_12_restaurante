package com.salas.edu.restaurante_gourmet_12.repository;

import com.salas.edu.restaurante_gourmet_12.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {
    Optional<Factura> findByPedidoIdPedido(Long idPedido);
    List<Factura> findByEstado(String estado);
    
    @Query("SELECT f FROM Factura f WHERE f.fechaEmision BETWEEN ?1 AND ?2")
    List<Factura> findByFechaEmisionBetween(LocalDateTime inicio, LocalDateTime fin);
}
