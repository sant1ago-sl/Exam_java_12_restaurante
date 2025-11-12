package com.salas.edu.restaurante_gourmet_12.controller;

import com.salas.edu.restaurante_gourmet_12.service.FacturaService;
import com.salas.edu.restaurante_gourmet_12.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador de Facturas
 * Solo CAJERO y ADMIN pueden generar y gestionar facturas
 */
@Controller
@RequestMapping("/facturas")
@PreAuthorize("hasAnyRole('CAJERO', 'ADMIN')")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("facturas", facturaService.findAll());
        return "facturas/listar";
    }

    @GetMapping("/nueva/{idPedido}")
    public String nueva(@PathVariable Long idPedido, Model model, RedirectAttributes flash) {
        try {
            model.addAttribute("pedido", pedidoService.findById(idPedido).orElse(null));
            return "facturas/nueva";
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error: " + e.getMessage());
            return "redirect:/pedidos";
        }
    }

    @PostMapping("/generar")
    public String generar(@RequestParam Long idPedido, 
                         @RequestParam String metodoPago, 
                         RedirectAttributes flash) {
        try {
            facturaService.generarFacturaDesdePedido(idPedido, metodoPago);
            flash.addFlashAttribute("success", "Factura generada exitosamente");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al generar factura: " + e.getMessage());
        }
        return "redirect:/facturas";
    }

    @PostMapping("/pagar/{id}")
    public String pagar(@PathVariable Long id, RedirectAttributes flash) {
        try {
            facturaService.marcarComoPagada(id);
            flash.addFlashAttribute("success", "Factura marcada como pagada");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/facturas";
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Long id, Model model, RedirectAttributes flash) {
        model.addAttribute("factura", facturaService.findById(id).orElse(null));
        return "facturas/ver";
    }
}
