package besededatos;

import besededatos.models.*;
import besededatos.controllers.*;
import besededatos.config.*;


import java.sql.*;
import java.util.Scanner;

public class TestInsercion {
    public static void main(String[] args) {
        System.out.println("\n=== TEST REGISTRO DE PERSONAS ===");
        System.out.println("");

        // ✅ OPCIÓN 2: Usar directamente el método estático
        try (Connection conn = Conexion.establecerConexion()) {
            if (conn == null) {
                System.out.println("✗ Error: No se pudo conectar a la base de datos.");
                return;
            }

            System.out.println("✓ Conexión exitosa a la base de datos.\n");

            mostrarCiudades(conn);

            Scanner sc = new Scanner(System.in);
            System.out.println("\n--- INSERTAR PERSONA DE PRUEBA ---");

            System.out.print("Nombre: ");
            String nombre = sc.nextLine();

            System.out.print("Apellido: ");
            String apellido = sc.nextLine();

            System.out.print("Dirección: ");
            String direccion = sc.nextLine();

            System.out.print("Teléfono: ");
            String telefono = sc.nextLine();

            System.out.print("Email: ");
            String email = sc.nextLine();

            String sql = "INSERT INTO Persona (Nombre, Apellido, Direccion, Telefono, Email, Status) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, nombre);
                pstmt.setString(2, apellido);
                pstmt.setString(3, direccion);
                pstmt.setString(4, telefono);
                pstmt.setString(5, email);
                pstmt.setString(6, "activo");

                int filas = pstmt.executeUpdate();
                if (filas > 0) {
                    ResultSet rs = pstmt.getGeneratedKeys();
                    if (rs.next()) {
                        System.out.println("\n✓ PERSONA INSERTADA CORRECTAMENTE!");
                        System.out.println("   ID: " + rs.getInt(1));
                        System.out.println("   Nombre: " + nombre + " " + apellido);
                        System.out.println("   Email: " + email);
                    }
                    rs.close();
                }
            }

        } catch (SQLException e) {
            System.err.println("Error de base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void mostrarCiudades(Connection conn) throws SQLException {
        String sql = "SELECT * FROM Ciudad";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n=== CIUDADES DISPONIBLES ===");
            boolean hayDatos = false;
            while (rs.next()) {
                hayDatos = true;
                System.out.printf("   ID: %d | Nombre: %s%n",
                        rs.getInt("idCiudad"),
                        rs.getString("Nombre"));
            }
            if (!hayDatos) {
                System.out.println("   No hay ciudades registradas.");
            }
            System.out.println();
        }
    }
}