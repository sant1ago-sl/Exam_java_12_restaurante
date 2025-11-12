package com.salas.edu.restaurante_gourmet_12.service;

import com.salas.edu.restaurante_gourmet_12.model.DetallePedido;
import com.salas.edu.restaurante_gourmet_12.model.Mesa;
import com.salas.edu.restaurante_gourmet_12.model.Pedido;
import com.salas.edu.restaurante_gourmet_12.model.Plato;
import com.salas.edu.restaurante_gourmet_12.repository.MesaRepository;
import com.salas.edu.restaurante_gourmet_12.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private MesaRepository mesaRepository;
    
    @Autowired
    private PlatoService platoService;

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> findById(Long id) {
        return pedidoRepository.findById(id);
    }

    // Método para preparar el pedido sin transacción
    private void prepararPedido(Pedido pedido) {
        // Establecer fecha si no existe
        if (pedido.getFecha() == null) {
            pedido.setFecha(LocalDateTime.now());
        }
        if (pedido.getFechaHora() == null) {
            pedido.setFechaHora(LocalDateTime.now());
        }
        
        // Validar que hay detalles
        if (pedido.getDetalles() == null || pedido.getDetalles().isEmpty()) {
            throw new IllegalArgumentException("El pedido debe tener al menos un plato");
        }
        
        // Calcular subtotales de los detalles
        for (DetallePedido detalle : pedido.getDetalles()) {
            if (detalle.getPlato() == null || detalle.getPlato().getId() == null) {
                throw new IllegalArgumentException("Cada detalle debe tener un plato válido");
            }
            
            // Cargar el plato completo desde la base de datos para obtener el precio
            Plato plato = platoService.findById(detalle.getPlato().getId())
                .orElseThrow(() -> new IllegalArgumentException("Plato no encontrado: " + detalle.getPlato().getId()));
            
            detalle.setPlato(plato);
            BigDecimal precio = plato.getPrecio();
            
            if (precio == null) {
                throw new IllegalArgumentException("El plato '" + plato.getNombre() + "' no tiene precio configurado");
            }
            
            detalle.setPrecio(precio);
            BigDecimal cantidad = BigDecimal.valueOf(detalle.getCantidad());
            detalle.setSubtotal(precio.multiply(cantidad));
            detalle.setPedido(pedido);
        }
        
        // Validar mesa
        if (pedido.getMesa() == null || pedido.getMesa().getId() == null) {
            throw new IllegalArgumentException("El pedido debe tener una mesa asignada");
        }
    }
    
    public Pedido save(Pedido pedido) {
        // Preparar y validar ANTES de la transacción
        prepararPedido(pedido);
        
        // Ahora sí, guardar en la transacción
        return guardarPedido(pedido);
    }
    
    @Transactional
    private Pedido guardarPedido(Pedido pedido) {
        // Cambiar estado de la mesa a OCUPADA
        Mesa mesa = mesaRepository.findById(pedido.getMesa().getId())
            .orElseThrow(() -> new IllegalArgumentException("Mesa no encontrada"));
        mesa.setEstado("OCUPADA");
        mesaRepository.save(mesa);
        
        // Guardar el pedido
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedido update(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public void deleteById(Long id) {
        pedidoRepository.deleteById(id);
    }

    public List<Pedido> findByEstado(String estado) {
        return pedidoRepository.findByEstado(estado);
    }

    @Transactional
    public void cambiarEstado(Long id, String nuevoEstado) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        pedido.setEstado(nuevoEstado);
        
        // Si el pedido se cierra, liberar la mesa
        if ("CERRADO".equals(nuevoEstado) && pedido.getMesa() != null) {
            Mesa mesa = pedido.getMesa();
            mesa.setEstado("DISPONIBLE");
            mesaRepository.save(mesa);
        }
        
        pedidoRepository.save(pedido);
    }

    public List<Pedido> findPedidosHoy() {
        LocalDateTime inicioHoy = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime finHoy = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
        return pedidoRepository.findByFechaHoraBetween(inicioHoy, finHoy);
    }

    public BigDecimal calcularTotalPedido(Pedido pedido) {
        return pedido.getDetalles().stream()
                .map(DetallePedido::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
