package com.salas.edu.restaurante_gourmet_12.controller;

import com.salas.edu.restaurante_gourmet_12.model.Insumo;
import com.salas.edu.restaurante_gourmet_12.service.InsumoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/inventario")
public class InventarioController {

    @Autowired
    private InsumoService insumoService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("insumos", insumoService.findAll());
        model.addAttribute("insumosStockBajo", insumoService.findInsumosConStockBajo());
        return "inventario/listar";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("insumo", new Insumo());
        return "inventario/form";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Insumo insumo, 
                         BindingResult result, 
                         RedirectAttributes flash) {
        if (result.hasErrors()) {
            return "inventario/form";
        }
        
        insumoService.save(insumo);
        flash.addFlashAttribute("success", "Insumo guardado exitosamente");
        return "redirect:/inventario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes flash) {
        Insumo insumo = insumoService.findById(id).orElse(null);
        if (insumo == null) {
            flash.addFlashAttribute("error", "Insumo no encontrado");
            return "redirect:/inventario";
        }
        model.addAttribute("insumo", insumo);
        return "inventario/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        insumoService.deleteById(id);
        flash.addFlashAttribute("warning", "Insumo eliminado");
        return "redirect:/inventario";
    }

    @GetMapping("/alertas")
    public String alertas(Model model) {
        model.addAttribute("insumosStockBajo", insumoService.findInsumosConStockBajo());
        return "inventario/alertas";
    }
}
