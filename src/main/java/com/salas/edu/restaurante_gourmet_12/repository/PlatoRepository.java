package com.salas.edu.restaurante_gourmet_12.repository;

import com.salas.edu.restaurante_gourmet_12.model.Plato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlatoRepository extends JpaRepository<Plato, Long> {
    List<Plato> findByEstado(Boolean estado);
    List<Plato> findByTipo(String tipo);
    List<Plato> findByNombreContainingIgnoreCase(String nombre);
}
