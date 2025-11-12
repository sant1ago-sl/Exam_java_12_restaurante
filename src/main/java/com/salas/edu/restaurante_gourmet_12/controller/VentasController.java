package com.salas.edu.restaurante_gourmet_12.controller;

import com.salas.edu.restaurante_gourmet_12.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;

/**
 * Controlador de Ventas
 * Solo CAJERO y ADMIN pueden ver reportes de ventas
 */
@Controller
@RequestMapping("/ventas")
@PreAuthorize("hasAnyRole('CAJERO', 'ADMIN')")
public class VentasController {

    @Autowired
    private FacturaService facturaService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("facturasHoy", facturaService.findFacturasHoy());
        BigDecimal totalVentas = facturaService.calcularTotalVentasHoy();
        model.addAttribute("totalVentasHoy", totalVentas);
        return "ventas/index";
    }

    @GetMapping("/reportes")
    public String reportes(Model model) {
        model.addAttribute("facturas", facturaService.findAll());
        return "ventas/reportes";
    }
}
