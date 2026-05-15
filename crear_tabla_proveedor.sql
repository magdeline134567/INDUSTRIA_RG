-- Script para crear la tabla Proveedor
USE INDUSTRIA_RG;
GO

-- Eliminar tabla si existe para recrear
IF EXISTS (SELECT * FROM sysobjects WHERE name='Proveedor' AND xtype='U')
BEGIN
    PRINT 'Eliminando tabla Proveedor existente...';
    DROP TABLE Proveedor;
END
GO

-- Crear tabla Proveedor
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

-- Insertar datos de ejemplo
PRINT 'Insertando datos de ejemplo...';

INSERT INTO Proveedor (nombre, contacto, telefono, email, direccion) VALUES
('Aluminios del Norte', 'Carlos Rodríguez', '555-1001', 'ventas@aluminiosnorte.com', 'Av. Industrial 123'),
('Vidrios y Cristales SA', 'María González', '555-1002', 'info@vidrioscristales.com', 'Calle Comercial 456'),
('Herrajes Pro', 'Juan Pérez', '555-1003', 'contacto@herrajespro.com', 'Zona Industrial 789'),
('Marcos y Perfiles', 'Ana López', '555-1004', 'ventas@marcosperfiles.com', 'Polígono Norte 321'),
('Selladores Industriales', 'Pedro Martínez', '555-1005', 'info@selladoresind.com', 'Parque Empresarial 654');

GO

-- Verificar la estructura y datos
PRINT 'Verificando estructura de la tabla:';
SELECT 
    COLUMN_NAME,
    DATA_TYPE,
    IS_NULLABLE,
    COLUMN_DEFAULT
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_NAME = 'Proveedor' 
ORDER BY ORDINAL_POSITION;

PRINT 'Datos insertados:';
SELECT 
    id_proveedor,
    nombre,
    contacto,
    telefono,
    email,
    direccion
FROM Proveedor
ORDER BY nombre;

GO

PRINT 'Tabla Proveedor creada y configurada exitosamente';
GO
