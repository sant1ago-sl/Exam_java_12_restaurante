package com.salas.edu.restaurante_gourmet_12.service;

import com.salas.edu.restaurante_gourmet_12.model.Plato;
import com.salas.edu.restaurante_gourmet_12.repository.PlatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PlatoService {

    @Autowired
    private PlatoRepository platoRepository;

    public List<Plato> findAll() {
        return platoRepository.findAll();
    }

    public Optional<Plato> findById(Long id) {
        return platoRepository.findById(id);
    }

    public Plato save(Plato plato) {
        return platoRepository.save(plato);
    }

    public Plato update(Plato plato) {
        return platoRepository.save(plato);
    }

    public void deleteById(Long id) {
        platoRepository.deleteById(id);
    }

    public List<Plato> findByEstado(Boolean estado) {
        return platoRepository.findByEstado(estado);
    }

    public List<Plato> findByTipo(String tipo) {
        return platoRepository.findByTipo(tipo);
    }

    public List<Plato> findPlatosActivos() {
        return platoRepository.findByEstado(true);
    }
}
