package com.salas.edu.restaurante_gourmet_12.controller;

import com.salas.edu.restaurante_gourmet_12.model.Pedido;
import com.salas.edu.restaurante_gourmet_12.service.ClienteService;
import com.salas.edu.restaurante_gourmet_12.service.MesaService;
import com.salas.edu.restaurante_gourmet_12.service.PedidoService;
import com.salas.edu.restaurante_gourmet_12.service.PlatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador de Pedidos
 * - ADMIN: Acceso completo
 * - MOZO: Puede crear y gestionar pedidos
 * - COCINERO: Solo puede ver pedidos y cambiar estados de preparación
 */
@Controller
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private MesaService mesaService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private PlatoService platoService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pedidos", pedidoService.findAll());
        return "pedidos/listar";
    }

    @GetMapping("/nuevo")
    @PreAuthorize("hasAnyRole('MOZO', 'ADMIN')")
    public String nuevo(Model model) {
        model.addAttribute("pedido", new Pedido());
        model.addAttribute("mesas", mesaService.findMesasDisponibles());
        model.addAttribute("clientes", clienteService.findAll());
        model.addAttribute("platos", platoService.findPlatosActivos());
        return "pedidos/form";
    }

    @PostMapping("/guardar")
    @PreAuthorize("hasAnyRole('MOZO', 'ADMIN')")
    public String guardar(@ModelAttribute Pedido pedido, Model model, RedirectAttributes flash) {
        try {
            // Validar que se seleccionó una mesa
            if (pedido.getMesa() == null || pedido.getMesa().getId() == null) {
                flash.addFlashAttribute("error", "Debe seleccionar una mesa");
                return "redirect:/pedidos/nuevo";
            }
            
            // Validar que se seleccionó un cliente
            if (pedido.getCliente() == null || pedido.getCliente().getId() == null) {
                flash.addFlashAttribute("error", "Debe seleccionar un cliente");
                return "redirect:/pedidos/nuevo";
            }
            
            // Validar que hay platos
            if (pedido.getDetalles() == null || pedido.getDetalles().isEmpty()) {
                flash.addFlashAttribute("error", "Debe agregar al menos un plato al pedido");
                return "redirect:/pedidos/nuevo";
            }
            
            pedidoService.save(pedido);
            flash.addFlashAttribute("success", "Pedido creado exitosamente");
            return "redirect:/pedidos";
            
        } catch (IllegalArgumentException e) {
            // Errores de validación del servicio
            flash.addFlashAttribute("error", "Error de validación: " + e.getMessage());
            return "redirect:/pedidos/nuevo";
        } catch (Exception e) {
            // Otros errores
            flash.addFlashAttribute("error", "Error al crear pedido: " + e.getMessage());
            e.printStackTrace();
            return "redirect:/pedidos/nuevo";
        }
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Long id, Model model, RedirectAttributes flash) {
        Pedido pedido = pedidoService.findById(id).orElse(null);
        if (pedido == null) {
            flash.addFlashAttribute("error", "Pedido no encontrado");
            return "redirect:/pedidos";
        }
        model.addAttribute("pedido", pedido);
        return "pedidos/ver";
    }

    @PostMapping("/cambiar-estado/{id}")
    @PreAuthorize("hasAnyRole('MOZO', 'COCINERO')")
    public String cambiarEstado(@PathVariable Long id, 
                               @RequestParam String estado, 
                               RedirectAttributes flash) {
        pedidoService.cambiarEstado(id, estado);
        flash.addFlashAttribute("success", "Estado del pedido actualizado");
        return "redirect:/pedidos";
    }

    @GetMapping("/cocina")
    @PreAuthorize("hasAnyRole('COCINERO', 'ADMIN')")
    public String cocina(Model model) {
        model.addAttribute("pedidosPendientes", pedidoService.findByEstado("PENDIENTE"));
        model.addAttribute("pedidosEnPreparacion", pedidoService.findByEstado("EN_PREPARACION"));
        return "pedidos/cocina";
    }
}
