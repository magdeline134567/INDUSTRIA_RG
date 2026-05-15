package besededatos;

import besededatos.config.Conexion;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

public class TestTecnicos {
    public static void main(String[] args) {
        System.out.println("=== PRUEBA DE TÉCNICOS - SQL SERVER ===");
        System.out.println("Base de datos: INDUSTRIA_RG");
        
        Conexion conexion = Conexion.getInstance();
        
        try (Connection conn = conexion.establecerConexion(); 
             Statement stmt = conn.createStatement()) {
            
            // Verificar si la tabla Tecnico existe (SQL Server)
            System.out.println("\n1. Verificando tabla Tecnico...");
            ResultSet rs = stmt.executeQuery(
                "SELECT * FROM sysobjects WHERE name='Tecnico' AND xtype='U'");
            if (rs.next()) {
                System.out.println("✓ La tabla Tecnico existe");
            } else {
                System.out.println("✗ La tabla Tecnico NO existe");
                System.out.println("💡 Ejecute el script insertar_tecnicos.sql para crearla");
                return;
            }
            
            // Verificar estructura de la tabla (SQL Server)
            System.out.println("\n2. Estructura de la tabla Tecnico:");
            rs = stmt.executeQuery(
                "SELECT COLUMN_NAME, DATA_TYPE, IS_NULLABLE " +
                "FROM INFORMATION_SCHEMA.COLUMNS " +
                "WHERE TABLE_NAME = 'Tecnico' " +
                "ORDER BY ORDINAL_POSITION");
            while (rs.next()) {
                System.out.println("  - " + rs.getString("COLUMN_NAME") + 
                                 ": " + rs.getString("DATA_TYPE") + 
                                 " (NULL: " + rs.getString("IS_NULLABLE") + ")");
            }
            
            // Contar técnicos
            System.out.println("\n3. Contando técnicos...");
            rs = stmt.executeQuery("SELECT COUNT(*) as total FROM Tecnico");
            if (rs.next()) {
                int total = rs.getInt("total");
                System.out.println("✓ Total de técnicos: " + total);
                
                if (total > 0) {
                    // Mostrar todos los técnicos
                    System.out.println("\n4. Lista de técnicos:");
                    rs = stmt.executeQuery(
                        "SELECT id_tecnico, nombre, especialidad, calificacion, telefono, disponibilidad " +
                        "FROM Tecnico ORDER BY nombre");
                    while (rs.next()) {
                        System.out.println("  - ID: " + rs.getInt("id_tecnico") + 
                                         ", Nombre: " + rs.getString("nombre") + 
                                         ", Especialidad: " + rs.getString("especialidad") +
                                         ", Calificación: " + rs.getDouble("calificacion") +
                                         ", Teléfono: " + rs.getString("telefono") +
                                         ", Disponibilidad: " + rs.getString("disponibilidad"));
                    }
                } else {
                    System.out.println("⚠ No hay técnicos registrados. Debe agregar técnicos primero.");
                    
                    // Sugerir insertar técnicos de ejemplo
                    System.out.println("\n💡 Sugerencia: Ejecute el script insertar_tecnicos.sql");
                    System.out.println("   o ejecute manualmente en SQL Server:");
                    System.out.println("   USE INDUSTRIA_RG;");
                    System.out.println("   INSERT INTO Tecnico (nombre, especialidad, calificacion, telefono, disponibilidad) VALUES");
                    System.out.println("   ('Juan Pérez', 'Instalación', 4.5, '123456789', 'Disponible'),");
                    System.out.println("   ('María García', 'Mantenimiento', 4.8, '987654321', 'Disponible');");
                }
            }
            
            System.out.println("\n=== PRUEBA COMPLETADA ===");
            
        } catch (Exception e) {
            System.err.println("✗ Error: " + e.getMessage());
            e.printStackTrace();
            System.out.println("\n💡 Verifique:");
            System.out.println("   - Que SQL Server esté corriendo");
            System.out.println("   - Que la base de datos INDUSTRIA_RG exista");
            System.out.println("   - Que el usuario 'sa' tenga contraseña '1234'");
            System.out.println("   - Que el puerto 1433 esté disponible");
        }
    }
}
