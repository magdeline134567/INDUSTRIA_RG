-- Script para insertar técnicos de ejemplo en SQL Server
-- Base de datos: INDUSTRIA_RG
-- Ejecutar este script si no hay técnicos en el sistema

USE INDUSTRIA_RG;
GO

-- Verificar si la tabla Tecnico existe y crearla si no
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Tecnico' AND xtype='U')
BEGIN
    CREATE TABLE Tecnico (
        id_tecnico INT IDENTITY(1,1) PRIMARY KEY,
        nombre VARCHAR(100) NOT NULL,
        especialidad VARCHAR(50) NOT NULL,
        calificacion DECIMAL(3,1) DEFAULT 0.0,
        telefono VARCHAR(20),
        disponibilidad VARCHAR(20) DEFAULT 'Disponible'
    );
    PRINT 'Tabla Tecnico creada exitosamente';
END
ELSE
BEGIN
    PRINT 'La tabla Tecnico ya existe';
END
GO

-- Primero verificamos la estructura de la tabla
DECLARE @disponibilidad_es_bit BIT = 0;

-- Verificar si la columna disponibilidad es de tipo bit
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS 
           WHERE TABLE_NAME = 'Tecnico' AND COLUMN_NAME = 'disponibilidad' 
           AND DATA_TYPE = 'bit')
BEGIN
    SET @disponibilidad_es_bit = 1;
    PRINT 'Columna disponibilidad es de tipo BIT';
END
ELSE
BEGIN
    PRINT 'Columna disponibilidad es de tipo VARCHAR/CHAR';
END
GO

-- Insertar técnicos de ejemplo (adaptado a la estructura existente)
IF NOT EXISTS (SELECT 1 FROM Tecnico WHERE nombre = 'Juan Pérez')
BEGIN
    -- Si disponibilidad es bit, usamos 1 para Disponible, 0 para Ocupado
    IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS 
               WHERE TABLE_NAME = 'Tecnico' AND COLUMN_NAME = 'disponibilidad' 
               AND DATA_TYPE = 'bit')
    BEGIN
        INSERT INTO Tecnico (nombre, especialidad, calificacion, telefono, disponibilidad) VALUES
        ('Juan Pérez', 'Instalación', 4.5, '123456789', 1),
        ('María García', 'Mantenimiento', 4.8, '987654321', 1),
        ('Carlos Rodríguez', 'Reparación', 4.2, '555123456', 0),
        ('Ana Martínez', 'Instalación', 4.9, '777888999', 1),
        ('Luis Sánchez', 'Mantenimiento', 4.1, '333444555', 1),
        ('Sofía López', 'Reparación', 4.7, '666777888', 1);
        PRINT 'Técnicos insertados (disponibilidad como BIT)';
    END
    ELSE
    BEGIN
        INSERT INTO Tecnico (nombre, especialidad, calificacion, telefono, disponibilidad) VALUES
        ('Juan Pérez', 'Instalación', 4.5, '123456789', 'Disponible'),
        ('María García', 'Mantenimiento', 4.8, '987654321', 'Disponible'),
        ('Carlos Rodríguez', 'Reparación', 4.2, '555123456', 'Ocupado'),
        ('Ana Martínez', 'Instalación', 4.9, '777888999', 'Disponible'),
        ('Luis Sánchez', 'Mantenimiento', 4.1, '333444555', 'Disponible'),
        ('Sofía López', 'Reparación', 4.7, '666777888', 'Disponible');
        PRINT 'Técnicos insertados (disponibilidad como VARCHAR)';
    END
END
ELSE
BEGIN
    PRINT 'Los técnicos de ejemplo ya existen en la base de datos';
END
GO

-- Verificar los técnicos insertados
SELECT * FROM Tecnico ORDER BY nombre;
GO
