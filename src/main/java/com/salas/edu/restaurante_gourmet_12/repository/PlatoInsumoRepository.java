package com.salas.edu.restaurante_gourmet_12.repository;

import com.salas.edu.restaurante_gourmet_12.model.PlatoInsumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlatoInsumoRepository extends JpaRepository<PlatoInsumo, Long> {
    List<PlatoInsumo> findByPlatoIdPlato(Long idPlato);
    List<PlatoInsumo> findByInsumoIdInsumo(Long idInsumo);
}
