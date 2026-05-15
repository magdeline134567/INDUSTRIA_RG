-- Script para crear la tabla Instalacion con estructura completa
USE INDUSTRIA_RG;
GO

-- Eliminar tabla si existe para recrear con estructura correcta
IF EXISTS (SELECT * FROM sysobjects WHERE name='Instalacion' AND xtype='U')
BEGIN
    PRINT 'Eliminando tabla Instalacion existente...';
    DROP TABLE Instalacion;
END
GO

-- Crear tabla Instalacion con estructura completa
CREATE TABLE Instalacion (
    id_instalacion INT IDENTITY(1,1) PRIMARY KEY,
    id_orden_trabajo INT NOT NULL,
    fecha DATE NOT NULL,
    hora VARCHAR(10) NOT NULL,
    tecnico_id INT NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'Pendiente',
    notas TEXT NULL,
    fecha_creacion DATETIME DEFAULT GETDATE(),
    fecha_actualizacion DATETIME DEFAULT GETDATE()
);
GO

-- Crear claves foráneas
PRINT 'Creando claves foráneas...';

-- Clave foránea a OrdenTrabajo
ALTER TABLE Instalacion ADD CONSTRAINT FK_Instalacion_OrdenTrabajo 
    FOREIGN KEY (id_orden_trabajo) REFERENCES OrdenTrabajo(id_orden_trabajo)
    ON DELETE NO ACTION ON UPDATE NO ACTION;

-- Clave foránea a Tecnico
ALTER TABLE Instalacion ADD CONSTRAINT FK_Instalacion_Tecnico 
    FOREIGN KEY (tecnico_id) REFERENCES Tecnico(id_tecnico)
    ON DELETE NO ACTION ON UPDATE NO ACTION;

GO

-- Insertar datos de ejemplo
PRINT 'Insertando datos de ejemplo...';

INSERT INTO Instalacion (id_orden_trabajo, fecha, hora, tecnico_id, estado, notas)
VALUES 
(1, '2025-05-15', '09:00', 1, 'Pendiente', 'Instalación programada'),
(2, '2025-05-16', '14:00', 2, 'En Proceso', 'En curso'),
(3, '2025-05-17', '10:30', 1, 'Completada', 'Finalizada exitosamente');

GO

-- Verificar la estructura y datos
PRINT 'Verificando estructura de la tabla:';
SELECT 
    COLUMN_NAME,
    DATA_TYPE,
    IS_NULLABLE,
    COLUMN_DEFAULT
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_NAME = 'Instalacion' 
ORDER BY ORDINAL_POSITION;

PRINT 'Datos insertados:';
SELECT 
    i.id_instalacion,
    o.codigo as orden_trabajo,
    i.fecha,
    i.hora,
    t.nombre as tecnico,
    i.estado,
    i.notas
FROM Instalacion i
LEFT JOIN OrdenTrabajo o ON i.id_orden_trabajo = o.id_orden_trabajo
LEFT JOIN Tecnico t ON i.tecnico_id = t.id_tecnico
ORDER BY i.fecha, i.hora;

GO

PRINT 'Tabla Instalacion creada y configurada exitosamente';
GO
