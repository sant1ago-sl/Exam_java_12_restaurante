package com.salas.edu.restaurante_gourmet_12.repository;

import com.salas.edu.restaurante_gourmet_12.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByEstado(String estado);
    List<Pedido> findByMesaIdMesa(Long idMesa);
    List<Pedido> findByClienteIdCliente(Long idCliente);
    
    @Query("SELECT p FROM Pedido p WHERE p.fechaHora BETWEEN ?1 AND ?2")
    List<Pedido> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin);
}
