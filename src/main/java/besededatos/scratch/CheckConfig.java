package besededatos.scratch;

import besededatos.config.Conexion;
import java.sql.*;

public class CheckConfig {
    public static void main(String[] args) {
        try (Connection conn = Conexion.getInstance().establecerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Configuracion")) {
            
            System.out.println("--- Contenido de Tabla Configuracion ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + 
                                   " | Clave: " + rs.getString("clave") + 
                                   " | Valor: " + rs.getString("valor"));
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
