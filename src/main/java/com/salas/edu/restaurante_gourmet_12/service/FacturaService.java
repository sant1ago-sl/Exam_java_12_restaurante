package com.salas.edu.restaurante_gourmet_12.service;

import com.salas.edu.restaurante_gourmet_12.model.DetalleFactura;
import com.salas.edu.restaurante_gourmet_12.model.Factura;
import com.salas.edu.restaurante_gourmet_12.model.Pedido;
import com.salas.edu.restaurante_gourmet_12.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private PedidoService pedidoService;

    public List<Factura> findAll() {
        return facturaRepository.findAll();
    }

    public Optional<Factura> findById(Long id) {
        return facturaRepository.findById(id);
    }

    public Factura save(Factura factura) {
        return facturaRepository.save(factura);
    }

    public Factura generarFacturaDesdePedido(Long idPedido, String metodoPago) {
        Pedido pedido = pedidoService.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        Factura factura = new Factura();
        factura.setNumero(generarNumeroFactura());
        factura.setPedido(pedido);
        factura.setMetodoPago(metodoPago);
        LocalDateTime ahora = LocalDateTime.now();
        factura.setFecha(ahora);
        factura.setFechaEmision(ahora);
        
        BigDecimal totalPedido = pedidoService.calcularTotalPedido(pedido);
        BigDecimal subtotal = totalPedido.divide(new BigDecimal("1.18"), 2, BigDecimal.ROUND_HALF_UP);
        BigDecimal igv = totalPedido.subtract(subtotal);
        
        factura.setSubtotal(subtotal);
        factura.setIgv(igv);
        factura.setTotal(totalPedido);
        
        // Crear detalle de factura por cada ítem del pedido
        pedido.getDetalles().forEach(dp -> {
            DetalleFactura detalle = new DetalleFactura();
            detalle.setFactura(factura);
            detalle.setConcepto(dp.getPlato().getNombre() + " x" + dp.getCantidad());
            detalle.setMonto(dp.getSubtotal());
            factura.getDetalles().add(detalle);
        });
        
        factura.setEstado("PENDIENTE");
        
        // Cambiar estado del pedido a CERRADO
        pedidoService.cambiarEstado(idPedido, "CERRADO");
        
        return facturaRepository.save(factura);
    }

    public void marcarComoPagada(Long id) {
        Factura factura = facturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada"));
        factura.setEstado("PAGADO");
        facturaRepository.save(factura);
    }

    public List<Factura> findFacturasHoy() {
        LocalDateTime inicioHoy = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime finHoy = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
        return facturaRepository.findByFechaEmisionBetween(inicioHoy, finHoy);
    }

    public BigDecimal calcularTotalVentasHoy() {
        return findFacturasHoy().stream()
                .filter(f -> "PAGADO".equals(f.getEstado()))
                .map(Factura::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    private String generarNumeroFactura() {
        long count = facturaRepository.count() + 1;
        return String.format("F%03d-%d", count, LocalDateTime.now().getYear());
    }
}
