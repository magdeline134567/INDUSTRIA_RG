-- Script para crear la tabla ACTIVIDAD_FEED
USE INDUSTRIA_RG;
GO

-- Verificar si la tabla existe y eliminarla
IF EXISTS (SELECT * FROM sysobjects WHERE name='ACTIVIDAD_FEED' AND xtype='U')
BEGIN
    PRINT 'Eliminando tabla ACTIVIDAD_FEED existente...';
    DROP TABLE ACTIVIDAD_FEED;
END
GO

-- Crear tabla ACTIVIDAD_FEED
CREATE TABLE ACTIVIDAD_FEED (
    id INT IDENTITY(1,1) PRIMARY KEY,
    mensaje VARCHAR(500) NOT NULL,
    usuario VARCHAR(100) NOT NULL,
    fecha_hora DATETIME DEFAULT GETDATE()
);
GO

-- Crear índice para mejorar rendimiento en consultas de fecha
CREATE INDEX IX_ACTIVIDAD_FEED_FECHA ON ACTIVIDAD_FEED(fecha_hora DESC);
GO

PRINT '=== TABLA ACTIVIDAD_FEED CREADA EXITOSAMENTE ===';
GO
