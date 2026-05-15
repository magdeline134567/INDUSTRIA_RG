package besededatos.controllers;

import besededatos.models.*;
import besededatos.config.*;
import besededatos.utils.*;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuController {

    private MainController mainController;
    private TabPane mainTabPane;
    private Stage primaryStage;

    @FXML private Button btnDashboard, btnDesgloseReg, btnOrdenesReg, btnInventarioReg, btnComprasReg, btnNominaReg, btnContabilidadReg, btnClientesReg, btnTecnicosReg, btnInstalacionesReg, btnUsuariosReg;
    @FXML private Button btnDesgloseCon, btnOrdenesCon, btnInventarioCon, btnComprasCon, btnNominaCon, btnContabilidadCon, btnClientesCon, btnTecnicosCon, btnInstalacionesCon, btnUsuariosCon;
    @FXML private Button btnReportes, btnVentas, btnConfig, btnActividad;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
        aplicarPermisos();
    }

    public void aplicarPermisos() {
        SessionManager sm = SessionManager.getInstance();
        
        checkAccess(btnDashboard, 0);
        checkAccess(btnDesgloseReg, 1);
        checkAccess(btnDesgloseCon, 2);
        checkAccess(btnOrdenesReg, 3);
        checkAccess(btnOrdenesCon, 4);
        checkAccess(btnInventarioReg, 5);
        checkAccess(btnInventarioCon, 6);
        checkAccess(btnComprasReg, 7);
        checkAccess(btnComprasCon, 8);
        checkAccess(btnNominaReg, 9);
        checkAccess(btnNominaCon, 10);
        checkAccess(btnContabilidadReg, 11);
        checkAccess(btnContabilidadCon, 12);
        checkAccess(btnClientesReg, 13);
        checkAccess(btnClientesCon, 14);
        checkAccess(btnTecnicosReg, 15);
        checkAccess(btnTecnicosCon, 16);
        checkAccess(btnUsuariosReg, 17);
        checkAccess(btnUsuariosCon, 18);
        checkAccess(btnInstalacionesReg, 19);
        checkAccess(btnInstalacionesCon, 20);
        checkAccess(btnReportes, 21);
        checkAccess(btnConfig, 22);
        checkAccess(btnVentas, 23);
        checkAccess(btnActividad, 24);
    }

    private void checkAccess(Button btn, int tabIndex) {
        if (btn == null) return;
        boolean hasAccess = SessionManager.getInstance().hasAccess(tabIndex);
        String originalText = btn.getText().replace("🔒 ", "");
        if (!hasAccess) {
            btn.setText("🔒 " + originalText);
            btn.setDisable(true);
            btn.setOpacity(0.6);
        } else {
            btn.setText(originalText);
            btn.setDisable(false);
            btn.setOpacity(1.0);
        }
    }

    public void setMainTabPane(TabPane mainTabPane) {
        this.mainTabPane = mainTabPane;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void activarBoton(ActionEvent event) {
        if (event != null && event.getSource() instanceof Button) {
            Button btnPulsado = (Button) event.getSource();
            
            // 1. Buscamos el contenedor principal de los botones (el VBox que está dentro del ScrollPane)
            // Subimos por el parentesco hasta encontrar el VBox que contiene las secciones
            Node parent = btnPulsado.getParent();
            while (parent != null && !(parent instanceof VBox && parent.getStyleClass().contains("menu-container"))) {
                // Casteamos a VBox para poder usar getChildren()
                if (parent instanceof VBox && ((VBox
                        ) parent).getChildren().contains(btnPulsado)) break;
                parent = parent.getParent();
            }


            if (parent instanceof VBox) {
                VBox menuPrincipal = (VBox) parent;
                // 2. Limpiar TODAS las secciones del menú
                for (Node seccion : menuPrincipal.getChildren()) {
                    if (seccion instanceof VBox) {
                        for (Node hijo : ((VBox) seccion).getChildren()) {
                            if (hijo instanceof Button) {
                                hijo.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-cursor: hand; -fx-alignment: CENTER_LEFT; -fx-padding: 10 20;");
                            }
                        }
                    }
                }
            }

            // 3. Aplicar estilo destacado al botón pulsado
            btnPulsado.setStyle("-fx-background-color: rgba(49, 210, 242, 0.15); " +
                                "-fx-text-fill: #31d2f2; " +
                                "-fx-font-weight: bold; " +
                                "-fx-border-color: #31d2f2; " +
                                "-fx-border-width: 0 0 0 4; " +
                                "-fx-alignment: CENTER_LEFT; " +
                                "-fx-padding: 10 20;");
        }
    }


    // ==================== SECCIÓN 1: PRINCIPAL ====================
    @FXML public void irADashboard(ActionEvent event) { if (mainController != null) { mainController.navegarA(0, event); activarBoton(event); } }
    @FXML public void irADesgloseRegistro(ActionEvent event) { if (mainController != null) { mainController.navegarA(1, event); activarBoton(event); } }
    @FXML public void irAOrdenesRegistro(ActionEvent event) { if (mainController != null) { mainController.navegarA(3, event); activarBoton(event); } }
    @FXML public void irAInstalacionesRegistro(ActionEvent event) { if (mainController != null) { mainController.navegarA(19, event); activarBoton(event); } }
    @FXML public void irAInventarioRegistro(ActionEvent event) { if (mainController != null) { mainController.navegarA(5, event); activarBoton(event); } }

    // ==================== SECCIÓN 2: ADMINISTRACIÓN ====================
    @FXML public void irAComprasRegistro(ActionEvent event) { if (mainController != null) { mainController.navegarA(7, event); activarBoton(event); } }
    @FXML public void irANominaRegistro(ActionEvent event) { if (mainController != null) { mainController.navegarA(9, event); activarBoton(event); } }
    @FXML public void irAContabilidadRegistro(ActionEvent event) { if (mainController != null) { mainController.navegarA(11, event); activarBoton(event); } }
    @FXML public void irAVentas(ActionEvent event) { if (mainController != null) { mainController.navegarA(23, event); activarBoton(event); } }

    // ==================== SECCIÓN 3: ENTIDADES ====================
    @FXML public void irAClientesRegistro(ActionEvent event) { if (mainController != null) { mainController.navegarA(13, event); activarBoton(event); } }
    @FXML public void irATecnicosRegistro(ActionEvent event) { if (mainController != null) { mainController.navegarA(15, event); activarBoton(event); } }
    @FXML public void irAUsuariosRegistro(ActionEvent event) { if (mainController != null) { mainController.navegarA(17, event); activarBoton(event); } }

    // ==================== SECCIÓN 4: SISTEMA ====================
    @FXML public void irAReportes(ActionEvent event) { if (mainController != null) { mainController.navegarA(21, event); activarBoton(event); } }
    @FXML public void irAConfiguracion(ActionEvent event) { if (mainController != null) { mainController.navegarA(22, event); activarBoton(event); } }
    @FXML public void irAActividad(ActionEvent event) { if (mainController != null) { mainController.navegarA(24, event); activarBoton(event); } }

    // ==================== SECCIÓN 5: CONSULTAS ====================
    @FXML public void irADesglose(ActionEvent event) { if (mainController != null) { mainController.navegarA(2, event); activarBoton(event); } }
    @FXML public void irAOrdenes(ActionEvent event) { if (mainController != null) { mainController.navegarA(4, event); activarBoton(event); } }
    @FXML public void irAInventario(ActionEvent event) { if (mainController != null) { mainController.navegarA(6, event); activarBoton(event); } }
    @FXML public void irACompras(ActionEvent event) { if (mainController != null) { mainController.navegarA(8, event); activarBoton(event); } }
    @FXML public void irANomina(ActionEvent event) { if (mainController != null) { mainController.navegarA(10, event); activarBoton(event); } }
    @FXML public void irAContabilidad(ActionEvent event) { if (mainController != null) { mainController.navegarA(12, event); activarBoton(event); } }
    @FXML public void irAClientes(ActionEvent event) { if (mainController != null) { mainController.navegarA(14, event); activarBoton(event); } }
    @FXML public void irATecnicos(ActionEvent event) { if (mainController != null) { mainController.navegarA(16, event); activarBoton(event); } }
    @FXML public void irAUsuarios(ActionEvent event) { if (mainController != null) { mainController.navegarA(18, event); activarBoton(event); } }
    @FXML public void irAInstalaciones(ActionEvent event) { if (mainController != null) { mainController.navegarA(20, event); activarBoton(event); } }



}
