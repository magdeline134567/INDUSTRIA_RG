package besededatos.controllers;

import besededatos.models.*;
import besededatos.config.*;
import besededatos.utils.*;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class ClienteController {

    Conexion conexion = Conexion.getInstance(); // ✅ CORREGIDO

    // ── Solo los campos que existen en el nuevo FXML ──
    @FXML private TextField     txtIdCliente;   // Username (id_cliente)
    @FXML private PasswordField txtTelefono;    // Password (teléfono)
    @FXML private Label         lblMensaje;     // Mensaje error/éxito

    // ── initialize vacío (no hay ComboBox ni TableView en este FXML) ──
    public void initialize() { }

    // ── INICIAR SESIÓN ──
    @FXML
    protected void FnIniciarSesion(ActionEvent event) {
        String username = txtIdCliente.getText().trim();
        String password = txtTelefono.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            mostrarMensaje("Ingrese su usuario y contraseña.", "#cc0000");
            return;
        }

        // Usamos el SessionManager centralizado
        if (SessionManager.getInstance().login(username, password)) {
            mostrarMensaje("¡Bienvenido, " + SessionManager.getInstance().getUsername() + "!", "#0a6c0a");
            abrirDashboardPrincipal();
        } else {
            mostrarMensaje("Usuario o contraseña incorrectos.", "#cc0000");
        }
    }

    // ── Abrir pantalla principal tras login ──
    private void abrirDashboardPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Basededatos/hello-view.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = new Stage();
            stage.setTitle("INDUSTRIA R.G - Sistema de Gestión");
            stage.setMaximized(true);
            stage.setScene(scene);

            // Cerrar ventana de login
            Stage loginStage = (Stage) txtTelefono.getScene().getWindow();
            loginStage.close();

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarMensaje("Error al cargar el sistema: " + e.getMessage(), "#cc0000");
        }
    }

    // ── OLVIDO CONTRASEÑA (REQUERIDO POR FXML) ──
    @FXML
    protected void FnOlvidoContrasena(ActionEvent event) {
        mostrarMensaje("Contacte al admin si olvidó su clave.", "#cc7700");
    }

    // ── Helper mensaje ──
    private void mostrarMensaje(String texto, String color) {
        if (lblMensaje != null) {
            lblMensaje.setText(texto);
            lblMensaje.setStyle("-fx-text-fill: " + color + "; -fx-font-size: 12px;");
        }
    }
}