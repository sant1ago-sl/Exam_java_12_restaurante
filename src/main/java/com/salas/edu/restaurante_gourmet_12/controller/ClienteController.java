package com.salas.edu.restaurante_gourmet_12.controller;

import com.salas.edu.restaurante_gourmet_12.model.Cliente;
import com.salas.edu.restaurante_gourmet_12.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador de Clientes
 * - ADMIN: Puede crear, editar y eliminar clientes
 * - MOZO: Solo puede crear clientes rápidos (para asociar a pedidos)
 */
@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", clienteService.findAll());
        return "clientes/listar";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/form";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Cliente cliente, 
                         BindingResult result, 
                         Model model,
                         RedirectAttributes flash) {
        if (result.hasErrors()) {
            return "clientes/form";
        }
        
        try {
            // Verificar si el DNI ya existe (solo para nuevos clientes o si cambió el DNI)
            if (cliente.getIdCliente() == null || 
                !clienteService.findById(cliente.getIdCliente())
                    .map(c -> c.getDni().equals(cliente.getDni()))
                    .orElse(false)) {
                
                if (clienteService.findByDni(cliente.getDni()).isPresent()) {
                    model.addAttribute("error", "Ya existe un cliente con el DNI: " + cliente.getDni());
                    model.addAttribute("cliente", cliente);
                    return "clientes/form";
                }
            }
            
            clienteService.save(cliente);
            flash.addFlashAttribute("success", "Cliente guardado exitosamente");
            return "redirect:/clientes";
            
        } catch (Exception e) {
            model.addAttribute("error", "Error al guardar el cliente: " + e.getMessage());
            model.addAttribute("cliente", cliente);
            return "clientes/form";
        }
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes flash) {
        Cliente cliente = clienteService.findById(id).orElse(null);
        if (cliente == null) {
            flash.addFlashAttribute("error", "Cliente no encontrado");
            return "redirect:/clientes";
        }
        model.addAttribute("cliente", cliente);
        return "clientes/form";
    }

    @GetMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        clienteService.deleteById(id);
        flash.addFlashAttribute("warning", "Cliente eliminado");
        return "redirect:/clientes";
    }
}
