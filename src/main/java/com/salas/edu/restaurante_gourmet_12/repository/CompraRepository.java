package com.salas.edu.restaurante_gourmet_12.repository;

import com.salas.edu.restaurante_gourmet_12.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {
    List<Compra> findByProveedorIdProveedor(Long idProveedor);
    
    @Query("SELECT c FROM Compra c WHERE c.fechaCompra BETWEEN ?1 AND ?2")
    List<Compra> findByFechaCompraBetween(LocalDateTime inicio, LocalDateTime fin);
}
