package com.salas.edu.restaurante_gourmet_12.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuración de seguridad de Spring Security
 * Define roles, permisos y rutas protegidas
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authenticationProvider(authenticationProvider())
            .authorizeHttpRequests(auth -> auth
                // Recursos públicos
                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                .requestMatchers("/login", "/error/**", "/access-denied").permitAll()
                
                // ===== RUTAS ADMIN EXCLUSIVAS =====
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/inventario/**").hasRole("ADMIN")
                .requestMatchers("/bitacora/**").hasRole("ADMIN")
                .requestMatchers("/proveedores/**", "/compras/**").hasRole("ADMIN")
                
                // ===== RUTAS PARA MOZO =====
                // Mesas: MOZO y ADMIN (mozo solo ver/asignar)
                .requestMatchers("/mesas/**").hasAnyRole("MOZO", "ADMIN")
                // Clientes: MOZO (registrar rápido) y ADMIN
                .requestMatchers("/clientes/**").hasAnyRole("MOZO", "ADMIN")
                // Pedidos: MOZO, COCINERO y ADMIN
                .requestMatchers("/pedidos/**").hasAnyRole("MOZO", "COCINERO", "ADMIN")
                
                // ===== RUTAS PARA CAJERO =====
                // Ventas y facturas: CAJERO y ADMIN
                .requestMatchers("/ventas/**", "/facturas/**").hasAnyRole("CAJERO", "ADMIN")
                
                // ===== RUTAS PARA COCINERO =====
                // Cocina (vista especial de pedidos)
                .requestMatchers("/cocina/**").hasAnyRole("COCINERO", "ADMIN")
                
                // ===== PLATOS - SOLO ADMIN PUEDE CREAR/EDITAR =====
                .requestMatchers("/platos/**").hasRole("ADMIN")
                
                // Dashboard accesible para todos los autenticados
                .requestMatchers("/", "/dashboard", "/home").authenticated()
                
                // Cualquier otra petición requiere autenticación
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/dashboard", false) // Permitir redirección basada en roles
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .exceptionHandling(ex -> ex
                .accessDeniedPage("/access-denied")
            )
            .csrf(csrf -> csrf.disable()); // Deshabilitado para simplificar, en producción habilitarlo

        return http.build();
    }
}
