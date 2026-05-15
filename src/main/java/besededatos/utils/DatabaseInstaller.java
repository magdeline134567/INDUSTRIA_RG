package besededatos.utils;

import besededatos.config.Conexion;

import java.io.File;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInstaller {
    public static void main(String[] args) {
        System.out.println("Iniciando instalacion automatica de la base de datos...");
        
        try {
            // Leer el archivo SQL
            File file = new File("database/industria_rg_db.sql");
            if (!file.exists()) {
                System.out.println("❌ No se encontro el archivo SQL en: " + file.getAbsolutePath());
                return;
            }
            
            String sqlContent = new String(Files.readAllBytes(file.toPath()));
            
            // Dividir el script por la palabra GO (SQL Server no entiende GO en JDBC)
            String[] commands = sqlContent.split("(?i)\\bGO\\b");
            
            // Conectar a la base de datos usando tu misma clase Conexion
            Connection conn = Conexion.getInstance().establecerConexion();
            if (conn == null) {
                System.out.println("❌ No se pudo conectar a SQL Server. Revisa tu Conexion.java");
                return;
            }
            
            Statement stmt = conn.createStatement();
            
            int successCount = 0;
            for (String command : commands) {
                command = command.trim();
                if (command.isEmpty()) continue;
                
                try {
                    stmt.execute(command);
                    successCount++;
                } catch (Exception e) {
                    System.err.println("⚠ Advertencia en un comando SQL (puede que la tabla ya exista): " + e.getMessage());
                }
            }
            
            System.out.println("✅ ¡Instalacion completada! Se ejecutaron " + successCount + " bloques SQL.");
            System.out.println("🚀 YA PUEDES ABRIR TU APLICACION Y HACER LOGIN.");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
