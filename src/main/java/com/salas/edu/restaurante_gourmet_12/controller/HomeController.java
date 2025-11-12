package com.salas.edu.restaurante_gourmet_12.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping({"/", "/dashboard", "/home"})
    public String dashboard(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            // Redirigir según el rol del usuario
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                String role = authority.getAuthority();
                
                if (role.equals("ROLE_MOZO")) {
                    return "redirect:/mesas";
                } else if (role.equals("ROLE_COCINERO")) {
                    return "redirect:/cocina";
                } else if (role.equals("ROLE_CAJERO")) {
                    return "redirect:/ventas";
                } else if (role.equals("ROLE_ADMIN")) {
                    return "redirect:/admin";
                }
            }
        }
        return "dashboard";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }
}
