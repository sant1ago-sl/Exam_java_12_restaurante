package com.salas.edu.restaurante_gourmet_12.aspect;

import com.salas.edu.restaurante_gourmet_12.model.Bitacora;
import com.salas.edu.restaurante_gourmet_12.repository.BitacoraRepository;
import com.salas.edu.restaurante_gourmet_12.repository.UsuarioRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Aspecto de Auditoría - Registra todas las operaciones CRUD en la bitácora
 */
@Aspect
@Component
public class AuditoriaAspect {

    private static final Logger logger = LoggerFactory.getLogger(AuditoriaAspect.class);

    @Autowired
    private BitacoraRepository bitacoraRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Registra operaciones de creación (save/persist)
     */
    @AfterReturning(pointcut = "execution(* com.salas.edu.restaurante_gourmet_12.service.*.save*(..)) || " +
                               "execution(* com.salas.edu.restaurante_gourmet_12.service.*.create*(..))", 
                    returning = "result")
    public void auditarCreacion(JoinPoint joinPoint, Object result) {
        registrarAuditoria(joinPoint, "CREATE", result);
    }

    /**
     * Registra operaciones de actualización (update)
     */
    @AfterReturning(pointcut = "execution(* com.salas.edu.restaurante_gourmet_12.service.*.update*(..))", 
                    returning = "result")
    public void auditarActualizacion(JoinPoint joinPoint, Object result) {
        registrarAuditoria(joinPoint, "UPDATE", result);
    }

    /**
     * Registra operaciones de eliminación (delete)
     */
    @AfterReturning(pointcut = "execution(* com.salas.edu.restaurante_gourmet_12.service.*.delete*(..)) || " +
                               "execution(* com.salas.edu.restaurante_gourmet_12.service.*.remove*(..))", 
                    returning = "result")
    public void auditarEliminacion(JoinPoint joinPoint, Object result) {
        registrarAuditoria(joinPoint, "DELETE", result);
    }

    /**
     * Método auxiliar para registrar en la bitácora
     */
    private void registrarAuditoria(JoinPoint joinPoint, String operacion, Object result) {
        try {
            String nombreMetodo = joinPoint.getSignature().getName();
            String nombreClase = joinPoint.getTarget().getClass().getSimpleName();
            String nombreTabla = extraerNombreTabla(nombreClase);
            
            Object[] args = joinPoint.getArgs();
            String detalles = Arrays.toString(args);
            
            String accion = String.format("[%s] %s.%s", operacion, nombreClase, nombreMetodo);
            
            Bitacora bitacora = new Bitacora();
            bitacora.setAccion(accion);
            bitacora.setOperacion(operacion);
            bitacora.setTabla(nombreTabla);
            bitacora.setDetalles(detalles.length() > 500 ? detalles.substring(0, 500) : detalles);
            bitacora.setFechaHora(LocalDateTime.now());
            
            // Obtener usuario autenticado
            try {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                if (auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser")) {
                    String username = auth.getName();
                    usuarioRepository.findByNombreUsuario(username).ifPresent(bitacora::setUsuario);
                }
            } catch (Exception e) {
                logger.warn("No se pudo obtener el usuario autenticado: {}", e.getMessage());
            }
            
            bitacoraRepository.save(bitacora);
            logger.info("Auditoría registrada: {} en tabla {}", operacion, nombreTabla);
            
        } catch (Exception e) {
            logger.error("Error al registrar auditoría: {}", e.getMessage());
        }
    }

    /**
     * Extrae el nombre de la tabla del nombre del servicio
     */
    private String extraerNombreTabla(String nombreClase) {
        if (nombreClase.endsWith("Service")) {
            String tabla = nombreClase.replace("Service", "");
            return tabla.toLowerCase();
        }
        return nombreClase.toLowerCase();
    }
}
