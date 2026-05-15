-- Script para ver la estructura real de la tabla Tecnico
USE INDUSTRIA_RG;
GO

-- Verificar estructura de la tabla Tecnico
SELECT 
    COLUMN_NAME,
    DATA_TYPE,
    IS_NULLABLE,
    COLUMN_DEFAULT,
    CHARACTER_MAXIMUM_LENGTH
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_NAME = 'Tecnico' 
ORDER BY ORDINAL_POSITION;
GO

-- Verificar datos existentes
SELECT TOP 5 * FROM Tecnico;
GO
