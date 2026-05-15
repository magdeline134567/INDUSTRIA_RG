package besededatos.config;

import besededatos.models.*;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;

public class Conexion2 {

    private static Conexion2 instancia = null;
    private Connection connection = null;

    String usuario = "sa";
    String contrasena = "1234";
    String db = "INDUSTRIA_RG";
    String server = "localhost";
    String puerto = "1433";

    private Conexion2() {}

    public static Conexion2 getInstance() {
        if (instancia == null) {
            instancia = new Conexion2();
        }
        return instancia;
    }

    public Connection establecerConexion() {
        try {
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
            JOptionPane.showMessageDialog(null, "Driver no encontrado: " + e.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en la conexion: " + e.toString());
        }
        return connection;
    }

    public void insertarDesglose(String codigo, int clienteId, int tipoVentanaId,
                                 double ancho, double alto, double costoMateriales,
                                 double manoObra, double gastosIndirectos, double precioVenta) {
        if (connection == null) establecerConexion();

        String sql = "INSERT INTO Desglose (codigo, cliente_id, tipo_ventana_id, ancho, alto, " +
                "costo_materiales, mano_obra, gastos_indirectos, precio_venta) " +
                "VALUES (?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, codigo);
            pstmt.setInt(2, clienteId);
            pstmt.setInt(3, tipoVentanaId);
            pstmt.setDouble(4, ancho);
            pstmt.setDouble(5, alto);
            pstmt.setDouble(6, costoMateriales);
            pstmt.setDouble(7, manoObra);
            pstmt.setDouble(8, gastosIndirectos);
            pstmt.setDouble(9, precioVenta);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar desglose: " + e.toString());
        }
    }

