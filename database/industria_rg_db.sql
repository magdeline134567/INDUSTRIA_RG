-- =======================================================
-- PROYECTO: INDUSTRIA_RG
-- SCRIPT DE BASE DE DATOS (SQL Server)
-- =======================================================

-- 1. Creación de la base de datos
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = N'INDUSTRIA_RG')
BEGIN
    CREATE DATABASE INDUSTRIA_RG;
END
GO

USE INDUSTRIA_RG;
GO

-- =======================================================
-- 2. CREACIÓN DE TABLAS
-- =======================================================

-- Tabla Roles (Para el RBAC - Role Based Access Control)
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Roles]') AND type in (N'U'))
BEGIN
    CREATE TABLE Roles (
        id_rol INT IDENTITY(1,1) PRIMARY KEY,
        nombre_rol NVARCHAR(50) NOT NULL UNIQUE
    );
END
GO

-- Tabla Usuarios (Para el Login)
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Usuarios]') AND type in (N'U'))
BEGIN
    CREATE TABLE Usuarios (
        id_usuario INT IDENTITY(1,1) PRIMARY KEY,
        username NVARCHAR(50) NOT NULL UNIQUE,
        password NVARCHAR(255) NOT NULL, -- En producción debe estar encriptado
        email NVARCHAR(100) NOT NULL,
        id_rol INT NOT NULL,
        estado BIT DEFAULT 1, -- 1: Activo, 0: Inactivo
        FOREIGN KEY (id_rol) REFERENCES Roles(id_rol)
    );
END
GO

-- Tabla Categorias (Para el CRUD)
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Categorias]') AND type in (N'U'))
BEGIN
    CREATE TABLE Categorias (
        id_categoria INT IDENTITY(1,1) PRIMARY KEY,
        nombre NVARCHAR(100) NOT NULL,
        descripcion NVARCHAR(255) NULL
    );
END
GO

-- Tabla Productos (Para el CRUD y JFreeChart)
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Productos]') AND type in (N'U'))
BEGIN
    CREATE TABLE Productos (
        id_producto INT IDENTITY(1,1) PRIMARY KEY,
        nombre NVARCHAR(150) NOT NULL,
        precio DECIMAL(18,2) NOT NULL,
        stock INT NOT NULL,
        id_categoria INT NOT NULL,
        fecha_registro DATETIME DEFAULT GETDATE(),
        FOREIGN KEY (id_categoria) REFERENCES Categorias(id_categoria)
    );
END
GO

-- =======================================================
-- 3. INSERCIÓN DE DATOS DE PRUEBA (MOCK DATA)
-- =======================================================

-- Insertar Roles
IF NOT EXISTS (SELECT 1 FROM Roles WHERE nombre_rol = 'Administrador')
BEGIN
    INSERT INTO Roles (nombre_rol) VALUES ('Administrador'), ('UsuarioNormal');
END
GO

-- Insertar Usuarios de prueba
IF NOT EXISTS (SELECT 1 FROM Usuarios WHERE username = 'admin')
BEGIN
    INSERT INTO Usuarios (username, password, email, id_rol, estado)
    VALUES 
    ('admin', 'admin123', 'admin@industriarg.com', 1, 1),
    ('juanperez', 'user123', 'juan.perez@industriarg.com', 2, 1);
END
GO

-- Insertar Categorías
IF NOT EXISTS (SELECT 1 FROM Categorias WHERE nombre = 'Electrónica')
BEGIN
    INSERT INTO Categorias (nombre, descripcion)
    VALUES 
    ('Electrónica', 'Componentes y dispositivos electrónicos'),
    ('Mecánica', 'Repuestos y piezas mecánicas'),
    ('Herramientas', 'Herramientas de trabajo manuales y eléctricas');
END
GO

-- Insertar Productos
IF NOT EXISTS (SELECT 1 FROM Productos WHERE nombre = 'Motor Eléctrico 5HP')
BEGIN
    INSERT INTO Productos (nombre, precio, stock, id_categoria)
    VALUES 
    ('Motor Eléctrico 5HP', 450.50, 15, 1),
    ('Rodamiento SKF 6204', 12.00, 150, 2),
    ('Taladro Percutor Bosch', 120.00, 30, 3),
    ('Sensor Inductivo M12', 35.75, 45, 1),
    ('Engranaje Helicoidal', 85.00, 20, 2);
END
GO

PRINT 'Base de datos INDUSTRIA_RG creada y poblada con éxito.';
GO
