package com.salas.edu.restaurante_gourmet_12.config;

import com.salas.edu.restaurante_gourmet_12.model.*;
import com.salas.edu.restaurante_gourmet_12.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private MesaRepository mesaRepository;

    @Autowired
    private InsumoRepository insumoRepository;

    @Autowired
    private PlatoRepository platoRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Solo inicializar si no hay datos
        if (usuarioRepository.count() == 0) {
            initializeData();
            System.out.println("✅ Datos iniciales cargados exitosamente");
        } else {
            System.out.println("ℹ️  Los datos ya existen, omitiendo inicialización");
        }
    }

    private void initializeData() {
        // Usuarios
        Usuario admin = new Usuario();
        admin.setNombreUsuario("admin");
        admin.setContrasena(passwordEncoder.encode("admin123"));
        admin.setRol("ADMIN");
        admin.setNombre("Carlos");
        admin.setApellido("Administrador");
        admin.setEmail("admin@saborgourmet.com");
        admin.setTelefono("987654321");
        admin.setEstado(true);
        usuarioRepository.save(admin);

        Usuario mozo = new Usuario();
        mozo.setNombreUsuario("mozo");
        mozo.setContrasena(passwordEncoder.encode("mozo123"));
        mozo.setRol("MOZO");
        mozo.setNombre("Juan");
        mozo.setApellido("Pérez");
        mozo.setEmail("mozo@saborgourmet.com");
        mozo.setTelefono("987654322");
        mozo.setEstado(true);
        usuarioRepository.save(mozo);

        Usuario cajero = new Usuario();
        cajero.setNombreUsuario("cajero");
        cajero.setContrasena(passwordEncoder.encode("cajero123"));
        cajero.setRol("CAJERO");
        cajero.setNombre("María");
        cajero.setApellido("García");
        cajero.setEmail("cajero@saborgourmet.com");
        cajero.setTelefono("987654323");
        cajero.setEstado(true);
        usuarioRepository.save(cajero);

        Usuario cocinero = new Usuario();
        cocinero.setNombreUsuario("cocinero");
        cocinero.setContrasena(passwordEncoder.encode("cocinero123"));
        cocinero.setRol("COCINERO");
        cocinero.setNombre("Pedro");
        cocinero.setApellido("López");
        cocinero.setEmail("cocinero@saborgourmet.com");
        cocinero.setTelefono("987654324");
        cocinero.setEstado(true);
        usuarioRepository.save(cocinero);

        // Clientes
        Cliente cliente1 = new Cliente();
        cliente1.setDni("12345678");
        cliente1.setNombres("Ana");
        cliente1.setApellidos("Martínez");
        cliente1.setTelefono("987111222");
        cliente1.setCorreo("ana.martinez@gmail.com");
        cliente1.setEstado(true);
        clienteRepository.save(cliente1);

        Cliente cliente2 = new Cliente();
        cliente2.setDni("23456789");
        cliente2.setNombres("Luis");
        cliente2.setApellidos("Ramírez");
        cliente2.setTelefono("987222333");
        cliente2.setCorreo("luis.ramirez@gmail.com");
        cliente2.setEstado(true);
        clienteRepository.save(cliente2);

        Cliente cliente3 = new Cliente();
        cliente3.setDni("34567890");
        cliente3.setNombres("Carmen");
        cliente3.setApellidos("Torres");
        cliente3.setTelefono("987333444");
        cliente3.setCorreo("carmen.torres@gmail.com");
        cliente3.setEstado(true);
        clienteRepository.save(cliente3);

        // Mesas
        for (int i = 1; i <= 8; i++) {
            Mesa mesa = new Mesa();
            mesa.setNumero(i);
            mesa.setCapacidad(i % 2 == 0 ? 4 : 2);
            mesa.setEstado("DISPONIBLE");
            mesaRepository.save(mesa);
        }

        // Insumos
        Insumo arroz = new Insumo();
        arroz.setNombre("Arroz");
        arroz.setUnidadMedida("KG");
        arroz.setStock(50.0);
        arroz.setStockMinimo(10.0);
        arroz.setPrecioCompra(new BigDecimal("3.50"));
        arroz.setEstado(true);
        insumoRepository.save(arroz);

        Insumo pollo = new Insumo();
        pollo.setNombre("Pollo");
        pollo.setUnidadMedida("KG");
        pollo.setStock(30.0);
        pollo.setStockMinimo(8.0);
        pollo.setPrecioCompra(new BigDecimal("12.00"));
        pollo.setEstado(true);
        insumoRepository.save(pollo);

        Insumo carne = new Insumo();
        carne.setNombre("Carne de Res");
        carne.setUnidadMedida("KG");
        carne.setStock(25.0);
        carne.setStockMinimo(5.0);
        carne.setPrecioCompra(new BigDecimal("18.00"));
        carne.setEstado(true);
        insumoRepository.save(carne);

        Insumo pescado = new Insumo();
        pescado.setNombre("Pescado");
        pescado.setUnidadMedida("KG");
        pescado.setStock(15.0);
        pescado.setStockMinimo(5.0);
        pescado.setPrecioCompra(new BigDecimal("15.00"));
        pescado.setEstado(true);
        insumoRepository.save(pescado);

        // Platos
        Plato ceviche = new Plato();
        ceviche.setNombre("Ceviche de Pescado");
        ceviche.setTipo("ENTRADA");
        ceviche.setPrecio(new BigDecimal("25.00"));
        ceviche.setDescripcion("Pescado fresco marinado en limón con cebolla, ají y culantro");
        ceviche.setEstado(true);
        platoRepository.save(ceviche);

        Plato causa = new Plato();
        causa.setNombre("Causa Limeña");
        causa.setTipo("ENTRADA");
        causa.setPrecio(new BigDecimal("18.00"));
        causa.setDescripcion("Papa amarilla con relleno de pollo y mayonesa");
        causa.setEstado(true);
        platoRepository.save(causa);

        Plato arrozConPollo = new Plato();
        arrozConPollo.setNombre("Arroz con Pollo");
        arrozConPollo.setTipo("FONDO");
        arrozConPollo.setPrecio(new BigDecimal("28.00"));
        arrozConPollo.setDescripcion("Arroz con pollo deshilachado, verduras y papa a la huancaína");
        arrozConPollo.setEstado(true);
        platoRepository.save(arrozConPollo);

        Plato lomoSaltado = new Plato();
        lomoSaltado.setNombre("Lomo Saltado");
        lomoSaltado.setTipo("FONDO");
        lomoSaltado.setPrecio(new BigDecimal("32.00"));
        lomoSaltado.setDescripcion("Carne salteada con cebolla, tomate y papas fritas");
        lomoSaltado.setEstado(true);
        platoRepository.save(lomoSaltado);

        Plato ajiDeGallina = new Plato();
        ajiDeGallina.setNombre("Ají de Gallina");
        ajiDeGallina.setTipo("FONDO");
        ajiDeGallina.setPrecio(new BigDecimal("26.00"));
        ajiDeGallina.setDescripcion("Pollo deshilachado en crema de ají amarillo con arroz");
        ajiDeGallina.setEstado(true);
        platoRepository.save(ajiDeGallina);

        Plato suspiro = new Plato();
        suspiro.setNombre("Suspiro Limeño");
        suspiro.setTipo("POSTRE");
        suspiro.setPrecio(new BigDecimal("12.00"));
        suspiro.setDescripcion("Dulce de leche con merengue de vino oporto");
        suspiro.setEstado(true);
        platoRepository.save(suspiro);

        Plato chicha = new Plato();
        chicha.setNombre("Chicha Morada");
        chicha.setTipo("BEBIDA");
        chicha.setPrecio(new BigDecimal("5.00"));
        chicha.setDescripcion("Refresco de maíz morado con frutas");
        chicha.setEstado(true);
        platoRepository.save(chicha);

        Plato incaKola = new Plato();
        incaKola.setNombre("Inca Kola");
        incaKola.setTipo("BEBIDA");
        incaKola.setPrecio(new BigDecimal("4.00"));
        incaKola.setDescripcion("Gaseosa nacional");
        incaKola.setEstado(true);
        platoRepository.save(incaKola);

        // Proveedores
        Proveedor prov1 = new Proveedor();
        prov1.setRuc("20123456789");
        prov1.setNombre("Distribuidora La Granja SAC");
        prov1.setTelefono("987777888");
        prov1.setCorreo("ventas@lagranja.com");
        prov1.setDireccion("Av. Los Alimentos 123, Lima");
        prov1.setEstado(true);
        proveedorRepository.save(prov1);

        Proveedor prov2 = new Proveedor();
        prov2.setRuc("20234567890");
        prov2.setNombre("Pescados Frescos del Mar");
        prov2.setTelefono("987888999");
        prov2.setCorreo("info@pescadosdelmar.com");
        prov2.setDireccion("Av. Pesquera 456, Callao");
        prov2.setEstado(true);
        proveedorRepository.save(prov2);
    }
}