    public ObservableList<Desglose> obtenerDesgloses() {
        ObservableList<Desglose> lista = FXCollections.observableArrayList();
        if (connection == null) establecerConexion();

        String sql = "SELECT d.codigo, c.nombre AS cliente, tv.nombre AS tipo_ventana, " +
                "d.ancho, d.alto, d.precio_venta FROM Desglose d " +
                "JOIN Cliente c ON d.cliente_id = c.id " +
                "JOIN TipoVentana tv ON d.tipo_ventana_id = tv.id " +
                "ORDER BY d.fecha_registro DESC";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                lista.add(new Desglose(
                        rs.getString("codigo"),
                        rs.getString("cliente"),
                        rs.getString("tipo_ventana"),
                        rs.getDouble("ancho"),
                        rs.getDouble("alto"),
                        rs.getDouble("precio_venta")
                ));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener desgloses: " + e.toString());
        }
        return lista;
    }

    public void insertarCompra(String codigo, int proveedorId, int materialId,
                               LocalDate fechaEntrega, double cantidad,
                               double precioUnitario, String estado) {
        if (connection == null) establecerConexion();

        String sql = "INSERT INTO Compra (codigo, proveedor_id, material_id, fecha_entrega, " +
                "cantidad, precio_unitario, estado) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, codigo);
            pstmt.setInt(2, proveedorId);
            pstmt.setInt(3, materialId);
            pstmt.setDate(4, Date.valueOf(fechaEntrega));
            pstmt.setDouble(5, cantidad);
            pstmt.setDouble(6, precioUnitario);
            pstmt.setString(7, estado);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar compra: " + e.toString());
        }
    }

    public ObservableList<Compra> obtenerCompras() {
        ObservableList<Compra> lista = FXCollections.observableArrayList();
        if (connection == null) establecerConexion();

        String sql = "SELECT c.codigo, p.nombre AS proveedor, m.nombre AS material, " +
                "CONVERT(varchar, c.fecha_entrega, 103) AS fechaEntrega, " +
                "c.cantidad, c.precio_unitario, c.total, c.estado " +
                "FROM Compra c JOIN Proveedor p ON c.proveedor_id = p.id " +
                "JOIN Material m ON c.material_id = m.id " +
                "ORDER BY c.fecha_registro DESC";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                lista.add(new Compra(
                        rs.getString("codigo"),
                        rs.getString("proveedor"),
                        rs.getString("material"),
                        rs.getString("fechaEntrega"),
                        rs.getDouble("cantidad"),
                        rs.getDouble("precio_unitario"),
                        rs.getDouble("total"),
                        rs.getString("estado")
                ));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener compras: " + e.toString());
        }
        return lista;
    }

    public void insertarOrdenTrabajo(String codigo, int clienteId, int desgloseId, int tecnicoId,
                                     LocalDate fechaInicio, LocalDate fechaEstimada,
                                     String estado, String observaciones) {
        if (connection == null) establecerConexion();

        String sql = "INSERT INTO OrdenTrabajo (codigo, cliente_id, desglose_id, tecnico_id, " +
                "fecha_inicio, fecha_estimada, estado, observaciones) VALUES (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, codigo);
            pstmt.setInt(2, clienteId);
            pstmt.setInt(3, desgloseId);
            pstmt.setInt(4, tecnicoId);
            pstmt.setDate(5, Date.valueOf(fechaInicio));
            pstmt.setDate(6, Date.valueOf(fechaEstimada));
            pstmt.setString(7, estado);
            pstmt.setString(8, observaciones);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar orden de trabajo: " + e.toString());
        }
    }

    public ObservableList<OrdenTrabajo> obtenerOrdenesTrabajo() {
        ObservableList<OrdenTrabajo> lista = FXCollections.observableArrayList();
        if (connection == null) establecerConexion();

        String sql = "SELECT o.codigo, c.nombre AS cliente, d.codigo AS desglose, " +
                "CONVERT(varchar, o.fecha_inicio, 103) AS fechaInicio, " +
                "CONVERT(varchar, o.fecha_estimada, 103) AS fechaEstimada, " +
                "o.estado, t.nombre AS tecnico FROM OrdenTrabajo o " +
                "JOIN Cliente c ON o.cliente_id = c.id " +
                "JOIN Desglose d ON o.desglose_id = d.id " +
                "JOIN Tecnico t ON o.tecnico_id = t.id " +
                "ORDER BY o.fecha_registro DESC";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                lista.add(new OrdenTrabajo(
                        rs.getString("codigo"),
                        rs.getString("cliente"),
                        rs.getString("desglose"),
                        rs.getString("fechaInicio"),
                        rs.getString("fechaEstimada"),
                        rs.getString("estado"),
                        rs.getString("tecnico")
                ));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener órdenes de trabajo: " + e.toString());
        }
        return lista;
    }

    public void insertarInstalacion(int ordenTrabajoId, int tecnicoId,
                                    LocalDate fecha, String hora,
                                    String estado, String notas) {
        if (connection == null) establecerConexion();

        String sql = "INSERT INTO Instalacion (orden_trabajo_id, tecnico_id, fecha, hora, estado, notas) " +
                "VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, ordenTrabajoId);
            pstmt.setInt(2, tecnicoId);
            pstmt.setDate(3, Date.valueOf(fecha));
            pstmt.setString(4, hora);
            pstmt.setString(5, estado);
            pstmt.setString(6, notas);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar instalación: " + e.toString());
        }
    }

    public ObservableList<Instalacion> obtenerInstalaciones() {
        ObservableList<Instalacion> lista = FXCollections.observableArrayList();
        if (connection == null) establecerConexion();

        String sql = "SELECT o.codigo AS orden, CONVERT(varchar, i.fecha, 103) AS fecha, " +
                "i.hora, t.nombre AS tecnico, i.estado " +
                "FROM Instalacion i JOIN OrdenTrabajo o ON i.orden_trabajo_id = o.id " +
                "JOIN Tecnico t ON i.tecnico_id = t.id ORDER BY i.fecha DESC";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                lista.add(new Instalacion(
                        rs.getString("orden"),
                        rs.getString("fecha"),
                        rs.getString("hora"),
                        rs.getString("tecnico"),
                        rs.getString("estado")
                ));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener instalaciones: " + e.toString());
        }
        return lista;
    }

    public int obtenerIdGenerico(String sql, String valor) {
        if (connection == null) establecerConexion();

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, valor);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener ID: " + e.toString());
        }
        return -1;
    }

    public ObservableList<String> fillCombo(String sql) {
        ObservableList<String> lista = FXCollections.observableArrayList();
        if (connection == null) establecerConexion();

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) lista.add(rs.getString(1));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar combo: " + e.toString());
        }
        return lista;
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