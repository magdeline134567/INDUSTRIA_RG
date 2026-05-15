-- Script completo para crear todas las tablas necesarias para el sistema de compras
USE INDUSTRIA_RG;
GO

-- ==================== LIMPIEZA DE TABLAS (ORDEN INVERSO) ====================
IF EXISTS (SELECT * FROM sysobjects WHERE name='Compras' AND xtype='U')
BEGIN
    PRINT 'Eliminando tabla Compras existente...';
    DROP TABLE Compras;
END
GO

IF EXISTS (SELECT * FROM sysobjects WHERE name='Inventario' AND xtype='U')
BEGIN
    PRINT 'Eliminando tabla Inventario existente...';
    DROP TABLE Inventario;
END
GO

IF EXISTS (SELECT * FROM sysobjects WHERE name='Proveedor' AND xtype='U')
BEGIN
    PRINT 'Eliminando tabla Proveedor existente...';
    DROP TABLE Proveedor;
END
GO

-- ==================== TABLA PROVEEDOR ====================
CREATE TABLE Proveedor (
    id_proveedor INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    contacto VARCHAR(100),
    telefono VARCHAR(20),
    email VARCHAR(100),
    direccion VARCHAR(200),
    fecha_creacion DATETIME DEFAULT GETDATE()
);
GO

INSERT INTO Proveedor (nombre, contacto, telefono, email, direccion) VALUES
('Aluminios del Norte', 'Carlos Rodríguez', '555-1001', 'ventas@aluminiosnorte.com', 'Av. Industrial 123'),
('Vidrios y Cristales SA', 'María González', '555-1002', 'info@vidrioscristales.com', 'Calle Comercial 456'),
('Herrajes Pro', 'Juan Pérez', '555-1003', 'contacto@herrajespro.com', 'Zona Industrial 789'),
('Marcos y Perfiles', 'Ana López', '555-1004', 'ventas@marcosperfiles.com', 'Polígono Norte 321'),
('Selladores Industriales', 'Pedro Martínez', '555-1005', 'info@selladoresind.com', 'Parque Empresarial 654');
GO

-- ==================== TABLA INVENTARIO (MATERIAL) ====================
CREATE TABLE Inventario (
    id INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    cantidad INT NOT NULL DEFAULT 0,
    costo_unitario DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    precio_unitario DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    ubicacion VARCHAR(100),
    fecha_creacion DATETIME DEFAULT GETDATE(),
    fecha_actualizacion DATETIME DEFAULT GETDATE()
);
GO

INSERT INTO Inventario (nombre, categoria, cantidad, costo_unitario, precio_unitario, ubicacion) VALUES
('Perfil Aluminio 60mm', 'Perfiles', 150, 25.50, 35.75, 'Bodega A, Estante 1'),
('Vidrio Templado 6mm', 'Vidrios', 80, 45.00, 65.00, 'Bodega B, Estante 2'),
('Cerradura Estándar', 'Herrajes', 200, 12.30, 18.50, 'Bodega A, Estante 3'),
('Marco PVC Blanco', 'Marcos', 75, 38.75, 52.25, 'Bodega C, Estante 1'),
('Silicona Blanca', 'Selladores', 120, 8.50, 12.75, 'Bodega B, Estante 4'),
('Perfil Aluminio 90mm', 'Perfiles', 100, 32.00, 45.50, 'Bodega A, Estante 2'),
('Vidrio Float 4mm', 'Vidrios', 120, 28.75, 42.00, 'Bodega B, Estante 1'),
('Bisagra Acero Inoxidable', 'Herrajes', 300, 6.80, 10.25, 'Bodega A, Estante 4'),
('Marco Aluminio Dorado', 'Marcos', 60, 45.20, 62.00, 'Bodega C, Estante 2'),
('Silicona Transparente', 'Selladores', 90, 9.20, 13.80, 'Bodega B, Estante 3');
GO

-- ==================== TABLA COMPRAS ====================

CREATE TABLE Compras (
    id INT IDENTITY(1,1) PRIMARY KEY,
    codigo_oc VARCHAR(50) NOT NULL UNIQUE,
    proveedor_id INT NOT NULL,
    material_id INT NOT NULL,
    fecha_entrega DATE,
    cantidad DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    precio_unitario DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    total DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    estado VARCHAR(50) DEFAULT 'Pendiente',
    fecha_creacion DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (proveedor_id) REFERENCES Proveedor(id_proveedor),
    FOREIGN KEY (material_id) REFERENCES Inventario(id)
);
GO

INSERT INTO Compras (codigo_oc, proveedor_id, material_id, fecha_entrega, cantidad, precio_unitario, total, estado) VALUES
('OC-2026-001', 1, 1, '2026-06-15', 50, 25.50, 1275.00, 'Pendiente'),
('OC-2026-002', 2, 2, '2026-06-20', 30, 45.00, 1350.00, 'En Proceso'),
('OC-2026-003', 3, 3, '2026-06-25', 100, 12.30, 1230.00, 'Recibida'),
('OC-2026-004', 1, 4, '2026-07-01', 25, 38.75, 968.75, 'Pendiente'),
('OC-2026-005', 4, 5, '2026-07-05', 40, 8.50, 340.00, 'En Proceso');
GO

-- ==================== VERIFICACIÓN ====================
PRINT '=== VERIFICACIÓN DE TABLAS ===';
PRINT '';
PRINT 'Tabla Proveedor:';
SELECT COUNT(*) AS 'Total Proveedores' FROM Proveedor;
PRINT '';
PRINT 'Tabla Inventario:';
SELECT COUNT(*) AS 'Total Materiales' FROM Inventario;
PRINT '';
PRINT 'Tabla Compras:';
SELECT COUNT(*) AS 'Total Compras' FROM Compras;
PRINT '';
PRINT 'Datos de compras con relaciones:';
SELECT 
    c.codigo_oc,
    p.nombre AS proveedor,
    m.nombre AS material,
    c.fecha_entrega,
    c.cantidad,
    c.precio_unitario,
    c.total,
    c.estado
FROM Compras c
JOIN Proveedor p ON c.proveedor_id = p.id_proveedor
JOIN Inventario m ON c.material_id = m.id
ORDER BY c.id;
GO

PRINT '=== TODAS LAS TABLAS CREADAS EXITOSAMENTE ===';
GO
