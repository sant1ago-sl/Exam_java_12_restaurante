-- ============================================
-- Datos de Prueba para Sabor Gourmet
-- ============================================

USE sabor_gourmet;

-- Usuarios (contraseñas encriptadas con BCrypt)
-- Todas las contraseñas son: admin123, mozo123, cajero123, cocinero123
INSERT INTO usuarios (nombre_usuario, contrasena, rol, estado, nombre, apellido, email, telefono) VALUES
('admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ADMIN', 1, 'Carlos', 'Administrador', 'admin@saborgourmet.com', '987654321'),
('mozo', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'MOZO', 1, 'Juan', 'Pérez', 'mozo@saborgourmet.com', '987654322'),
('cajero', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'CAJERO', 1, 'María', 'García', 'cajero@saborgourmet.com', '987654323'),
('cocinero', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'COCINERO', 1, 'Pedro', 'López', 'cocinero@saborgourmet.com', '987654324');

-- Clientes
INSERT INTO clientes (dni, nombres, apellidos, telefono, correo, estado) VALUES
('12345678', 'Ana', 'Martínez', '987111222', 'ana.martinez@gmail.com', 1),
('23456789', 'Luis', 'Ramírez', '987222333', 'luis.ramirez@gmail.com', 1),
('34567890', 'Carmen', 'Torres', '987333444', 'carmen.torres@gmail.com', 1),
('45678901', 'Roberto', 'Sánchez', '987444555', 'roberto.sanchez@gmail.com', 1),
('56789012', 'Patricia', 'Flores', '987555666', 'patricia.flores@gmail.com', 1);

-- Mesas
INSERT INTO mesas (numero, capacidad, estado) VALUES
(1, 4, 'DISPONIBLE'),
(2, 2, 'DISPONIBLE'),
(3, 6, 'DISPONIBLE'),
(4, 4, 'DISPONIBLE'),
(5, 2, 'DISPONIBLE'),
(6, 8, 'DISPONIBLE'),
(7, 4, 'DISPONIBLE'),
(8, 2, 'DISPONIBLE');

-- Insumos
INSERT INTO insumos (nombre, unidad_medida, stock, stock_minimo, precio_compra, estado) VALUES
('Arroz', 'KG', 50.0, 10.0, 3.50, 1),
('Pollo', 'KG', 30.0, 8.0, 12.00, 1),
('Carne de Res', 'KG', 25.0, 5.0, 18.00, 1),
('Pescado', 'KG', 15.0, 5.0, 15.00, 1),
('Tomate', 'KG', 20.0, 5.0, 2.50, 1),
('Cebolla', 'KG', 15.0, 5.0, 2.00, 1),
('Papa', 'KG', 40.0, 10.0, 1.80, 1),
('Aceite', 'LITRO', 10.0, 3.0, 8.50, 1),
('Sal', 'KG', 5.0, 1.0, 1.20, 1),
('Azúcar', 'KG', 8.0, 2.0, 3.00, 1),
('Leche', 'LITRO', 12.0, 4.0, 4.50, 1),
('Huevos', 'UNIDAD', 60.0, 20.0, 0.50, 1);

-- Platos
INSERT INTO platos (nombre, tipo, precio, descripcion, estado) VALUES
('Ceviche de Pescado', 'ENTRADA', 25.00, 'Pescado fresco marinado en limón con cebolla, ají y culantro', 1),
('Causa Limeña', 'ENTRADA', 18.00, 'Papa amarilla con relleno de pollo y mayonesa', 1),
('Ensalada Caesar', 'ENTRADA', 15.00, 'Lechuga romana, crutones, queso parmesano y aderezo Caesar', 1),
('Arroz con Pollo', 'FONDO', 28.00, 'Arroz con pollo deshilachado, verduras y papa a la huancaína', 1),
('Lomo Saltado', 'FONDO', 32.00, 'Carne salteada con cebolla, tomate y papas fritas', 1),
('Ají de Gallina', 'FONDO', 26.00, 'Pollo deshilachado en crema de ají amarillo con arroz', 1),
('Tallarines a la Huancaína', 'FONDO', 24.00, 'Tallarines bañados en salsa huancaína con bistec', 1),
('Suspiro Limeño', 'POSTRE', 12.00, 'Dulce de leche con merengue de vino oporto', 1),
('Mazamorra Morada', 'POSTRE', 8.00, 'Postre tradicional peruano a base de maíz morado', 1),
('Chicha Morada', 'BEBIDA', 5.00, 'Refresco de maíz morado con frutas', 1),
('Inca Kola', 'BEBIDA', 4.00, 'Gaseosa nacional', 1),
('Limonada', 'BEBIDA', 5.00, 'Limonada natural', 1);

-- Proveedores
INSERT INTO proveedores (ruc, nombre, telefono, correo, direccion, estado) VALUES
('20123456789', 'Distribuidora La Granja SAC', '987777888', 'ventas@lagranja.com', 'Av. Los Alimentos 123, Lima', 1),
('20234567890', 'Pescados Frescos del Mar', '987888999', 'info@pescadosdelmar.com', 'Av. Pesquera 456, Callao', 1),
('20345678901', 'Abarrotes Mayoristas', '987999000', 'ventas@abarrotes.com', 'Jr. Comercio 789, Lima', 1);
