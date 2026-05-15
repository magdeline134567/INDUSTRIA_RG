-- Script para crear datos de ejemplo en todas las tablas
USE INDUSTRIA_RG;
GO

-- Datos para tabla Cliente
IF EXISTS (SELECT * FROM sysobjects WHERE name='Cliente' AND xtype='U')
BEGIN
    PRINT 'Insertando datos en tabla Cliente...';
    INSERT INTO Cliente (nombre, telefono, email, direccion) VALUES
    ('Constructora ABC', '555-1234', 'contacto@constructoraabc.com', 'Av. Principal 123'),
    ('Ventanas Modernas SA', '555-5678', 'ventas@ventanasmodernas.com', 'Calle Secundaria 456'),
    ('Proyectos Residenciales Ltda', '555-9012', 'info@proyectosresidenciales.com', 'Plaza Central 789'),
    ('Edificios Corporativos', '555-3456', 'comercial@edificioscorp.com', 'Zona Industrial 321'),
    ('Arquitectura y Diseño', '555-7890', 'diseño@arquitecturaydiseño.com', 'Centro Empresarial 654');
END
GO

-- Datos para tabla Tecnico (si no existen)
IF EXISTS (SELECT * FROM sysobjects WHERE name='Tecnico' AND xtype='U')
BEGIN
    PRINT 'Verificando datos en tabla Tecnico...';
    -- Los técnicos ya deberían existir del script anterior
END
GO

-- Datos para tabla Desglose
IF EXISTS (SELECT * FROM sysobjects WHERE name='Desglose' AND xtype='U')
BEGIN
    PRINT 'Insertando datos en tabla Desglose...';
    INSERT INTO Desglose (codigo, cliente_id, tipo_ventana, ancho, alto, area, perimetro, tipo_perfil, grosor, tipo_vidrio, espesor, herrajes, sellador, acabado, cant_hojas, costo_materiales, mano_obra, gastos_indirectos, precio_venta) VALUES
    ('VEN-2025-001', 1, 'Ventana Corrediza', 1.80, 1.20, 2.16, 6.00, 'Aluminio', '60mm', 'Templado', '6mm', 'Cerradura', 'Silicona', 'Blanco', 2, 450.00, 180.00, 45.00, 750.00),
    ('VEN-2025-002', 2, 'Ventana Fija', 1.50, 1.50, 2.25, 6.00, 'PVC', '45mm', 'Float', '4mm', 'Bisagra', 'Masilla', 'Dorado', 1, 320.00, 120.00, 30.00, 520.00),
    ('VEN-2025-003', 3, 'Puerta Balcon', 0.90, 2.10, 1.89, 6.00, 'Hierro', '50mm', 'Esmerilado', '5mm', 'Manija', 'Burlete', 'Negro', 1, 380.00, 150.00, 40.00, 620.00),
    ('VEN-2025-004', 4, 'Ventana Abatible', 1.20, 1.20, 1.44, 4.80, 'Aluminio', '90mm', 'Reflectivo', '6mm', 'Cerrojo', 'Silicona', 'Plata', 2, 280.00, 110.00, 25.00, 450.00),
    ('VEN-2025-005', 5, 'Ventana Guillotina', 2.00, 1.80, 3.60, 7.60, 'PVC', '45mm', 'Templado', '6mm', 'Bisagra', 'Masilla', 'Blanco', 2, 520.00, 200.00, 50.00, 850.00);
END
GO

-- Datos para tabla Compras
IF EXISTS (SELECT * FROM sysobjects WHERE name='Compras' AND xtype='U')
BEGIN
    PRINT 'Insertando datos en tabla Compras...';
    INSERT INTO Compras (codigo_oc, proveedor_id, material, cantidad, precio_unitario, fecha_entrega) VALUES
    ('OC-2025-001', 1, 'Perfil Aluminio 60mm', 200, 25.50, '2025-01-15'),
    ('OC-2025-002', 2, 'Vidrio Templado 6mm', 100, 45.00, '2025-01-20'),
    ('OC-2025-003', 3, 'Cerradura Estándar', 300, 12.30, '2025-01-25'),
    ('OC-2025-004', 4, 'Marco PVC Blanco', 100, 38.75, '2025-02-01'),
    ('OC-2025-005', 5, 'Silicona Blanca', 200, 8.50, '2025-02-05');
END
GO

-- Datos para tabla Nomina
IF EXISTS (SELECT * FROM sysobjects WHERE name='Nomina' AND xtype='U')
BEGIN
    PRINT 'Insertando datos en tabla Nomina...';
    INSERT INTO Nomina (empleado, puesto, salario_base, horas_extras, deducciones) VALUES
    ('Juan Pérez', 'Técnico Senior', 2500.00, 20, 450.00),
    ('María González', 'Diseñadora', 2200.00, 15, 380.00),
    ('Carlos Rodríguez', 'Instalador', 1800.00, 25, 320.00),
    ('Ana Martínez', 'Vendedora', 2000.00, 10, 350.00),
    ('Luis Sánchez', 'Supervisor', 2800.00, 12, 480.00);
END
GO

-- Datos para tabla Contabilidad
IF EXISTS (SELECT * FROM sysobjects WHERE name='Contabilidad' AND xtype='U')
BEGIN
    PRINT 'Insertando datos en tabla Contabilidad...';
    INSERT INTO Contabilidad (concepto, tipo, monto, fecha, descripcion) VALUES
    ('Venta Ventanas', 'Ingreso', 15000.00, '2025-01-10', 'Venta de 20 ventanas corredizas'),
    ('Compra Materiales', 'Egreso', 8500.00, '2025-01-12', 'Compra de perfiles y vidrios'),
    ('Servicio Instalación', 'Ingreso', 3200.00, '2025-01-15', 'Instalación en edificio corporativo'),
    ('Salarios', 'Egreso', 12500.00, '2025-01-30', 'Pago de nomina mensual'),
    ('Alquiler Local', 'Egreso', 2500.00, '2025-02-01', 'Alquiler de bodega y oficina');
END
GO

-- Datos para tabla Usuario
IF EXISTS (SELECT * FROM sysobjects WHERE name='Usuario' AND xtype='U')
BEGIN
    PRINT 'Insertando datos en tabla Usuario...';
    INSERT INTO Usuario (nombre, usuario, contraseña, rol, estado) VALUES
    ('Administrador', 'admin', 'admin123', 'Administrador', 'Activo'),
    ('Supervisor', 'supervisor', 'sup123', 'Supervisor', 'Activo'),
    ('Técnico', 'tecnico', 'tec123', 'Técnico', 'Activo'),
    ('Vendedor', 'vendedor', 'ven123', 'Vendedor', 'Activo'),
    ('Contador', 'contador', 'con123', 'Contador', 'Activo');
END
GO

PRINT 'Datos de ejemplo creados exitosamente para todas las tablas';
GO
