-- Script corregido para insertar datos de ejemplo en tablas existentes
USE INDUSTRIA_RG;
GO

PRINT '=== INSERTANDO DATOS DE EJEMPLO CORREGIDOS ===';

-- Verificar si existen datos antes de insertar
-- Datos para tabla Cliente (si no existen)
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
    PRINT 'ℹ️ Los clientes ya existen en la tabla';
END
GO

-- Datos para tabla Desglose (estructura simplificada)
IF NOT EXISTS (SELECT 1 FROM Desglose WHERE codigo LIKE 'VEN-%')
BEGIN
    PRINT 'Insertando datos en tabla Desglose...';
    -- Insertar con estructura básica (adaptar según columnas reales)
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
    PRINT 'ℹ️ Los desgloses ya existen en la tabla';
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
    PRINT '✅ Órdenes de trabajo insertadas correctamente';
END
ELSE
BEGIN
    PRINT 'ℹ️ Las órdenes de trabajo ya existen en la tabla';
END
GO

-- Datos para tabla Compras
IF NOT EXISTS (SELECT 1 FROM Compras WHERE codigo_oc LIKE 'OC-%')
BEGIN
    PRINT 'Insertando datos en tabla Compras...';
    INSERT INTO Compras (codigo_oc, cantidad, precio_unitario) VALUES
    ('OC-2025-001', 200, 25.50),
    ('OC-2025-002', 100, 45.00),
    ('OC-2025-003', 300, 12.30),
    ('OC-2025-004', 100, 38.75),
    ('OC-2025-005', 200, 8.50);
    PRINT '✅ Compras insertadas correctamente';
END
ELSE
BEGIN
    PRINT 'ℹ️ Las compras ya existen en la tabla';
END
GO

-- Datos para tabla Nomina
IF NOT EXISTS (SELECT 1 FROM Nomina WHERE salario_base > 0)
BEGIN
    PRINT 'Insertando datos en tabla Nomina...';
    -- Adaptar según estructura real de la tabla
    INSERT INTO Nomina (salario_base) VALUES
    (2500.00),
    (2200.00),
    (1800.00),
    (2000.00),
    (2800.00);
    PRINT '✅ Datos de nómina insertados correctamente';
END
ELSE
BEGIN
    PRINT 'ℹ️ Los datos de nómina ya existen en la tabla';
END
GO

-- Datos para tabla Contabilidad
IF NOT EXISTS (SELECT 1 FROM Contabilidad WHERE monto > 0)
BEGIN
    PRINT 'Insertando datos en tabla Contabilidad...';
    -- Adaptar según estructura real de la tabla
    INSERT INTO Contabilidad (monto) VALUES
    (15000.00),
    (8500.00),
    (3200.00),
    (12500.00),
    (2500.00);
    PRINT '✅ Datos de contabilidad insertados correctamente';
END
ELSE
BEGIN
    PRINT 'ℹ️ Los datos de contabilidad ya existen en la tabla';
END
GO

-- Datos para tabla Usuario
IF NOT EXISTS (SELECT 1 FROM Usuario WHERE rol IS NOT NULL)
BEGIN
    PRINT 'Insertando datos en tabla Usuario...';
    -- Adaptar según estructura real de la tabla
    INSERT INTO Usuario (rol) VALUES
    ('Administrador'),
    ('Supervisor'),
    ('Técnico'),
    ('Vendedor'),
    ('Contador');
    PRINT '✅ Usuarios insertados correctamente';
END
ELSE
BEGIN
    PRINT 'ℹ️ Los usuarios ya existen en la tabla';
END
GO

PRINT '=== DATOS DE EJEMPLO INSERTADOS CORRECTAMENTE ===';
GO
