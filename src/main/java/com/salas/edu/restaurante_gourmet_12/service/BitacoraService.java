package com.salas.edu.restaurante_gourmet_12.service;

import com.salas.edu.restaurante_gourmet_12.model.Bitacora;
import com.salas.edu.restaurante_gourmet_12.repository.BitacoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BitacoraService {

    @Autowired
    private BitacoraRepository bitacoraRepository;

    public List<Bitacora> findAll() {
        return bitacoraRepository.findAll();
    }

    public List<Bitacora> findRecientes() {
        return bitacoraRepository.findTop50ByOrderByFechaHoraDesc();
    }

    public List<Bitacora> findByTabla(String tabla) {
        return bitacoraRepository.findByTabla(tabla);
    }

    public List<Bitacora> findByOperacion(String operacion) {
        return bitacoraRepository.findByOperacion(operacion);
    }
}
