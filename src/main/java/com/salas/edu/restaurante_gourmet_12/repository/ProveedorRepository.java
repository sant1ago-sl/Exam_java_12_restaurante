package com.salas.edu.restaurante_gourmet_12.repository;

import com.salas.edu.restaurante_gourmet_12.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    Optional<Proveedor> findByRuc(String ruc);
    List<Proveedor> findByEstado(Boolean estado);
    List<Proveedor> findByNombreContainingIgnoreCase(String nombre);
    boolean existsByRuc(String ruc);
}
