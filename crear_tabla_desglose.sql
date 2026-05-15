-- Script para agregar columnas faltantes a la tabla Desglose (sin eliminar datos)
USE INDUSTRIA_RG;
GO

-- Verificar si la tabla existe, si no existe crearla
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Desglose' AND xtype='U')
BEGIN
    PRINT 'Creando tabla Desglose...';
    CREATE TABLE Desglose (
        id_desglose INT IDENTITY(1,1) PRIMARY KEY,
        codigo VARCHAR(50) NOT NULL UNIQUE,
        cliente_id INT NULL,
        tipo_ventana_id INT NULL,
        ancho DECIMAL(10,2) NOT NULL,
        alto DECIMAL(10,2) NOT NULL,
        area DECIMAL(10,2) NULL,
        perimetro DECIMAL(10,2) NULL,
        tipo_perfil VARCHAR(50) NULL,
        grosor_mm VARCHAR(20) NULL,
        tipo_vidrio VARCHAR(50) NULL,
        espesor_mm VARCHAR(20) NULL,
        herrajes VARCHAR(50) NULL,
        sellador VARCHAR(50) NULL,
        acabado VARCHAR(50) NULL,
        cant_hojas INT NULL,
        costo_materiales DECIMAL(10,2) NULL,
        mano_obra DECIMAL(10,2) NULL,
        gastos_indirectos DECIMAL(10,2) NULL,
        precio_venta DECIMAL(10,2) NULL,
        margen_ganancia DECIMAL(10,2) NULL,
        fecha_registro DATETIME DEFAULT GETDATE()
    );
    PRINT 'Tabla Desglose creada exitosamente';
END
ELSE
BEGIN
    PRINT 'Tabla Desglose ya existe, verificando columnas faltantes...';
    
    -- Agregar columnas faltantes si no existen
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Desglose') AND name = 'cliente_id')
    BEGIN
        ALTER TABLE Desglose ADD cliente_id INT NULL;
        PRINT 'Columna cliente_id agregada';
    END
    
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Desglose') AND name = 'tipo_ventana_id')
    BEGIN
        ALTER TABLE Desglose ADD tipo_ventana_id INT NULL;
        PRINT 'Columna tipo_ventana_id agregada';
    END
    
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Desglose') AND name = 'area')
    BEGIN
        ALTER TABLE Desglose ADD area DECIMAL(10,2) NULL;
        PRINT 'Columna area agregada';
    END
    
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Desglose') AND name = 'perimetro')
    BEGIN
        ALTER TABLE Desglose ADD perimetro DECIMAL(10,2) NULL;
        PRINT 'Columna perimetro agregada';
    END
    
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Desglose') AND name = 'tipo_perfil')
    BEGIN
        ALTER TABLE Desglose ADD tipo_perfil VARCHAR(50) NULL;
        PRINT 'Columna tipo_perfil agregada';
    END
    
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Desglose') AND name = 'grosor_mm')
    BEGIN
        ALTER TABLE Desglose ADD grosor_mm VARCHAR(20) NULL;
        PRINT 'Columna grosor_mm agregada';
    END
    
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Desglose') AND name = 'tipo_vidrio')
    BEGIN
        ALTER TABLE Desglose ADD tipo_vidrio VARCHAR(50) NULL;
        PRINT 'Columna tipo_vidrio agregada';
    END
    
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Desglose') AND name = 'espesor_mm')
    BEGIN
        ALTER TABLE Desglose ADD espesor_mm VARCHAR(20) NULL;
        PRINT 'Columna espesor_mm agregada';
    END
    
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Desglose') AND name = 'herrajes')
    BEGIN
        ALTER TABLE Desglose ADD herrajes VARCHAR(50) NULL;
        PRINT 'Columna herrajes agregada';
    END
    
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Desglose') AND name = 'sellador')
    BEGIN
        ALTER TABLE Desglose ADD sellador VARCHAR(50) NULL;
        PRINT 'Columna sellador agregada';
    END
    
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Desglose') AND name = 'acabado')
    BEGIN
        ALTER TABLE Desglose ADD acabado VARCHAR(50) NULL;
        PRINT 'Columna acabado agregada';
    END
    
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Desglose') AND name = 'cant_hojas')
    BEGIN
        ALTER TABLE Desglose ADD cant_hojas INT NULL;
        PRINT 'Columna cant_hojas agregada';
    END
    
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Desglose') AND name = 'costo_materiales')
    BEGIN
        ALTER TABLE Desglose ADD costo_materiales DECIMAL(10,2) NULL;
        PRINT 'Columna costo_materiales agregada';
    END
    
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Desglose') AND name = 'mano_obra')
    BEGIN
        ALTER TABLE Desglose ADD mano_obra DECIMAL(10,2) NULL;
        PRINT 'Columna mano_obra agregada';
    END
    
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Desglose') AND name = 'gastos_indirectos')
    BEGIN
        ALTER TABLE Desglose ADD gastos_indirectos DECIMAL(10,2) NULL;
        PRINT 'Columna gastos_indirectos agregada';
    END
    
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Desglose') AND name = 'margen_ganancia')
    BEGIN
        ALTER TABLE Desglose ADD margen_ganancia DECIMAL(10,2) NULL;
        PRINT 'Columna margen_ganancia agregada';
    END
    
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Desglose') AND name = 'fecha_registro')
    BEGIN
        ALTER TABLE Desglose ADD fecha_registro DATETIME DEFAULT GETDATE();
        PRINT 'Columna fecha_registro agregada';
    END
    
    PRINT 'Verificación de columnas completada';
END
GO

-- Crear claves foráneas
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_Desglose_Cliente')
BEGIN
    ALTER TABLE Desglose ADD CONSTRAINT FK_Desglose_Cliente 
        FOREIGN KEY (cliente_id) REFERENCES Cliente(id_cliente)
        ON DELETE SET NULL ON UPDATE CASCADE;
    PRINT 'FK_Desglose_Cliente creada';
END
GO

IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_Desglose_TipoVentana')
BEGIN
    ALTER TABLE Desglose ADD CONSTRAINT FK_Desglose_TipoVentana 
        FOREIGN KEY (tipo_ventana_id) REFERENCES TipoVentana(id_tipo)
        ON DELETE SET NULL ON UPDATE CASCADE;
    PRINT 'FK_Desglose_TipoVentana creada';
END
GO

PRINT '=== TABLA DESGLOSE CREADA EXITOSAMENTE ===';
GO
