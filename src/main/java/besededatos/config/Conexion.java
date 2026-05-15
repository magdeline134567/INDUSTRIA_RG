package besededatos.config;

import besededatos.models.*;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.*;
import java.sql.*;

public class Conexion {

    // ✅ SINGLETON: instancia única estática
    private static Conexion instancia = null;
    private static Connection connection = null;

    // Cambiar a static para poder usarlos en métodos estáticos
    static String db = "INDUSTRIA_RG";
    static String server = "localhost";
    static String puerto = "1433";  // ✅ Ahora es static
    static String usuario = "sa";    // ✅ Ahora es static
    static String contrasena = "1234"; // ✅ Ahora es static

    // ✅ Constructor PRIVADO — nadie puede hacer new Conexion()
    private Conexion() {}

    // ✅ Único punto de acceso — siempre devuelve la MISMA instancia
    public static Conexion getInstance() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }

    public static Connection establecerConexion() {
        try {
            // ✅ Solo conecta si no hay conexión activa
            if (connection != null && !connection.isClosed()) {
                return connection;
            }
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            String cadena = "jdbc:sqlserver://" + server + ":" + puerto + ";"
                    + "databaseName=" + db + ";"
                    + "encrypt=true;"
                    + "trustServerCertificate=true;"
                    + "loginTimeout=30;";

            System.out.println("Conectando a: " + cadena);
            connection = DriverManager.getConnection(cadena, usuario, contrasena);
            System.out.println("Conexion exitosa a SQL Server");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Driver de SQL Server no encontrado: " + e.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en la conexion " + e.toString());
        }
        return connection;
    }

    public void InsertarDatos(String nombre, String apellido, String direccion, String telefono, String email) {
        if (connection == null) establecerConexion();

        String sql = "insert into Persona (Nombre, Apellido, Direccion, Telefono, Email, Status) values (?,?,?,?,?,?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, nombre);
            pstmt.setString(2, apellido);
            pstmt.setString(3, direccion);
            pstmt.setString(4, telefono);
            pstmt.setString(5, email);
            pstmt.setString(6, "activo");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar datos " + e.toString());
        }
    }

    public void BorrarDatos(int id) {
        if (connection == null) establecerConexion();

        String sql = "delete from Persona where idpersona = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al borrar los datos " + e.toString());
        }
    }

    public void actualizarDatos(int id, String nombre) {
        if (connection == null) establecerConexion();

        String sql = "update Persona set Nombre = ? where idpersona = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, nombre);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar datos " + e.toString());
        }
    }

    public void leerDatos() {
        if (connection == null) establecerConexion();

        String sql = "select * from Persona";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("idpersona"));
                System.out.println("Nombre: " + rs.getString("Nombre"));
                System.out.println("Apellido: " + rs.getString("Apellido"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al leer datos " + e.toString());
        }
    }

    public ObservableList<Cliente> mostrarPersonas() {
        ObservableList<Cliente> lista = FXCollections.observableArrayList();
        if (connection == null) establecerConexion();

        String sql = "SELECT id_cliente, nombre, telefono FROM Cliente";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                lista.add(new Cliente(
                        rs.getInt("id_cliente"),
                        rs.getString("nombre"),
                        rs.getString("telefono")
                ));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar datos " + e.toString());
        }
        return lista;
    }
}