package com.salas.edu.restaurante_gourmet_12.controller;

import com.salas.edu.restaurante_gourmet_12.model.Mesa;
import com.salas.edu.restaurante_gourmet_12.service.MesaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador de Mesas
 * - ADMIN: Puede crear, editar, eliminar y cambiar estado de mesas
 * - MOZO: Solo puede ver mesas y cambiar estado (asignar/liberar)
 */
@Controller
@RequestMapping("/mesas")
public class MesaController {

    @Autowired
    private MesaService mesaService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("mesas", mesaService.findAll());
        return "mesas/listar";
    }

    @GetMapping("/nuevo")
    @PreAuthorize("hasRole('ADMIN')")
    public String nuevo(Model model) {
        model.addAttribute("mesa", new Mesa());
        return "mesas/form";
    }

    @PostMapping("/guardar")
    @PreAuthorize("hasRole('ADMIN')")
    public String guardar(@Valid @ModelAttribute Mesa mesa, 
                         BindingResult result, 
                         RedirectAttributes flash) {
        if (result.hasErrors()) {
            return "mesas/form";
        }
        
        mesaService.save(mesa);
        flash.addFlashAttribute("success", "Mesa guardada exitosamente");
        return "redirect:/mesas";
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes flash) {
        Mesa mesa = mesaService.findById(id).orElse(null);
        if (mesa == null) {
            flash.addFlashAttribute("error", "Mesa no encontrada");
            return "redirect:/mesas";
        }
        model.addAttribute("mesa", mesa);
        return "mesas/form";
    }

    @GetMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        mesaService.deleteById(id);
        flash.addFlashAttribute("warning", "Mesa eliminada");
        return "redirect:/mesas";
    }

    @PostMapping("/cambiar-estado/{id}")
    public String cambiarEstado(@PathVariable Long id, 
                               @RequestParam String estado, 
                               RedirectAttributes flash) {
        mesaService.cambiarEstado(id, estado);
        flash.addFlashAttribute("success", "Estado actualizado");
        return "redirect:/mesas";
    }
}
