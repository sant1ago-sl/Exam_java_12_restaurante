-- ============================================
-- Base de Datos: sabor_gourmet
-- Sistema de Gestión de Restaurante
-- ============================================

CREATE DATABASE IF NOT EXISTS sabor_gourmet;
USE sabor_gourmet;

-- Tabla: Usuarios
CREATE TABLE IF NOT EXISTS usuarios (
    id_usuario BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_usuario VARCHAR(255) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    rol VARCHAR(50) NOT NULL,
    estado TINYINT(1) NOT NULL DEFAULT 1,
    nombre VARCHAR(255),
    apellido VARCHAR(255),
    email VARCHAR(255),
    telefono VARCHAR(20)
);

-- Tabla: Clientes
CREATE TABLE IF NOT EXISTS clientes (
    id_cliente BIGINT AUTO_INCREMENT PRIMARY KEY,
    dni VARCHAR(8) NOT NULL UNIQUE,
    nombres VARCHAR(255) NOT NULL,
    apellidos VARCHAR(255) NOT NULL,
    telefono VARCHAR(20),
    correo VARCHAR(255),
    estado TINYINT(1) NOT NULL DEFAULT 1
);

-- Tabla: Mesas
CREATE TABLE IF NOT EXISTS mesas (
    id_mesa BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero INT NOT NULL UNIQUE,
    capacidad INT NOT NULL,
    estado VARCHAR(50) NOT NULL DEFAULT 'DISPONIBLE'
);

-- Tabla: Insumos
CREATE TABLE IF NOT EXISTS insumos (
    id_insumo BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    unidad_medida VARCHAR(50) NOT NULL,
    stock DOUBLE NOT NULL DEFAULT 0.0,
    stock_minimo DOUBLE NOT NULL,
    precio_compra DECIMAL(10,2) NOT NULL,
    estado TINYINT(1) NOT NULL DEFAULT 1
);

-- Tabla: Platos
CREATE TABLE IF NOT EXISTS platos (
    id_plato BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    descripcion VARCHAR(500),
    estado TINYINT(1) NOT NULL DEFAULT 1
);

-- Tabla: Plato_Insumos (Relación Platos-Insumos)
CREATE TABLE IF NOT EXISTS plato_insumos (
    id_plato_insumo BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_plato BIGINT NOT NULL,
    id_insumo BIGINT NOT NULL,
    cantidad_usada DOUBLE NOT NULL,
    FOREIGN KEY (id_plato) REFERENCES platos(id_plato),
    FOREIGN KEY (id_insumo) REFERENCES insumos(id_insumo)
);

-- Tabla: Pedidos
CREATE TABLE IF NOT EXISTS pedidos (
    id_pedido BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_cliente BIGINT,
    id_mesa BIGINT NOT NULL,
    fecha_hora DATETIME NOT NULL,
    estado VARCHAR(50) NOT NULL DEFAULT 'PENDIENTE',
    FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente),
    FOREIGN KEY (id_mesa) REFERENCES mesas(id_mesa)
);

-- Tabla: Detalle_Pedidos
CREATE TABLE IF NOT EXISTS detalle_pedidos (
    id_detalle_pedido BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_pedido BIGINT NOT NULL,
    id_plato BIGINT NOT NULL,
    cantidad INT NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_pedido) REFERENCES pedidos(id_pedido),
    FOREIGN KEY (id_plato) REFERENCES platos(id_plato)
);

-- Tabla: Facturas
CREATE TABLE IF NOT EXISTS facturas (
    id_factura BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_pedido BIGINT NOT NULL,
    fecha_emision DATETIME NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    metodo_pago VARCHAR(50) NOT NULL,
    estado VARCHAR(50) NOT NULL DEFAULT 'PENDIENTE',
    FOREIGN KEY (id_pedido) REFERENCES pedidos(id_pedido)
);

-- Tabla: Detalle_Facturas
CREATE TABLE IF NOT EXISTS detalle_facturas (
    id_detalle_factura BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_factura BIGINT NOT NULL,
    concepto VARCHAR(255) NOT NULL,
    monto DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_factura) REFERENCES facturas(id_factura)
);

-- Tabla: Proveedores
CREATE TABLE IF NOT EXISTS proveedores (
    id_proveedor BIGINT AUTO_INCREMENT PRIMARY KEY,
    ruc VARCHAR(11) NOT NULL UNIQUE,
    nombre VARCHAR(255) NOT NULL,
    telefono VARCHAR(20),
    correo VARCHAR(255),
    direccion VARCHAR(500),
    estado TINYINT(1) NOT NULL DEFAULT 1
);

-- Tabla: Compras
CREATE TABLE IF NOT EXISTS compras (
    id_compra BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_proveedor BIGINT NOT NULL,
    fecha_compra DATETIME NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_proveedor) REFERENCES proveedores(id_proveedor)
);

-- Tabla: Detalle_Compras
CREATE TABLE IF NOT EXISTS detalle_compras (
    id_detalle_compra BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_compra BIGINT NOT NULL,
    id_insumo BIGINT NOT NULL,
    cantidad DOUBLE NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_compra) REFERENCES compras(id_compra),
    FOREIGN KEY (id_insumo) REFERENCES insumos(id_insumo)
);

-- Tabla: Bitacora
CREATE TABLE IF NOT EXISTS bitacora (
    id_bitacora BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_usuario BIGINT,
    accion VARCHAR(1000) NOT NULL,
    fecha_hora DATETIME NOT NULL,
    tabla VARCHAR(100),
    operacion VARCHAR(50),
    detalles TEXT,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);
