-- Script para crear la tabla TipoVentana
USE INDUSTRIA_RG;
GO

-- Verificar si la tabla existe y eliminarla
IF EXISTS (SELECT * FROM sysobjects WHERE name='TipoVentana' AND xtype='U')
BEGIN
    PRINT 'Eliminando tabla TipoVentana existente...';
    DROP TABLE TipoVentana;
END
GO

-- Crear tabla TipoVentana
CREATE TABLE TipoVentana (
    id_tipo INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    fecha_creacion DATETIME DEFAULT GETDATE()
);
GO

-- Insertar datos de ejemplo
INSERT INTO TipoVentana (nombre, descripcion) VALUES
('Ventana Corrediza', 'Ventana que se desliza horizontalmente'),
('Ventana Fija', 'Ventana que no se abre, fija al marco'),
('Puerta Balcon', 'Puerta que da a un balcón o terraza'),
('Ventana Abatible', 'Ventana que se abre hacia adentro o afuera'),
('Ventana Guillotina', 'Ventana con dos hojas que se deslizan verticalmente'),
('Ventana Proyectable', 'Ventana que se proyecta hacia afuera'),
('Ventana Oscilobatiente', 'Ventana que puede abrirse de dos formas'),
('Puerta Corrediza', 'Puerta que se desliza horizontalmente');
GO

PRINT '=== TABLA TIPOVENTANA CREADA EXITOSAMENTE ===';
GO
