-- Script para verificar la estructura de la tabla OrdenTrabajo
USE INDUSTRIA_RG;
GO

-- Verificar si la tabla OrdenTrabajo existe
IF EXISTS (SELECT * FROM sysobjects WHERE name='OrdenTrabajo' AND xtype='U')
BEGIN
    PRINT 'La tabla OrdenTrabajo existe';
    
    -- Mostrar estructura completa de la tabla
    SELECT 
        COLUMN_NAME,
        DATA_TYPE,
        IS_NULLABLE,
        COLUMN_DEFAULT,
        CHARACTER_MAXIMUM_LENGTH
    FROM INFORMATION_SCHEMA.COLUMNS 
    WHERE TABLE_NAME = 'OrdenTrabajo' 
    ORDER BY ORDINAL_POSITION;
    
    -- Mostrar datos existentes (si hay)
    PRINT 'Datos existentes en OrdenTrabajo:';
    SELECT TOP 5 * FROM OrdenTrabajo;
END
ELSE
BEGIN
    PRINT 'La tabla OrdenTrabajo NO existe - creándola...';
    
    -- Crear tabla OrdenTrabajo con estructura completa
    CREATE TABLE OrdenTrabajo (
        id_orden_trabajo INT IDENTITY(1,1) PRIMARY KEY,
        codigo VARCHAR(50) NOT NULL UNIQUE,
        cliente_id INT NOT NULL,
        desglose_id INT NOT NULL,
        fecha_inicio DATE,
        fecha_estimada DATE,
        estado VARCHAR(20) DEFAULT 'Pendiente',
        tecnico_id INT,
        observaciones TEXT,
        fecha_creacion DATETIME DEFAULT GETDATE(),
        fecha_actualizacion DATETIME DEFAULT GETDATE()
    );
    
    -- Crear claves foráneas
    ALTER TABLE OrdenTrabajo ADD CONSTRAINT FK_OrdenTrabajo_Cliente 
        FOREIGN KEY (cliente_id) REFERENCES Cliente(id_cliente);
    
    ALTER TABLE OrdenTrabajo ADD CONSTRAINT FK_OrdenTrabajo_Desglose 
        FOREIGN KEY (desglose_id) REFERENCES Desglose(id_desglose);
    
    ALTER TABLE OrdenTrabajo ADD CONSTRAINT FK_OrdenTrabajo_Tecnico 
        FOREIGN KEY (tecnico_id) REFERENCES Tecnico(id_tecnico);
    
    PRINT 'Tabla OrdenTrabajo creada exitosamente';
END
GO
