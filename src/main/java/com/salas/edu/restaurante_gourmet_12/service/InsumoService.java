package com.salas.edu.restaurante_gourmet_12.service;

import com.salas.edu.restaurante_gourmet_12.model.Insumo;
import com.salas.edu.restaurante_gourmet_12.repository.InsumoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InsumoService {

    @Autowired
    private InsumoRepository insumoRepository;

    public List<Insumo> findAll() {
        return insumoRepository.findAll();
    }

    public Optional<Insumo> findById(Long id) {
        return insumoRepository.findById(id);
    }

    public Insumo save(Insumo insumo) {
        return insumoRepository.save(insumo);
    }

    public Insumo update(Insumo insumo) {
        return insumoRepository.save(insumo);
    }

    public void deleteById(Long id) {
        insumoRepository.deleteById(id);
    }

    public List<Insumo> findInsumosConStockBajo() {
        return insumoRepository.findInsumosConStockBajo();
    }

    public void actualizarStock(Long id, Double cantidad) {
        Insumo insumo = insumoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Insumo no encontrado"));
        insumo.setStock(insumo.getStock() + cantidad);
        insumoRepository.save(insumo);
    }
}
