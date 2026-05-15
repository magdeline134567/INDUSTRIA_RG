-- Script final corregido para insertar datos de ejemplo
USE INDUSTRIA_RG;
GO

PRINT '=== INSERTANDO DATOS DE EJEMPLO ===';

-- Datos para tabla Cliente
IF NOT EXISTS (SELECT 1 FROM Cliente WHERE nombre = 'Constructora ABC')
BEGIN
    PRINT 'Insertando datos en tabla Cliente...';
    INSERT INTO Cliente (nombre, telefono, email, direccion) VALUES
    ('Constructora ABC', '555-1234', 'contacto@constructoraabc.com', 'Av. Principal 123'),
    ('Ventanas Modernas SA', '555-5678', 'ventas@ventanasmodernas.com', 'Calle Secundaria 456'),
    ('Proyectos Residenciales Ltda', '555-9012', 'info@proyectosresidenciales.com', 'Plaza Central 789'),
    ('Edificios Corporativos', '555-3456', 'comercial@edificioscorp.com', 'Zona Industrial 321'),
    ('Arquitectura y Diseño', '555-7890', 'diseño@arquitecturaydiseño.com', 'Centro Empresarial 654');
    PRINT '✅ Clientes insertados correctamente';
END
ELSE
BEGIN
    PRINT 'ℹ️ Los clientes ya existen';
END
GO

-- Datos para tabla Desglose
IF NOT EXISTS (SELECT 1 FROM Desglose WHERE codigo LIKE 'VEN-%')
BEGIN
    PRINT 'Insertando datos en tabla Desglose...';
    INSERT INTO Desglose (codigo, ancho, alto, precio_venta) VALUES
    ('VEN-2025-001', 1.80, 1.20, 750.00),
    ('VEN-2025-002', 1.50, 1.50, 520.00),
    ('VEN-2025-003', 0.90, 2.10, 620.00),
    ('VEN-2025-004', 1.20, 1.20, 450.00),
    ('VEN-2025-005', 2.00, 1.80, 850.00);
    PRINT '✅ Desgloses insertados correctamente';
END
ELSE
BEGIN
    PRINT 'ℹ️ Los desgloses ya existen';
END
GO

-- Datos para tabla OrdenTrabajo
IF NOT EXISTS (SELECT 1 FROM OrdenTrabajo WHERE codigo LIKE 'OT-%')
BEGIN
    PRINT 'Insertando datos en tabla OrdenTrabajo...';
    INSERT INTO OrdenTrabajo (codigo, estado) VALUES
    ('OT-2025-001', 'Pendiente'),
    ('OT-2025-002', 'En Producción'),
    ('OT-2025-003', 'Lista para Instalar'),
    ('OT-2025-004', 'Instalada'),
    ('OT-2025-005', 'Cancelada');
    PRINT '✅ Órdenes insertadas correctamente';
END
ELSE
BEGIN
    PRINT 'ℹ️ Las órdenes ya existen';
END
GO

-- Datos para tabla Usuario
IF NOT EXISTS (SELECT 1 FROM Usuario WHERE nombre_completo IS NOT NULL)
BEGIN
    PRINT 'Insertando datos en tabla Usuario...';
    INSERT INTO Usuario (nombre_completo, rol) VALUES
    ('Administrador Principal', 'Administrador'),
    ('Supervisor de Producción', 'Supervisor'),
    ('Técnico Especialista', 'Técnico'),
    ('Vendedor Ejecutivo', 'Vendedor'),
    ('Contador General', 'Contador');
    PRINT '✅ Usuarios insertados correctamente';
END
ELSE
BEGIN
    PRINT 'ℹ️ Los usuarios ya existen';
END
GO

PRINT '=== DATOS DE EJEMPLO INSERTADOS CORRECTAMENTE ===';
GO
