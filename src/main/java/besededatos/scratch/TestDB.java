package besededatos.scratch;
import besededatos.config.Conexion;
import java.sql.*;

public class TestDB {
    public static void main(String[] args) {
        System.out.println("--- DIAGNÓSTICO DE BASE DE DATOS ---");
        try (Connection conn = Conexion.getInstance().establecerConexion()) {
            if (conn == null) {
                System.err.println("Fallo total de conexión.");
                return;
            }
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, "Configuracion", null);
            if (tables.next()) {
                System.out.println("OK: La tabla 'Configuracion' existe.");
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Configuracion");
                if (rs.next()) {
                    System.out.println("Registros encontrados: " + rs.getInt(1));
                }
            } else {
                System.err.println("ERROR: La tabla 'Configuracion' NO EXISTE en esta base de datos.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
