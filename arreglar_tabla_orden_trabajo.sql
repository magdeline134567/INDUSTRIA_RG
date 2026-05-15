-- Script para arreglar la tabla OrdenTrabajo sin eliminarla completamente
USE INDUSTRIA_RG;
GO

-- Paso 1: Eliminar restricciones de clave foránea si existen
PRINT 'Eliminando restricciones de clave foránea...';

IF EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_Instalacion_OrdenTrabajo')
BEGIN
    PRINT 'Eliminando FK_Instalacion_OrdenTrabajo...';
    ALTER TABLE Instalacion DROP CONSTRAINT FK_Instalacion_OrdenTrabajo;
END

IF EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_OrdenTrabajo_Cliente')
BEGIN
    PRINT 'Eliminando FK_OrdenTrabajo_Cliente...';
    ALTER TABLE OrdenTrabajo DROP CONSTRAINT FK_OrdenTrabajo_Cliente;
END

IF EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_OrdenTrabajo_Desglose')
BEGIN
    PRINT 'Eliminando FK_OrdenTrabajo_Desglose...';
    ALTER TABLE OrdenTrabajo DROP CONSTRAINT FK_OrdenTrabajo_Desglose;
END

IF EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_OrdenTrabajo_Tecnico')
BEGIN
    PRINT 'Eliminando FK_OrdenTrabajo_Tecnico...';
    ALTER TABLE OrdenTrabajo DROP CONSTRAINT FK_OrdenTrabajo_Tecnico;
END
GO

-- Paso 2: Verificar estructura actual de la tabla
PRINT 'Verificando estructura actual de OrdenTrabajo:';
SELECT 
    COLUMN_NAME,
    DATA_TYPE,
    IS_NULLABLE,
    CHARACTER_MAXIMUM_LENGTH
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_NAME = 'OrdenTrabajo' 
ORDER BY ORDINAL_POSITION;
GO

-- Paso 3: Agregar columnas faltantes si no existen
PRINT 'Verificando y agregando columnas faltantes...';

-- Agregar columna estado si no existe
IF NOT EXISTS (SELECT * FROM syscolumns WHERE id = OBJECT_ID('OrdenTrabajo') AND name = 'estado')
BEGIN
    PRINT 'Agregando columna estado...';
    ALTER TABLE OrdenTrabajo ADD estado VARCHAR(20) NOT NULL DEFAULT 'Pendiente';
END
ELSE
BEGIN
    PRINT 'La columna estado ya existe';
END

-- Agregar columna observaciones si no existe
IF NOT EXISTS (SELECT * FROM syscolumns WHERE id = OBJECT_ID('OrdenTrabajo') AND name = 'observaciones')
BEGIN
    PRINT 'Agregando columna observaciones...';
    ALTER TABLE OrdenTrabajo ADD observaciones TEXT NULL;
END
ELSE
BEGIN
    PRINT 'La columna observaciones ya existe';
END

-- Agregar columna fecha_creacion si no existe
IF NOT EXISTS (SELECT * FROM syscolumns WHERE id = OBJECT_ID('OrdenTrabajo') AND name = 'fecha_creacion')
BEGIN
    PRINT 'Agregando columna fecha_creacion...';
    ALTER TABLE OrdenTrabajo ADD fecha_creacion DATETIME DEFAULT GETDATE();
END
ELSE
BEGIN
    PRINT 'La columna fecha_creacion ya existe';
END

-- Agregar columna fecha_actualizacion si no existe
IF NOT EXISTS (SELECT * FROM syscolumns WHERE id = OBJECT_ID('OrdenTrabajo') AND name = 'fecha_actualizacion')
BEGIN
    PRINT 'Agregando columna fecha_actualizacion...';
    ALTER TABLE OrdenTrabajo ADD fecha_actualizacion DATETIME DEFAULT GETDATE();
END
ELSE
BEGIN
    PRINT 'La columna fecha_actualizacion ya existe';
END
GO

-- Paso 4: Recrear claves foráneas
PRINT 'Recreando claves foráneas...';

-- Verificar que las tablas referenciadas existen antes de crear las claves foráneas
IF EXISTS (SELECT * FROM sysobjects WHERE name='Cliente' AND xtype='U')
BEGIN
    ALTER TABLE OrdenTrabajo ADD CONSTRAINT FK_OrdenTrabajo_Cliente 
        FOREIGN KEY (cliente_id) REFERENCES Cliente(id_cliente)
        ON DELETE NO ACTION ON UPDATE NO ACTION;
    PRINT 'FK_OrdenTrabajo_Cliente creada';
END
ELSE
BEGIN
    PRINT 'Tabla Cliente no encontrada - omitiendo FK_OrdenTrabajo_Cliente';
END

IF EXISTS (SELECT * FROM sysobjects WHERE name='Desglose' AND xtype='U')
BEGIN
    ALTER TABLE OrdenTrabajo ADD CONSTRAINT FK_OrdenTrabajo_Desglose 
        FOREIGN KEY (desglose_id) REFERENCES Desglose(id_desglose)
        ON DELETE NO ACTION ON UPDATE NO ACTION;
    PRINT 'FK_OrdenTrabajo_Desglose creada';
END
ELSE
BEGIN
    PRINT 'Tabla Desglose no encontrada - omitiendo FK_OrdenTrabajo_Desglose';
END

IF EXISTS (SELECT * FROM sysobjects WHERE name='Tecnico' AND xtype='U')
BEGIN
    ALTER TABLE OrdenTrabajo ADD CONSTRAINT FK_OrdenTrabajo_Tecnico 
        FOREIGN KEY (tecnico_id) REFERENCES Tecnico(id_tecnico)
        ON DELETE SET NULL ON UPDATE NO ACTION;
    PRINT 'FK_OrdenTrabajo_Tecnico creada';
END
ELSE
BEGIN
    PRINT 'Tabla Tecnico no encontrada - omitiendo FK_OrdenTrabajo_Tecnico';
END
GO

-- Paso 5: Verificar estructura final
PRINT 'Estructura final de OrdenTrabajo:';
SELECT 
    COLUMN_NAME,
    DATA_TYPE,
    IS_NULLABLE,
    COLUMN_DEFAULT,
    CHARACTER_MAXIMUM_LENGTH
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_NAME = 'OrdenTrabajo' 
ORDER BY ORDINAL_POSITION;
GO

-- Paso 6: Mostrar datos existentes
PRINT 'Datos existentes en OrdenTrabajo:';
SELECT TOP 5 * FROM OrdenTrabajo ORDER BY id_orden_trabajo;
GO

PRINT 'Tabla OrdenTrabajo arreglada exitosamente';
GO
