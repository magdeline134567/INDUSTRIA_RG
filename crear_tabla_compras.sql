-- Script para crear la tabla Compras
USE INDUSTRIA_RG;
GO

-- Eliminar tabla si existe para recrear
IF EXISTS (SELECT * FROM sysobjects WHERE name='Compras' AND xtype='U')
BEGIN
    PRINT 'Eliminando tabla Compras existente...';
    DROP TABLE Compras;
END
GO

-- Crear tabla Compras
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

-- Insertar datos de ejemplo
PRINT 'Insertando datos de ejemplo...';

INSERT INTO Compras (codigo_oc, proveedor_id, material_id, fecha_entrega, cantidad, precio_unitario, total, estado) VALUES
('OC-2026-001', 1, 1, '2026-06-15', 50, 25.50, 1275.00, 'Pendiente'),
('OC-2026-002', 2, 2, '2026-06-20', 30, 45.00, 1350.00, 'En Proceso'),
('OC-2026-003', 3, 3, '2026-06-25', 100, 12.30, 1230.00, 'Recibida'),
('OC-2026-004', 1, 4, '2026-07-01', 25, 38.75, 968.75, 'Pendiente'),
('OC-2026-005', 4, 5, '2026-07-05', 40, 8.50, 340.00, 'En Proceso');

GO

-- Verificar la estructura y datos
PRINT 'Verificando estructura de la tabla:';
SELECT 
    COLUMN_NAME,
    DATA_TYPE,
    IS_NULLABLE,
    COLUMN_DEFAULT
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_NAME = 'Compras' 
ORDER BY ORDINAL_POSITION;

PRINT 'Datos insertados:';
SELECT 
    c.id,
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

PRINT 'Tabla Compras creada y configurada exitosamente';
GO
