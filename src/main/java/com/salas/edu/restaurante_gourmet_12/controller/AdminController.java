package com.salas.edu.restaurante_gourmet_12.controller;

import com.salas.edu.restaurante_gourmet_12.model.Usuario;
import com.salas.edu.restaurante_gourmet_12.service.BitacoraService;
import com.salas.edu.restaurante_gourmet_12.service.ProveedorService;
import com.salas.edu.restaurante_gourmet_12.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador de Administración - Solo accesible para ADMIN
 * Gestiona usuarios, bitácora y proveedores
 */
@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private BitacoraService bitacoraService;

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    public String index(Model model) {
        return "admin/index";
    }

    // Usuarios
    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.findAll());
        model.addAttribute("titulo", "Gestión de Usuarios");
        return "admin/usuarios/index";
    }

    @GetMapping("/usuarios/nuevo")
    public String nuevoUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("titulo", "Nuevo Usuario");
        model.addAttribute("roles", new String[]{"ADMIN", "MOZO", "COCINERO", "CAJERO"});
        return "admin/usuarios/form";
    }

    @PostMapping("/usuarios/guardar")
    public String guardarUsuario(@Valid @ModelAttribute Usuario usuario, 
                                BindingResult result, 
                                Model model,
                                RedirectAttributes flash) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", usuario.getIdUsuario() == null ? "Nuevo Usuario" : "Editar Usuario");
            model.addAttribute("roles", new String[]{"ADMIN", "MOZO", "COCINERO", "CAJERO"});
            return "admin/usuarios/form";
        }
        
        try {
            // Validar nombre de usuario único
            if (usuario.getIdUsuario() == null || 
                !usuarioService.findById(usuario.getIdUsuario())
                    .map(u -> u.getNombreUsuario().equals(usuario.getNombreUsuario()))
                    .orElse(false)) {
                
                if (usuarioService.existsByNombreUsuario(usuario.getNombreUsuario())) {
                    model.addAttribute("error", "Ya existe un usuario con ese nombre de usuario");
                    model.addAttribute("usuario", usuario);
                    model.addAttribute("titulo", usuario.getIdUsuario() == null ? "Nuevo Usuario" : "Editar Usuario");
                    model.addAttribute("roles", new String[]{"ADMIN", "MOZO", "COCINERO", "CAJERO"});
                    return "admin/usuarios/form";
                }
            }
            
            usuarioService.save(usuario);
            flash.addFlashAttribute("success", "Usuario guardado exitosamente");
            return "redirect:/admin/usuarios";
            
        } catch (Exception e) {
            model.addAttribute("error", "Error al guardar el usuario: " + e.getMessage());
            model.addAttribute("usuario", usuario);
            model.addAttribute("titulo", usuario.getIdUsuario() == null ? "Nuevo Usuario" : "Editar Usuario");
            model.addAttribute("roles", new String[]{"ADMIN", "MOZO", "COCINERO", "CAJERO"});
            return "admin/usuarios/form";
        }
    }

    @GetMapping("/usuarios/editar/{id}")
    public String editarUsuario(@PathVariable Long id, Model model, RedirectAttributes flash) {
        Usuario usuario = usuarioService.findById(id).orElse(null);
        if (usuario == null) {
            flash.addFlashAttribute("error", "Usuario no encontrado");
            return "redirect:/admin/usuarios";
        }
        model.addAttribute("usuario", usuario);
        model.addAttribute("titulo", "Editar Usuario");
        model.addAttribute("roles", new String[]{"ADMIN", "MOZO", "COCINERO", "CAJERO"});
        return "admin/usuarios/form";
    }

    @GetMapping("/usuarios/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id, RedirectAttributes flash) {
        usuarioService.deleteById(id);
        flash.addFlashAttribute("warning", "Usuario eliminado");
        return "redirect:/admin/usuarios";
    }

    // Bitácora
    @GetMapping("/bitacora")
    public String bitacora(Model model) {
        model.addAttribute("registros", bitacoraService.findRecientes());
        return "admin/bitacora/listar";
    }

    // Proveedores
    @GetMapping("/proveedores")
    public String listarProveedores(Model model) {
        model.addAttribute("proveedores", proveedorService.findAll());
        return "admin/proveedores/listar";
    }
}
