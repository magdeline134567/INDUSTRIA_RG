-- =======================================================
-- CONFIGURACIÓN DE CORREO (SMTP) PARA INDUSTRIA_RG
-- =======================================================

-- 1. Crear la tabla si no existe (Asegurando estructura correcta)
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Configuracion]') AND type in (N'U'))
BEGIN
    CREATE TABLE Configuracion (
        id INT IDENTITY(1,1) PRIMARY KEY,
        clave NVARCHAR(50) NOT NULL UNIQUE,
        valor NVARCHAR(MAX) NOT NULL,
        descripcion NVARCHAR(255) NULL
    );
END
GO

-- 2. Insertar parámetros base si no existen
IF NOT EXISTS (SELECT 1 FROM Configuracion WHERE clave = 'SMTP_HOST')
    INSERT INTO Configuracion (clave, valor, descripcion) VALUES ('SMTP_HOST', 'smtp.gmail.com', 'Servidor SMTP (ej: smtp.gmail.com)');

IF NOT EXISTS (SELECT 1 FROM Configuracion WHERE clave = 'SMTP_PORT')
    INSERT INTO Configuracion (clave, valor, descripcion) VALUES ('SMTP_PORT', '587', 'Puerto SMTP (587 para TLS, 465 para SSL)');

IF NOT EXISTS (SELECT 1 FROM Configuracion WHERE clave = 'SMTP_USER')
    INSERT INTO Configuracion (clave, valor, descripcion) VALUES ('SMTP_USER', 'tu_correo@gmail.com', 'Usuario/Correo del remitente');

IF NOT EXISTS (SELECT 1 FROM Configuracion WHERE clave = 'SMTP_PASS')
    INSERT INTO Configuracion (clave, valor, descripcion) VALUES ('SMTP_PASS', 'tu_password_de_aplicacion', 'Contraseña o App Password');

IF NOT EXISTS (SELECT 1 FROM Configuracion WHERE clave = 'SMTP_AUTH')
    INSERT INTO Configuracion (clave, valor, descripcion) VALUES ('SMTP_AUTH', 'true', 'Requiere autenticación (true/false)');

IF NOT EXISTS (SELECT 1 FROM Configuracion WHERE clave = 'SMTP_TLS')
    INSERT INTO Configuracion (clave, valor, descripcion) VALUES ('SMTP_TLS', 'true', 'Habilitar STARTTLS (true/false)');

PRINT 'Tabla Configuracion verificada e inicializada correctamente.';
