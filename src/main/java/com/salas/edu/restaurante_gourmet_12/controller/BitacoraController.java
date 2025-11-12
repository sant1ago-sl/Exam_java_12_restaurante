package com.salas.edu.restaurante_gourmet_12.controller;

import com.salas.edu.restaurante_gourmet_12.service.BitacoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controlador de Bitácora - Solo accesible para ADMIN
 * Permite consultar el historial completo de auditoría del sistema
 */
@Controller
@RequestMapping("/bitacora")
@PreAuthorize("hasRole('ADMIN')")
public class BitacoraController {

    @Autowired
    private BitacoraService bitacoraService;

    /**
     * Listado completo de auditoría
     */
    @GetMapping
    public String index(Model model) {
        model.addAttribute("registros", bitacoraService.findRecientes());
        model.addAttribute("titulo", "Bitácora de Auditoría");
        return "bitacora/index";
    }

    /**
     * Filtrar por tabla
     */
    @GetMapping("/filtrar")
    public String filtrar(@RequestParam(required = false) String tabla,
                         @RequestParam(required = false) String operacion,
                         Model model) {
        
        if (tabla != null && !tabla.isEmpty()) {
            model.addAttribute("registros", bitacoraService.findByTabla(tabla));
            model.addAttribute("filtro", "Tabla: " + tabla);
        } else if (operacion != null && !operacion.isEmpty()) {
            model.addAttribute("registros", bitacoraService.findByOperacion(operacion));
            model.addAttribute("filtro", "Operación: " + operacion);
        } else {
            model.addAttribute("registros", bitacoraService.findRecientes());
        }
        
        model.addAttribute("titulo", "Bitácora de Auditoría");
        return "bitacora/index";
    }
}
