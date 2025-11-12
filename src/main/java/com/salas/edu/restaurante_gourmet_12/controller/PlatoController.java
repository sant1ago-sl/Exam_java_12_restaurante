package com.salas.edu.restaurante_gourmet_12.controller;

import com.salas.edu.restaurante_gourmet_12.model.Plato;
import com.salas.edu.restaurante_gourmet_12.service.PlatoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador de Platos - Solo accesible para ADMIN
 * ADMIN puede crear, editar, eliminar y cambiar precios de platos
 */
@Controller
@RequestMapping("/platos")
@PreAuthorize("hasRole('ADMIN')")
public class PlatoController {

    @Autowired
    private PlatoService platoService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("platos", platoService.findAll());
        return "platos/listar";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("plato", new Plato());
        return "platos/form";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Plato plato, 
                         BindingResult result, 
                         RedirectAttributes flash) {
        if (result.hasErrors()) {
            return "platos/form";
        }
        
        platoService.save(plato);
        flash.addFlashAttribute("success", "Plato guardado exitosamente");
        return "redirect:/platos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes flash) {
        Plato plato = platoService.findById(id).orElse(null);
        if (plato == null) {
            flash.addFlashAttribute("error", "Plato no encontrado");
            return "redirect:/platos";
        }
        model.addAttribute("plato", plato);
        return "platos/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        platoService.deleteById(id);
        flash.addFlashAttribute("warning", "Plato eliminado");
        return "redirect:/platos";
    }
}
