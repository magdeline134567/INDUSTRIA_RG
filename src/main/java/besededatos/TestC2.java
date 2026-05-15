package besededatos;

import besededatos.models.*;
import besededatos.controllers.*;
import besededatos.config.*;


import java.sql.Connection;

public class TestC2 {
    public static void main(String[] args) {
        System.out.println("Probando conexión...");

        // ✅ CORREGIDO: Usar getInstance() en lugar de new Conexion()
        Conexion conexion = Conexion.getInstance();
        Connection conn = conexion.establecerConexion();

        if (conn != null) {
            System.out.println("✓ Conexión exitosa a la base de datos!");
            System.out.println("Detalles de la conexión: " + conn.toString());
        } else {
            System.out.println("✗ Falló la conexión.");
            System.out.println("Verifica:");
            System.out.println("  1. Que SQL Server esté ejecutándose");
            System.out.println("  2. Que la base de datos 'INDUSTRIA_RG' exista");
            System.out.println("  3. Que el usuario y contraseña sean correctos");
            System.out.println("  4. Que el puerto 1433 esté disponible");
        }
    }
}