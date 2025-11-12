package com.salas.edu.restaurante_gourmet_12.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

/**
 * Aspecto de Logging - Registra información de ejecución de métodos
 */
@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Registra el inicio de métodos de servicio
     */
    @Before("execution(* com.salas.edu.restaurante_gourmet_12.service.*.*(..))")
    public void logAntesDeLlamarMetodo(JoinPoint joinPoint) {
        String nombreMetodo = joinPoint.getSignature().toShortString();
        String argumentos = Arrays.toString(joinPoint.getArgs());
        logger.info("→ Iniciando método: {} con argumentos: {}", nombreMetodo, argumentos);
    }

    /**
     * Registra el resultado exitoso de métodos de servicio
     */
    @AfterReturning(pointcut = "execution(* com.salas.edu.restaurante_gourmet_12.service.*.*(..))", 
                    returning = "result")
    public void logDespuesDeLlamarMetodo(JoinPoint joinPoint, Object result) {
        String nombreMetodo = joinPoint.getSignature().toShortString();
        logger.info("✓ Método completado exitosamente: {} - Resultado: {}", 
                    nombreMetodo, 
                    result != null ? result.getClass().getSimpleName() : "void");
    }

    /**
     * Registra excepciones lanzadas en métodos de servicio
     */
    @AfterThrowing(pointcut = "execution(* com.salas.edu.restaurante_gourmet_12.service.*.*(..))", 
                   throwing = "exception")
    public void logExcepcion(JoinPoint joinPoint, Throwable exception) {
        String nombreMetodo = joinPoint.getSignature().toShortString();
        logger.error("✗ Excepción en método: {} - Error: {}", nombreMetodo, exception.getMessage());
    }

    /**
     * Mide el tiempo de ejecución de métodos de servicio
     */
    @Around("execution(* com.salas.edu.restaurante_gourmet_12.service.*.*(..))")
    public Object medirTiempoEjecucion(ProceedingJoinPoint joinPoint) throws Throwable {
        Instant inicio = Instant.now();
        
        try {
            Object resultado = joinPoint.proceed();
            
            Instant fin = Instant.now();
            Duration duracion = Duration.between(inicio, fin);
            long milisegundos = duracion.toMillis();
            
            String nombreMetodo = joinPoint.getSignature().toShortString();
            
            if (milisegundos > 1000) {
                logger.warn("⏱ Método lento: {} - Tiempo de ejecución: {} ms", nombreMetodo, milisegundos);
            } else {
                logger.debug("⏱ Tiempo de ejecución: {} - {} ms", nombreMetodo, milisegundos);
            }
            
            return resultado;
            
        } catch (Throwable ex) {
            Instant fin = Instant.now();
            Duration duracion = Duration.between(inicio, fin);
            logger.error("⏱ Método falló después de {} ms", duracion.toMillis());
            throw ex;
        }
    }

    /**
     * Registra accesos a controladores
     */
    @Before("execution(* com.salas.edu.restaurante_gourmet_12.controller.*.*(..))")
    public void logAccesoControlador(JoinPoint joinPoint) {
        String nombreControlador = joinPoint.getSignature().getDeclaringTypeName();
        String nombreMetodo = joinPoint.getSignature().getName();
        logger.info("🌐 Acceso a controlador: {}.{}", nombreControlador, nombreMetodo);
    }
}
