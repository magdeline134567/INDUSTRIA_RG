-- Script para crear la tabla Material con estructura completa
USE INDUSTRIA_RG;
GO

-- Eliminar tabla si existe para recrear con estructura correcta
IF EXISTS (SELECT * FROM sysobjects WHERE name='Inventario' AND xtype='U')
BEGIN
    PRINT 'Eliminando tabla Inventario existente...';
    DROP TABLE Inventario;
END
GO

-- Crear tabla Inventario (Material)
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

-- Insertar datos de ejemplo
PRINT 'Insertando datos de ejemplo...';

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
('Silicona Transparente', 'Selladores', 90, 9.20, 13.80, 'Bodega B, Estante 3'),
('Perfil PVC 45mm', 'Perfiles', 180, 18.60, 26.90, 'Bodega A, Estante 5'),
('Vidrio Esmerilado 5mm', 'Vidrios', 95, 35.40, 51.75, 'Bodega B, Estante 5'),
('Manija Aluminio', 'Herrajes', 250, 8.90, 13.40, 'Bodega A, Estante 6'),
('Marco Madera Caoba', 'Marcos', 40, 65.80, 89.50, 'Bodega C, Estante 3'),
('Masilla Flexible', 'Selladores', 150, 5.60, 8.40, 'Bodega B, Estante 6'),
('Tornillo 4x40', 'Herrajes', 500, 0.45, 0.85, 'Bodega A, Estante 7'),
('Burlete Goma', 'Selladores', 200, 3.20, 4.90, 'Bodega B, Estante 7'),
('Perfil Hierro 50mm', 'Perfiles', 80, 28.40, 41.20, 'Bodega A, Estante 8'),
('Vidrio Reflectivo 6mm', 'Vidrios', 60, 52.30, 75.80, 'Bodega B, Estante 8'),
('Cerrojo Seguridad', 'Herrajes', 120, 15.70, 23.50, 'Bodega A, Estante 9');

GO

-- Verificar la estructura y datos
PRINT 'Verificando estructura de la tabla:';
SELECT 
    COLUMN_NAME,
    DATA_TYPE,
    IS_NULLABLE,
    COLUMN_DEFAULT
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_NAME = 'Inventario' 
ORDER BY ORDINAL_POSITION;

PRINT 'Datos insertados:';
SELECT 
    id,
    nombre,
    categoria,
    cantidad,
    costo_unitario,
    precio_unitario,
    ubicacion
FROM Inventario
ORDER BY nombre;

GO

-- Crear categorías únicas para el combobox
PRINT 'Categorías disponibles:';
SELECT DISTINCT categoria FROM Inventario ORDER BY categoria;

GO

PRINT 'Tabla Inventario creada y configurada exitosamente';
GO
