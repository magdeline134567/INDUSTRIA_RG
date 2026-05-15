-- Script para crear/actualizar la tabla OrdenTrabajo con estructura completa
USE INDUSTRIA_RG;
GO

-- Eliminar tabla si existe para recrear con estructura correcta
IF EXISTS (SELECT * FROM sysobjects WHERE name='OrdenTrabajo' AND xtype='U')
BEGIN
    PRINT 'Eliminando tabla OrdenTrabajo existente...';
    DROP TABLE OrdenTrabajo;
END
GO

-- Crear tabla OrdenTrabajo con estructura completa
CREATE TABLE OrdenTrabajo (
    id_orden_trabajo INT IDENTITY(1,1) PRIMARY KEY,
    codigo VARCHAR(50) NOT NULL UNIQUE,
    cliente_id INT NOT NULL,
    desglose_id INT NOT NULL,
    fecha_inicio DATE NULL,
    fecha_estimada DATE NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'Pendiente',
    tecnico_id INT NULL,
    observaciones TEXT NULL,
    fecha_creacion DATETIME DEFAULT GETDATE(),
    fecha_actualizacion DATETIME DEFAULT GETDATE()
);
GO

-- Crear claves foráneas
PRINT 'Creando claves foráneas...';

-- Clave foránea a Cliente
ALTER TABLE OrdenTrabajo ADD CONSTRAINT FK_OrdenTrabajo_Cliente 
    FOREIGN KEY (cliente_id) REFERENCES Cliente(id_cliente)
    ON DELETE NO ACTION ON UPDATE NO ACTION;

-- Clave foránea a Desglose  
ALTER TABLE OrdenTrabajo ADD CONSTRAINT FK_OrdenTrabajo_Desglose 
    FOREIGN KEY (desglose_id) REFERENCES Desglose(id_desglose)
    ON DELETE NO ACTION ON UPDATE NO ACTION;

-- Clave foránea a Tecnico
ALTER TABLE OrdenTrabajo ADD CONSTRAINT FK_OrdenTrabajo_Tecnico 
    FOREIGN KEY (tecnico_id) REFERENCES Tecnico(id_tecnico)
    ON DELETE SET NULL ON UPDATE NO ACTION;

GO

-- Insertar datos de ejemplo
PRINT 'Insertando datos de ejemplo...';

INSERT INTO OrdenTrabajo (codigo, cliente_id, desglose_id, fecha_inicio, fecha_estimada, estado, tecnico_id, observaciones)
VALUES 
('OT-2025-001', 1, 1, '2025-05-12', '2025-05-20', 'Pendiente', 1, 'Instalación de ventana principal'),
('OT-2025-002', 2, 2, '2025-05-13', '2025-05-25', 'En Proceso', 2, 'Reparación de puerta corrediza'),
('OT-2025-003', 3, 3, '2025-05-14', '2025-05-30', 'Completada', 1, 'Mantenimiento general');

GO

-- Verificar la estructura y datos
PRINT 'Verificando estructura de la tabla:';
SELECT 
    COLUMN_NAME,
    DATA_TYPE,
    IS_NULLABLE,
    COLUMN_DEFAULT
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_NAME = 'OrdenTrabajo' 
ORDER BY ORDINAL_POSITION;

PRINT 'Datos insertados:';
SELECT 
    o.codigo,
    c.nombre as cliente,
    d.codigo as desglose,
    o.fecha_inicio,
    o.fecha_estimada,
    o.estado,
    t.nombre as tecnico,
    o.observaciones
FROM OrdenTrabajo o
LEFT JOIN Cliente c ON o.cliente_id = c.id_cliente
LEFT JOIN Desglose d ON o.desglose_id = d.id_desglose  
LEFT JOIN Tecnico t ON o.tecnico_id = t.id_tecnico
ORDER BY o.codigo;

GO

PRINT 'Tabla OrdenTrabajo creada y configurada exitosamente';
GO
