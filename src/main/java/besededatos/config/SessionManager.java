package besededatos.config;

import besededatos.models.*;


import java.sql.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SessionManager {
    private static SessionManager instance;
    private int userId;
    private String username;
    private String rol;

    private SessionManager() {
        this.rol = "INVITADO"; // Rol por defecto
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public boolean login(String username, String password) {
        // Ajuste FINAL para que coincida con tu tabla 'Usuario'
        String sql = "SELECT id_usuario, usuario, rol " +
                     "FROM Usuario " +
                     "WHERE usuario = ? AND clave = ? AND estado = 'Activo'";
        
        try (Connection conn = Conexion.getInstance().establecerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                this.userId = rs.getInt("id_usuario");
                this.username = rs.getString("usuario");
                this.rol = rs.getString("rol") != null ? rs.getString("rol").toUpperCase() : "USUARIO";
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public void logout() {
        this.userId = 0;
        this.username = null;
        this.rol = "INVITADO";
    }

    public boolean hasAccess(int tabIndex) {
        // Acceso TOTAL si es ADMIN (más flexible)
        if (rol != null && (rol.toUpperCase().contains("ADMIN") || "ADMINISTRADOR".equalsIgnoreCase(rol))) {
            return true;
        }
        
        if (tabIndex == 0) return true; // Todos ven el Dashboard

        switch (rol.toUpperCase()) {
            case "COMPRAS":
                return Arrays.asList(7, 8).contains(tabIndex);
            case "INVENTARIO":
                return Arrays.asList(5, 6).contains(tabIndex);
            case "NOMINA":
                return Arrays.asList(9, 10, 11, 12).contains(tabIndex);
            case "VENTAS":
                return Arrays.asList(23, 1, 2, 13, 14).contains(tabIndex);
            default:
                return false;
        }
    }

    public boolean canEdit(int tabIndex) {
        if (rol != null && rol.toUpperCase().contains("ADMIN")) return true;
        if ("CONTADOR".equalsIgnoreCase(rol)) return false;

        switch (rol.toUpperCase()) {
            case "VENDEDOR":
                // No puede editar en 2, 4, 20
                if (Arrays.asList(2, 4, 20).contains(tabIndex)) return false;
                return hasAccess(tabIndex);
            case "PRODUCCION":
            case "TECNICO":
                // No puede editar en 2, 6
                if (Arrays.asList(2, 6).contains(tabIndex)) return false;
                return hasAccess(tabIndex);
            case "GERENTE":
                return hasAccess(tabIndex); // Gerente puede editar todo lo que ve
            default:
                return false;
        }
    }

    public String getCurrentRol() { return rol; }
    public String getUsername() { return username; }
    public int getUserId() { return userId; }
}
