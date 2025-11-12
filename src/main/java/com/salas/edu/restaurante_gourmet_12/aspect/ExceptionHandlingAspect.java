package com.salas.edu.restaurante_gourmet_12.aspect;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Aspecto para manejo centralizado de excepciones
 */
@Aspect
@Component
public class ExceptionHandlingAspect {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlingAspect.class);

    @AfterThrowing(pointcut = "execution(* com.salas.edu.restaurante_gourmet_12..*(..))", throwing = "ex")
    public void manejarExcepcion(Exception ex) {
        logger.error("❌ Excepción capturada: {} - Mensaje: {}", 
                     ex.getClass().getSimpleName(), 
                     ex.getMessage());
        
        // Aquí podrías agregar lógica adicional como:
        // - Enviar notificaciones
        // - Registrar en sistema externo
        // - Crear tickets de soporte
    }
}
