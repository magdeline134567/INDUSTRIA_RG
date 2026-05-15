package besededatos;

import besededatos.models.*;
import besededatos.controllers.*;
import besededatos.config.*;


import java.sql.Connection;

public class TestConexion {
    public static void main(String[] args) {
        System.out.println("Probando conexion...");

        try {
            String url = "jdbc:mysql://localhost:3306/puertas_y_ventanas?useSSL=false&serverTimezone=UTC";
            String usuario = "root";
            String contrasena = "1234";

            Connection conn = java.sql.DriverManager.getConnection(url, usuario, contrasena);
            System.out.println("✓ Conexion exitosa a MySQL!");
            conn.close();
        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }
}