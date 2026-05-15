package besededatos.controllers;

import besededatos.models.*;
import besededatos.config.*;
import besededatos.utils.*;


import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.AreaChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.chart.XYChart;
import javafx.geometry.Insets;
import javafx.scene.Scene;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.*;
import javax.swing.JOptionPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.application.Platform;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;


public class MainController {

    private Conexion conexion = Conexion.getInstance();
    private static SessionManager sessionManager = SessionManager.getInstance();

    @FXML private Label lblVentasTotales, lblUtilidadNeta, lblOrdenesTotales, lblClientesActivos, lblEficiencia;
    @FXML private Label lblVentasTrend, lblOrdenesTrend, lblFechaActualizacion;

    @FXML private TabPane mainTabPane;

    private void activarBoton(ActionEvent event) {
        if (event != null && event.getSource() instanceof Button) {
            Button btn = (Button) event.getSource();
            if (btn.getParent() instanceof FlowPane) {
                FlowPane navBar = (FlowPane) btn.getParent();
                for (Node n : navBar.getChildren()) {
                    if (n instanceof Button) {
                        n.setStyle("-fx-text-fill: white; -fx-background-color: transparent; -fx-background-radius: 8; -fx-padding: 8 12;");
                    }
                }
            }
            btn.setStyle("-fx-background-color: #ffffff22; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 8 12;");
        }
    }

    @FXML public void FnIniciarSesion(ActionEvent event) { irADashboard(null); }
    @FXML public void irALogin(ActionEvent event) { /* Lógica login */ }

    // MÉTODO MAESTRO DE NAVEGACIÓN CON SEGURIDAD (Público para el menú lateral)
    public void navegarA(int index, ActionEvent event) {
        if (mainTabPane == null) return;
        
        if (sessionManager.hasAccess(index)) {
            mainTabPane.getSelectionModel().select(index);
        } else {
            // Enviamos a la pestaña 25 (Acceso Denegado)
            mainTabPane.getSelectionModel().select(25);
        }
        // Solo activamos botón si el evento viene de un botón que NO es del menú lateral
        // (El menú lateral ya gestiona sus propios estilos)
        if (event != null && event.getSource() instanceof Button) {
            Button btn = (Button) event.getSource();
            if (btn.getParent() instanceof FlowPane) {
                activarBoton(event);
            }
        }
    }




    // ==================== NAVEGACIÓN PRINCIPAL ====================
    @FXML public void irADashboard(ActionEvent event) { 
        navegarA(0, event); 
    }
    @FXML public void irADesgloseRegistro(ActionEvent event) { navegarA(1, event); }
    @FXML public void irAOrdenesRegistro(ActionEvent event) { 
        navegarA(3, event);
        // Recargar técnicos al cambiar a pestaña de órdenes
        cargarTecnicosEnOrdenes();
    }
    @FXML public void irAInventarioRegistro(ActionEvent event) { navegarA(5, event); }
    @FXML public void irAComprasRegistro(ActionEvent event) { navegarA(7, event); }
    @FXML public void irANominaRegistro(ActionEvent event) { navegarA(9, event); }
    @FXML public void irAContabilidadRegistro(ActionEvent event) { navegarA(11, event); }
    @FXML public void irAClientesRegistro(ActionEvent event) { navegarA(13, event); }
    @FXML public void irATecnicosRegistro(ActionEvent event) { navegarA(15, event); }
    @FXML public void irAUsuariosRegistro(ActionEvent event) { navegarA(17, event); }
    @FXML public void irAInstalacionesRegistro(ActionEvent event) { navegarA(19, event); }

    @FXML public void irADesglose(ActionEvent event) { navegarA(2, event); }
    @FXML public void irAOrdenes(ActionEvent event) { navegarA(4, event); }
    @FXML public void irAInventario(ActionEvent event) { navegarA(6, event); }
    @FXML public void irACompras(ActionEvent event) { navegarA(8, event); }
    @FXML public void irANomina(ActionEvent event) { navegarA(10, event); }
    @FXML public void irAContabilidad(ActionEvent event) { navegarA(12, event); }
    @FXML public void irAClientes(ActionEvent event) { navegarA(14, event); }
    @FXML public void irATecnicos(ActionEvent event) { navegarA(16, event); }
    @FXML public void irAUsuarios(ActionEvent event) { navegarA(18, event); }
    @FXML public void irAInstalaciones(ActionEvent event) { navegarA(20, event); }


    @FXML void irADesgloseConsulta()      { mainTabPane.getSelectionModel().select(2); }
    @FXML void irAOrdenesConsulta()       { mainTabPane.getSelectionModel().select(4); }
    @FXML void irAInventarioConsulta()    { mainTabPane.getSelectionModel().select(6); }
    @FXML void irAComprasConsulta()       { mainTabPane.getSelectionModel().select(8); }
    @FXML void irANominaConsulta()        { mainTabPane.getSelectionModel().select(10); }
    @FXML void irAContabilidadConsulta()  { mainTabPane.getSelectionModel().select(12); }
    @FXML void irAClientesConsulta()      { mainTabPane.getSelectionModel().select(14); }
    @FXML void irATecnicosConsulta()      { mainTabPane.getSelectionModel().select(16); }
    @FXML void irAUsuariosConsulta()      { mainTabPane.getSelectionModel().select(18); }
    @FXML void irAInstalacionesConsulta() { mainTabPane.getSelectionModel().select(20); }

    @FXML public void irAReportes(ActionEvent event) { if (mainTabPane != null) mainTabPane.getSelectionModel().select(21); activarBoton(event); }
    @FXML public void irAConfiguracion(ActionEvent event) { if (mainTabPane != null) mainTabPane.getSelectionModel().select(22); activarBoton(event); }
    @FXML public void irAVentas(ActionEvent event) { if (mainTabPane != null) mainTabPane.getSelectionModel().select(23); activarBoton(event); }
    @FXML public void irAActividad(ActionEvent event) { if (mainTabPane != null) mainTabPane.getSelectionModel().select(24); activarBoton(event); }

    // ==================== VARIABLES FX ====================
    @FXML private PieChart chartOrdenesPie;
    @FXML private BarChart<String, Number> chartIngresosBar;
    @FXML private LineChart<String, Number> chartVentasLine;
    @FXML private AreaChart<String, Number> chartOrdenesMesArea;
    @FXML private BarChart<String, Number> chartTopProductosBar;
    @FXML private PieChart chartCategoriasPie;

    // Labels para contadores de órdenes
    @FXML private Label lblPendientes, lblEnProduccion, lblListasInstalar, lblInstaladas;

    @FXML private ComboBox<String> cmbDesgloseExistente, cmbCategoriaDesglose;
    @FXML private TextField txtAncho, txtAlto, txtCostoMateriales, txtManoObra, txtGastosIndirectos, txtPrecioVenta;
    @FXML private Label lblArea, lblPerimetro, lblCostoTotal, lblMargen;
    @FXML private ComboBox<String> cmbCliente, cmbTipoVentana, cmbTipoPerfil, cmbGrosor, cmbTipoVidrio, cmbEspesor, cmbHerrajes, cmbSellador, cmbAcabado;
    @FXML private Spinner<Integer> spnCantHojas;
    @FXML private TableView<Desglose> tableView;
    @FXML private TableColumn<Desglose, String> colCodigo, colCliente, colTipoVentana;
    @FXML private TableColumn<Desglose, Double> colAncho, colAlto, colPrecioVenta;

    @FXML private ComboBox<String> cmbCategoriaCompra;
    @FXML private TextField txtCantidadCompra, txtPrecioUnitario, txtBuscarCompra;
    @FXML private DatePicker dpFechaEntrega;
    @FXML private Label lblTotal;
    @FXML private ComboBox<String> cmbProveedor, cmbMaterial, cmbEstado;
    @FXML private TableView<Compra> tableViewCompras;
    @FXML private TableColumn<Compra, String> colCodigoCompra, colProveedor, colMaterial, colFechaEntrega, colEstadoCompra;
    @FXML private TableColumn<Compra, Number> colCantidadCompra, colPrecioUnitarioCompra, colTotalCompra;

    @FXML private ComboBox<String> cmbOrdenExistente, cmbCategoriaOrden;
    @FXML private TextField txtCodigoOrden;
    @FXML private TextArea txtObservaciones;
    @FXML private DatePicker dpFechaInicio, dpFechaEstimada;
    @FXML private ComboBox<String> cmbClienteOrden, cmbDesglose, cmbTecnico, cmbEstadoOrden;
    @FXML private TableView<OrdenTrabajo> tableViewOrdenes;
    @FXML private TableColumn<OrdenTrabajo, String> colCodigoOrden, colClienteOrden, colDesglose, colFechaInicio, colFechaEstimada, colEstadoOrden, colTecnico;

    @FXML private ComboBox<String> cmbOrdenTrabajo, cmbTecnicoInst, cmbEstadoInst, cmbHora;
    @FXML private DatePicker dpFechaInst;
    @FXML private TextArea taNotas;
    @FXML private TableView<Instalacion> tableViewInstalaciones;
    @FXML private TableColumn<Instalacion, String> colOrden, colFecha, colHora, colTecnicoInst, colEstadoInst;

    @FXML private ComboBox<String> cmbInvMaterial, cmbInvCategoria;
    @FXML private TextField txtInvCantidad, txtInvCostoUnit, txtInvPrecioUnit, txtInvUbicacion;
    @FXML private TableView<InventarioItem> tableViewInventario;
    @FXML private TableColumn<InventarioItem, Integer> colInvId;
    @FXML private TableColumn<InventarioItem, String> colInvNombre, colInvCategoria, colInvUbicacion;
    @FXML private TableColumn<InventarioItem, Integer> colInvCantidad;
    @FXML private TableColumn<InventarioItem, Double> colInvCosto, colInvPrecio;

    @FXML private TextField txtNomEmpleado, txtNomPuesto, txtNomSalarioBase, txtNomHorasExtras, txtNomDeducciones;
    @FXML private TableView<NominaItem> tableViewNomina;
    @FXML private TableColumn<NominaItem, Integer> colNomId;
    @FXML private TableColumn<NominaItem, String> colNomEmpleado, colNomPuesto;
    @FXML private TableColumn<NominaItem, Double> colNomSalarioBase, colNomHorasExtras, colNomDeducciones, colNomSalarioNeto;
    @FXML private Label lblNomSalarioNeto;

    @FXML private DatePicker dpContFecha;
    @FXML private ComboBox<String> cmbContTipo;
    @FXML private TextField txtContSubTipo, txtContDesc, txtContMonto;
    @FXML private Label lblTotalIngresos, lblTotalGastos, lblBalance;
    @FXML private TableView<MovimientoContable> tableViewContabilidad;
    @FXML private TableColumn<MovimientoContable, Integer> colContId;
    @FXML private TableColumn<MovimientoContable, String> colContFecha, colContTipo, colContSubTipo, colContDesc;
    @FXML private TableColumn<MovimientoContable, Double> colContMonto;

    // CLIENTES
    @FXML private TextField txtCliNombre, txtCliCedula, txtCliTelefono, txtCliEmail, txtCliDireccion;
    @FXML private ComboBox<String> cmbCliTipo;
    @FXML private TableView<ClienteCompleto> tableViewClientes;
    @FXML private TableColumn<ClienteCompleto, Integer> colCliId;
    @FXML private TableColumn<ClienteCompleto, String> colCliNombre, colCliCedula, colCliTelefono, colCliEmail, colCliTipo;

    @FXML private TextField txtTecNombre, txtTecCalificacion, txtTecTelefono;
    @FXML private ComboBox<String> cmbTecEspecialidad, cmbTecDisponibilidad;
    @FXML private TableView<TecnicoCompleto> tableViewTecnicos;
    @FXML private TableColumn<TecnicoCompleto, Integer> colTecId;
    @FXML private TableColumn<TecnicoCompleto, String> colTecNombre, colTecEspecialidad, colTecCalificacion, colTecTelefono, colTecDisponibilidad;

    // ACTIVIDAD DEL SISTEMA
    @FXML private VBox contenedorActividad;
    @FXML private Label lblTotalActividades;

    // FOOTER
    @FXML private Label lblHoraRD;
    @FXML private Label lblUltimaConexion;

    // Variable para el Timeline
    private javafx.animation.Timeline horaTimeline;

    @FXML private TextField txtConfClave, txtConfValor, txtConfDesc;
    @FXML private TableView<ConfiguracionItem> tableViewConfiguracion;
    @FXML private TableColumn<ConfiguracionItem, Integer> colConfId;
    @FXML private TableColumn<ConfiguracionItem, String> colConfClave, colConfValor, colConfDesc;

    @FXML private TextField txtUsuarioNombre, txtUsuarioUsername, txtUsuarioEmail;
    @FXML private PasswordField txtUsuarioPassword;
    @FXML private ComboBox<String> cmbUsuarioRol, cmbUsuarioDepto;
    @FXML private TableView<Usuario> tableViewUsuarios;
    @FXML private TableColumn<Usuario, Integer> colUsuarioId;
    @FXML private TableColumn<Usuario, String> colUsuarioUsername, colUsuarioNombre, colUsuarioEmail, colUsuarioRol, colUsuarioDepto, colUsuarioUltimoAcceso, colUsuarioEstado;

    @FXML private CheckBox chkFactura, chkDesglose, chkVentas;

    @FXML private PieChart chartReportesPie;
    @FXML private BarChart<String, Number> chartReportesBar;

    // VENTAS
    @FXML private ComboBox<String> cmbVentasCliente, cmbVentasTipo;
    @FXML private DatePicker dpVentasValido;

    @FXML private MenuController sideMenuController;

    @FXML
    public void FnGenerarCotizacion(ActionEvent event) {
        if (cmbVentasCliente.getValue() == null || cmbVentasTipo.getValue() == null || dpVentasValido.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText("Por favor complete todos los campos de la cotización.");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText("Cotización generada exitosamente para " + cmbVentasCliente.getValue());
        alert.showAndWait();
        
        cmbVentasCliente.setValue(null);
        cmbVentasTipo.setValue(null);
        dpVentasValido.setValue(null);
    }

    // Variable para almacenar el ID del registro seleccionado en cada módulo
    private int selectedClienteId = -1;
    private int selectedTecnicoId = -1;
    private int selectedInstalacionId = -1;
    private int selectedUsuarioId = -1;
    private int selectedNominaId = -1;
    private int selectedMovimientoId = -1;

    // ==================== MÉTODOS AUXILIARES ====================
    private ObservableList<String> fillCombo(String sql) {
        ObservableList<String> list = FXCollections.observableArrayList();
        Connection conn = conexion.establecerConexion();
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) list.add(rs.getString(1));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    private double parseDouble(String val) {
        if (val == null || val.trim().isEmpty()) return 0.0;
        try { return Double.parseDouble(val.trim().replace(",", ".")); } catch (Exception e) { return 0.0; }
    }

    private int obtenerIdGenerico(String sql, String valor) {
        Connection conn = conexion.establecerConexion();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, valor);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    // ==================== INITIALIZE ====================
    @FXML
    public void initialize() {
        if (sideMenuController != null) {
            sideMenuController.setMainController(this);
            sideMenuController.setMainTabPane(mainTabPane);
        }

        // Inicializar footer con hora en tiempo real
        initFooter();

        initDashboardCharts();
        initDesglose();
        initCompras();
        initOrdenes();
        initInstalaciones();
        initInventario();
        initNomina();
        initContabilidad();
        initClientes();
        initTecnicos();
        initConfiguracion();
        initUsuarios();
        actualizarContadoresOrdenes();
        aplicarPermisos();

        // Cargar actividades automáticamente
        javafx.application.Platform.runLater(() -> cargarActividades());
    }

    private void actualizarContadoresOrdenes() {
        Connection conn = conexion.establecerConexion();
        try (Statement stmt = conn.createStatement()) {
            int pendientes = 0, enProduccion = 0, listasInstalar = 0, instaladas = 0;
            
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM OrdenTrabajo WHERE estado = 'Pendiente'");
            if (rs.next()) pendientes = rs.getInt(1);
            
            rs = stmt.executeQuery("SELECT COUNT(*) FROM OrdenTrabajo WHERE estado = 'En Proceso'");
            if (rs.next()) enProduccion = rs.getInt(1);
            
            rs = stmt.executeQuery("SELECT COUNT(*) FROM OrdenTrabajo WHERE estado = 'Lista Instalar'");
            if (rs.next()) listasInstalar = rs.getInt(1);
            
            rs = stmt.executeQuery("SELECT COUNT(*) FROM OrdenTrabajo WHERE estado = 'Completada'");
            if (rs.next()) instaladas = rs.getInt(1);
            
            if (lblPendientes != null) lblPendientes.setText(String.valueOf(pendientes));
            if (lblEnProduccion != null) lblEnProduccion.setText(String.valueOf(enProduccion));
            if (lblListasInstalar != null) lblListasInstalar.setText(String.valueOf(listasInstalar));
            if (lblInstaladas != null) lblInstaladas.setText(String.valueOf(instaladas));
            
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar contadores: " + e.getMessage());
        }
    }
    
    private void aplicarPermisos() {
        if (mainTabPane == null) return;
        for (int i = 0; i < mainTabPane.getTabs().size(); i++) {
            Tab tab = mainTabPane.getTabs().get(i);
            String originalTitle = tab.getText().replace("🔒 ", "");
            if (!sessionManager.hasAccess(i)) {
                tab.setText("🔒 " + originalTitle);
                tab.setDisable(true);
            } else {
                tab.setText(originalTitle);
                tab.setDisable(false);
            }
        }
    }

    // ==================== DASHBOARD ====================

    // ==================== DESGLOSE ====================
    private void initDesglose() {
        if (spnCantHojas != null) spnCantHojas.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1));
        
        // Cargar combobox de desgloses existentes
        if (cmbDesgloseExistente != null) {
            ObservableList<String> desgloses = FXCollections.observableArrayList();
            String sql = "SELECT codigo FROM Desglose ORDER BY codigo";
            Connection conn = conexion.establecerConexion();
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) desgloses.add(rs.getString("codigo"));
                cmbDesgloseExistente.setItems(desgloses);
                System.out.println("✅ Cargados " + desgloses.size() + " desgloses en cmbDesgloseExistente");
            } catch (SQLException e) {
                System.err.println("❌ Error al cargar desgloses: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        // Cargar combobox de categorías para filtrar
        if (cmbCategoriaDesglose != null) {
            ObservableList<String> categorias = FXCollections.observableArrayList();
            categorias.addAll("Todos", "Ventana Corrediza", "Ventana Fija", "Puerta Balcon", "Ventana Abatible", "Ventana Guillotina");
            cmbCategoriaDesglose.setItems(categorias);
            System.out.println("✅ Cargadas " + categorias.size() + " categorías en cmbCategoriaDesglose");
            
            // Agregar listener para filtrar desgloses por tipo
            cmbCategoriaDesglose.setOnAction(event -> {
                String categoriaSeleccionada = cmbCategoriaDesglose.getValue();
                if (categoriaSeleccionada != null && cmbDesgloseExistente != null) {
                    filtrarDesglosesPorTipo(categoriaSeleccionada);
                }
            });
        }
        
        if (cmbCliente != null) cmbCliente.setItems(fillCombo("SELECT nombre FROM Cliente ORDER BY nombre"));
        if (cmbTipoVentana != null) cmbTipoVentana.setItems(fillCombo("SELECT nombre FROM TipoVentana ORDER BY nombre"));
        // ComboBoxes con valores fijos (no tienen tabla propia)
        if (cmbTipoPerfil != null) cmbTipoPerfil.setItems(FXCollections.observableArrayList("Aluminio Nat.", "Aluminio Blanco", "Aluminio Negro", "PVC Blanco", "PVC Gris"));
        if (cmbGrosor != null) cmbGrosor.setItems(FXCollections.observableArrayList("1.2 mm", "1.5 mm", "2.0 mm", "2.5 mm", "3.0 mm"));
        if (cmbTipoVidrio != null) cmbTipoVidrio.setItems(FXCollections.observableArrayList("Claro", "Bronce", "Gris", "Reflectivo", "Esmerilado", "Templado"));
        if (cmbEspesor != null) cmbEspesor.setItems(FXCollections.observableArrayList("4 mm", "5 mm", "6 mm", "8 mm", "10 mm"));
        if (cmbHerrajes != null) cmbHerrajes.setItems(FXCollections.observableArrayList("Básico", "Estándar", "Premium", "Seguridad"));
        if (cmbSellador != null) cmbSellador.setItems(FXCollections.observableArrayList("Silicona Blanca", "Silicona Gris", "Poliuretano", "Acrílico"));
        if (cmbAcabado != null) cmbAcabado.setItems(FXCollections.observableArrayList("Natural", "Anodizado", "Lacado Blanco", "Lacado Negro", "Wood Effect"));
        if (tableView != null) {
            colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
            colCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
            colTipoVentana.setCellValueFactory(new PropertyValueFactory<>("tipoVentana"));
            colAncho.setCellValueFactory(new PropertyValueFactory<>("ancho"));
            colAlto.setCellValueFactory(new PropertyValueFactory<>("alto"));
            colPrecioVenta.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));
            cargarDesgloses();
        }
    }
    
    private void filtrarDesglosesPorTipo(String tipo) {
        Connection conn = conexion.establecerConexion();
        try (Statement stmt = conn.createStatement()) {
            String sql;
            if ("Todos".equals(tipo)) {
                sql = "SELECT codigo FROM Desglose ORDER BY codigo";
            } else {
                sql = "SELECT codigo FROM Desglose WHERE tipo_ventana = '" + tipo + "' ORDER BY codigo";
            }
            
            ResultSet rs = stmt.executeQuery(sql);
            ObservableList<String> desglosesFiltrados = FXCollections.observableArrayList();
            
            while (rs.next()) {
                desglosesFiltrados.add(rs.getString("codigo"));
            }
            
            cmbDesgloseExistente.setItems(desglosesFiltrados);
            
            if (desglosesFiltrados.isEmpty()) {
                System.out.println("⚠️ No hay desgloses del tipo: " + tipo);
            } else {
                System.out.println("✅ Filtrados " + desglosesFiltrados.size() + " desgloses para el tipo: " + tipo);
            }
            
            rs.close();
        } catch (SQLException e) {
            System.err.println("❌ Error al filtrar desgloses por tipo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void cargarDesgloses() {
        ObservableList<Desglose> lista = FXCollections.observableArrayList();
        String sql = "SELECT d.codigo, c.nombre, tv.nombre, d.ancho, d.alto, d.precio_venta " +
                     "FROM Desglose d " +
                     "JOIN Cliente c ON d.cliente_id = c.id_cliente " +
                     "JOIN TipoVentana tv ON d.tipo_ventana_id = tv.id_tipo";
        Connection conn = conexion.establecerConexion();
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) lista.add(new Desglose(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getDouble(5), rs.getDouble(6)));
            tableView.setItems(lista);
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @FXML protected void FnGuardarDesglose(ActionEvent event) {
        // Validaciones básicas
        if (cmbCliente == null || cmbCliente.getValue() == null) { JOptionPane.showMessageDialog(null, "Debe seleccionar un Cliente."); return; }
        if (cmbTipoVentana == null || cmbTipoVentana.getValue() == null) { JOptionPane.showMessageDialog(null, "Debe seleccionar el Tipo de Ventana."); return; }
        if (txtAncho == null || txtAncho.getText().trim().isEmpty()) { JOptionPane.showMessageDialog(null, "El campo Ancho es obligatorio."); return; }
        if (txtAlto == null || txtAlto.getText().trim().isEmpty()) { JOptionPane.showMessageDialog(null, "El campo Alto es obligatorio."); return; }

        double ancho, alto, costo, mano, gastos, precioVenta;
        try {
            ancho = parseDouble(txtAncho.getText());
            alto  = parseDouble(txtAlto.getText());
            costo = parseDouble(txtCostoMateriales != null ? txtCostoMateriales.getText() : "0");
            mano  = parseDouble(txtManoObra != null ? txtManoObra.getText() : "0");
            gastos = parseDouble(txtGastosIndirectos != null ? txtGastosIndirectos.getText() : "0");
            precioVenta = parseDouble(txtPrecioVenta != null ? txtPrecioVenta.getText() : "0");
            if (precioVenta == 0) precioVenta = costo + mano + gastos;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Los campos numéricos (Ancho, Alto, Costos) deben ser números válidos.");
            return;
        }

        // Obtener IDs del cliente y tipo ventana por separado
        int clienteId = -1, tipoVentanaId = -1;
        Connection conn = conexion.establecerConexion();
        
        // Obtener ID del cliente
        try (PreparedStatement ps = conn.prepareStatement("SELECT id_cliente FROM Cliente WHERE nombre = ?")) {
            ps.setString(1, cmbCliente.getValue());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) { clienteId = rs.getInt(1); }
        } catch (SQLException e) { 
            JOptionPane.showMessageDialog(null, "Error al obtener ID del cliente: " + e.getMessage()); 
            e.printStackTrace();
            return; 
        }
        
        // Obtener ID del tipo de ventana
        try (PreparedStatement ps = conn.prepareStatement("SELECT id_tipo FROM TipoVentana WHERE nombre = ?")) {
            ps.setString(1, cmbTipoVentana.getValue());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) { tipoVentanaId = rs.getInt(1); }
        } catch (SQLException e) { 
            JOptionPane.showMessageDialog(null, "Error al obtener ID del tipo de ventana: " + e.getMessage()); 
            e.printStackTrace();
            return; 
        }

        if (clienteId == -1 || tipoVentanaId == -1) { 
            JOptionPane.showMessageDialog(null, "No se encontraron los IDs. Cliente ID: " + clienteId + ", TipoVentana ID: " + tipoVentanaId); 
            return; 
        }

        // Generar código automáticamente si no se selecciona uno existente
        String codigo;
        if (cmbDesgloseExistente != null && cmbDesgloseExistente.getValue() != null) {
            codigo = cmbDesgloseExistente.getValue();
        } else {
            // Generar código automático: VEN-YYYY-XXX
            try {
                int year = java.time.Year.now().getValue();
                String sqlCount = "SELECT COUNT(*) FROM Desglose WHERE codigo LIKE 'VEN-" + year + "%'";
                int count = 0;
                try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sqlCount)) {
                    if (rs.next()) count = rs.getInt(1);
                }
                codigo = String.format("VEN-%d-%03d", year, count + 1);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al generar código: " + e.getMessage());
                return;
            }
        }

        // Insertar en la tabla Desglose (columnas confirmadas en BD)
        String sql = "INSERT INTO Desglose (codigo, cliente_id, tipo_ventana_id, ancho, alto, area, perimetro, costo_materiales, mano_obra, gastos_indirectos, precio_venta, margen_ganancia) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection con = conexion.establecerConexion();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            double area = ancho * alto;
            double perim = 2 * (ancho + alto);
            double costoTotal = costo + mano + gastos;
            double margen = costoTotal > 0 ? ((precioVenta - costoTotal) / precioVenta) * 100 : 0;
            ps.setString(1, codigo);
            ps.setInt(2, clienteId);
            ps.setInt(3, tipoVentanaId);
            ps.setDouble(4, ancho);
            ps.setDouble(5, alto);
            ps.setDouble(6, area);
            ps.setDouble(7, perim);
            ps.setDouble(8, costo);
            ps.setDouble(9, mano);
            ps.setDouble(10, gastos);
            ps.setDouble(11, precioVenta);
            ps.setDouble(12, margen);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "✅ Desglose '" + codigo + "' guardado correctamente.");
                registrarActividad("Desglose guardado: " + codigo + " | Cliente: " + cmbCliente.getValue(), getUsuarioActual());
                limpiarDesglose();
                cargarDesgloses();
                if (mainTabPane != null) mainTabPane.getSelectionModel().select(2);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar el desglose: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML protected void FnNuevoDesglose(ActionEvent event) { limpiarDesglose(); }

    @FXML protected void FnEditarDesglose(ActionEvent event) {
        Desglose sel = tableView != null ? tableView.getSelectionModel().getSelectedItem() : null;
        if (sel == null) { JOptionPane.showMessageDialog(null, "Seleccione un desglose de la tabla para editar."); return; }
        // Navegar al formulario y cargar datos
        if (mainTabPane != null) mainTabPane.getSelectionModel().select(1);
        if (cmbDesgloseExistente != null) cmbDesgloseExistente.setValue(sel.getCodigo());
        if (cmbCliente != null) cmbCliente.setValue(sel.getCliente());
        if (cmbTipoVentana != null) cmbTipoVentana.setValue(sel.getTipoVentana());
        if (txtAncho != null) txtAncho.setText(String.valueOf(sel.getAncho()));
        if (txtAlto != null) txtAlto.setText(String.valueOf(sel.getAlto()));
        if (txtPrecioVenta != null) txtPrecioVenta.setText(String.valueOf(sel.getPrecioVenta()));
    }

    @FXML protected void FnBorrarDesglose(ActionEvent event) {
        Desglose sel = tableView != null ? tableView.getSelectionModel().getSelectedItem() : null;
        if (sel == null) { JOptionPane.showMessageDialog(null, "Seleccione un desglose de la tabla para borrar."); return; }
        int confirm = JOptionPane.showConfirmDialog(null, "¿Eliminar el desglose '" + sel.getCodigo() + "'?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        Connection conn = conexion.establecerConexion();
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM Desglose WHERE codigo = ?")) {
            ps.setString(1, sel.getCodigo());
            if (ps.executeUpdate() > 0) { JOptionPane.showMessageDialog(null, "Desglose eliminado."); cargarDesgloses(); }
        } catch (SQLException e) { JOptionPane.showMessageDialog(null, "Error al borrar: " + e.getMessage()); }
    }

    @FXML protected void SeleccionarDesglose(MouseEvent event) {
        if (tableView == null) return;
        Desglose sel = tableView.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        if (cmbDesgloseExistente != null) cmbDesgloseExistente.setValue(sel.getCodigo());
        if (cmbCliente != null) cmbCliente.setValue(sel.getCliente());
        if (cmbTipoVentana != null) cmbTipoVentana.setValue(sel.getTipoVentana());
        if (txtAncho != null) txtAncho.setText(String.valueOf(sel.getAncho()));
        if (txtAlto != null) txtAlto.setText(String.valueOf(sel.getAlto()));
        if (txtPrecioVenta != null) txtPrecioVenta.setText(String.valueOf(sel.getPrecioVenta()));
        // Calcular labels
        double area = sel.getAncho() * sel.getAlto();
        double perim = 2 * (sel.getAncho() + sel.getAlto());
        if (lblArea != null) lblArea.setText(String.format("%.2f m²", area));
        if (lblPerimetro != null) lblPerimetro.setText(String.format("%.2f m", perim));
    }

    @FXML protected void FnCalcular() {
        try {
            double ancho = txtAncho != null && !txtAncho.getText().isEmpty() ? Double.parseDouble(txtAncho.getText().trim()) : 0;
            double alto  = txtAlto != null && !txtAlto.getText().isEmpty() ? Double.parseDouble(txtAlto.getText().trim()) : 0;
            double costo = txtCostoMateriales != null && !txtCostoMateriales.getText().isEmpty() ? Double.parseDouble(txtCostoMateriales.getText().trim()) : 0;
            double mano  = txtManoObra != null && !txtManoObra.getText().isEmpty() ? Double.parseDouble(txtManoObra.getText().trim()) : 0;
            double gastos = txtGastosIndirectos != null && !txtGastosIndirectos.getText().isEmpty() ? Double.parseDouble(txtGastosIndirectos.getText().trim()) : 0;
            double area = ancho * alto;
            double perim = 2 * (ancho + alto);
            double costoTotal = costo + mano + gastos;
            double precioV = costoTotal > 0 ? costoTotal * 1.30 : 0; // 30% margen
            double margen = costoTotal > 0 ? ((precioV - costoTotal) / precioV) * 100 : 0;
            if (lblArea != null) lblArea.setText(String.format("%.2f m²", area));
            if (lblPerimetro != null) lblPerimetro.setText(String.format("%.2f m", perim));
            if (lblCostoTotal != null) lblCostoTotal.setText(String.format("%.2f", costoTotal));
            if (lblMargen != null) lblMargen.setText(String.format("%.1f%%", margen));
            if (txtPrecioVenta != null && txtPrecioVenta.getText().isEmpty()) txtPrecioVenta.setText(String.format("%.2f", precioV));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Ingrese valores numéricos válidos en Ancho, Alto y Costos.");
        }
    }

    private void limpiarDesglose() {
        if (cmbDesgloseExistente != null) cmbDesgloseExistente.setValue(null);
        if (txtAncho != null) txtAncho.clear();
        if (txtAlto != null) txtAlto.clear();
        if (txtCostoMateriales != null) txtCostoMateriales.clear();
        if (txtManoObra != null) txtManoObra.clear();
        if (txtGastosIndirectos != null) txtGastosIndirectos.clear();
        if (txtPrecioVenta != null) txtPrecioVenta.clear();
        if (cmbCliente != null) cmbCliente.setValue(null);
        if (cmbTipoVentana != null) cmbTipoVentana.setValue(null);
        if (cmbTipoPerfil != null) cmbTipoPerfil.setValue(null);
        if (cmbGrosor != null) cmbGrosor.setValue(null);
        if (cmbTipoVidrio != null) cmbTipoVidrio.setValue(null);
        if (cmbEspesor != null) cmbEspesor.setValue(null);
        if (cmbHerrajes != null) cmbHerrajes.setValue(null);
        if (cmbSellador != null) cmbSellador.setValue(null);
        if (cmbAcabado != null) cmbAcabado.setValue(null);
        if (spnCantHojas != null) spnCantHojas.getValueFactory().setValue(1);
        if (lblArea != null) lblArea.setText("0.00 m²");
        if (lblPerimetro != null) lblPerimetro.setText("0.00 m");
        if (lblCostoTotal != null) lblCostoTotal.setText("0.00");
        if (lblMargen != null) lblMargen.setText("0.0%");
    }

    // ==================== COMPRAS ====================
    private void initCompras() {
        if (tableViewCompras != null) {
            colCodigoCompra.setCellValueFactory(new PropertyValueFactory<>("codigo"));
            colProveedor.setCellValueFactory(new PropertyValueFactory<>("proveedor"));
            colMaterial.setCellValueFactory(new PropertyValueFactory<>("material"));
            colFechaEntrega.setCellValueFactory(new PropertyValueFactory<>("fechaEntrega"));
            colCantidadCompra.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
            colPrecioUnitarioCompra.setCellValueFactory(new PropertyValueFactory<>("precioUnitario"));
            colTotalCompra.setCellValueFactory(new PropertyValueFactory<>("total"));
            colEstadoCompra.setCellValueFactory(new PropertyValueFactory<>("estado"));
            cargarCompras();
        }
        
        // Cargar combobox de categorías para filtrar
        if (cmbCategoriaCompra != null) {
            ObservableList<String> categorias = FXCollections.observableArrayList();
            categorias.addAll("Todos", "Aluminio", "Vidrio", "Herrajes", "Selladores");
            cmbCategoriaCompra.setItems(categorias);
            System.out.println("✅ Cargadas " + categorias.size() + " categorías en cmbCategoriaCompra");
            
            // Agregar listener para filtrar compras por material
            cmbCategoriaCompra.setOnAction(event -> {
                String categoriaSeleccionada = cmbCategoriaCompra.getValue();
                if (categoriaSeleccionada != null) {
                    filtrarComprasPorMaterial(categoriaSeleccionada);
                }
            });
        }
        
        // Cargar otros comboboxes
        if (cmbProveedor != null) {
            ObservableList<String> proveedores = FXCollections.observableArrayList();
            String sql = "SELECT nombre FROM Proveedor ORDER BY nombre";
            Connection conn = conexion.establecerConexion();
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) proveedores.add(rs.getString("nombre"));
                cmbProveedor.setItems(proveedores);
                System.out.println("✅ Cargados " + proveedores.size() + " proveedores en cmbProveedor");
            } catch (SQLException e) {
                System.err.println("❌ Error al cargar proveedores: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        if (cmbMaterial != null) {
            ObservableList<String> materiales = FXCollections.observableArrayList();
            String sql = "SELECT nombre FROM Inventario ORDER BY nombre";
            Connection conn = conexion.establecerConexion();
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) materiales.add(rs.getString("nombre"));
                cmbMaterial.setItems(materiales);
                System.out.println("✅ Cargados " + materiales.size() + " materiales en cmbMaterial");
            } catch (SQLException e) { e.printStackTrace(); }
        }
        
        if (cmbEstado != null) {
            cmbEstado.setItems(FXCollections.observableArrayList("Pendiente", "En Proceso", "Recibida", "Cancelada"));
        }
        
        // Agregar listeners para cálculo automático del total
        if (txtCantidadCompra != null && txtPrecioUnitario != null) {
            txtCantidadCompra.textProperty().addListener((obs, oldVal, newVal) -> calcularTotalCompra());
            txtPrecioUnitario.textProperty().addListener((obs, oldVal, newVal) -> calcularTotalCompra());
        }
    }
    
    private void calcularTotalCompra() {
        try {
            String cantidadStr = txtCantidadCompra != null && !txtCantidadCompra.getText().isEmpty() ? 
                txtCantidadCompra.getText().trim().replaceAll("[^0-9.]", "") : "0";
            String precioStr = txtPrecioUnitario != null && !txtPrecioUnitario.getText().isEmpty() ? 
                txtPrecioUnitario.getText().trim().replaceAll("[^0-9.]", "") : "0";
            
            double cantidad = cantidadStr.isEmpty() ? 0.0 : Double.parseDouble(cantidadStr);
            double precio = precioStr.isEmpty() ? 0.0 : Double.parseDouble(precioStr);
            double total = cantidad * precio;
            
            if (lblTotal != null) {
                lblTotal.setText(String.format("%.2f", total));
            }
        } catch (Exception e) {
            if (lblTotal != null) {
                lblTotal.setText("0.00");
            }
        }
    }
    private void cargarCompras() {
        ObservableList<Compra> lista = FXCollections.observableArrayList();
        String sql = "SELECT c.codigo_oc, p.nombre, m.nombre, c.fecha_entrega, c.cantidad, c.precio_unitario, c.total, c.estado " +
                     "FROM Compras c " +
                     "JOIN Proveedor p ON c.proveedor_id = p.id_proveedor " +
                     "JOIN Inventario m ON c.material_id = m.id";
        Connection conn = conexion.establecerConexion();
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) lista.add(new Compra(rs.getString(1), rs.getString(2), rs.getString(3), String.valueOf(rs.getDate(4)), rs.getDouble(5), rs.getDouble(6), rs.getDouble(7), rs.getString(8)));
            tableViewCompras.setItems(lista);
        } catch (SQLException e) { 
            System.err.println("❌ Error al cargar compras: " + e.getMessage());
            e.printStackTrace(); 
        }
    }
    @FXML protected void FnGuardarCompra(ActionEvent event) {
        // Validaciones básicas
        if (cmbProveedor == null || cmbProveedor.getValue() == null) { 
            JOptionPane.showMessageDialog(null, "Debe seleccionar un Proveedor."); return; 
        }
        if (cmbMaterial == null || cmbMaterial.getValue() == null) { 
            JOptionPane.showMessageDialog(null, "Debe seleccionar un Material."); return; 
        }
        if (txtCantidadCompra == null || txtCantidadCompra.getText().trim().isEmpty()) { 
            JOptionPane.showMessageDialog(null, "El campo Cantidad es obligatorio."); return; 
        }
        if (txtPrecioUnitario == null || txtPrecioUnitario.getText().trim().isEmpty()) { 
            JOptionPane.showMessageDialog(null, "El campo Precio Unitario es obligatorio."); return; 
        }

        // Limpiar texto no numérico antes de parsear
        String cantidadStr = txtCantidadCompra.getText().trim().replaceAll("[^0-9.]", "");
        double cantidad = cantidadStr.isEmpty() ? 0.0 : Double.parseDouble(cantidadStr);

        String precioUnitarioStr = txtPrecioUnitario.getText().trim().replaceAll("[^0-9.]", "");
        double precioUnitario = precioUnitarioStr.isEmpty() ? 0.0 : Double.parseDouble(precioUnitarioStr);

        double total = cantidad * precioUnitario;
        
        // Actualizar label del total
        if (lblTotal != null) {
            lblTotal.setText(String.format("%.2f", total));
        }
        
        // Generar código de compra automáticamente
        String codigoCompra = "OC-" + java.time.Year.now().getValue() + "-" + String.format("%03d", (int)(Math.random() * 1000));
        
        // Insertar en la tabla Compras
        String sql = "INSERT INTO Compras (codigo_oc, proveedor_id, material_id, fecha_entrega, cantidad, precio_unitario, total, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = conexion.establecerConexion();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, codigoCompra);
            ps.setInt(2, obtenerProveedorId(cmbProveedor.getValue()));
            ps.setInt(3, obtenerMaterialId(cmbMaterial.getValue()));
            if (dpFechaEntrega != null && dpFechaEntrega.getValue() != null) {
                ps.setDate(4, java.sql.Date.valueOf(dpFechaEntrega.getValue()));
            } else {
                ps.setNull(4, java.sql.Types.DATE);
            }
            ps.setDouble(5, cantidad);
            ps.setDouble(6, precioUnitario);
            ps.setDouble(7, total);
            ps.setString(8, cmbEstado != null ? cmbEstado.getValue() : "Pendiente");
            
            int rows = ps.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "✅ Compra '" + codigoCompra + "' guardada correctamente.");
                registrarActividad("Compra registrada: " + codigoCompra + " | Proveedor: " + cmbProveedor.getValue(), getUsuarioActual());
                limpiarCompra();
                cargarCompras();
                if (mainTabPane != null) mainTabPane.getSelectionModel().select(8);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar la compra: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @FXML protected void FnEditarCompra(ActionEvent event) {
        Compra sel = tableViewCompras != null ? tableViewCompras.getSelectionModel().getSelectedItem() : null;
        if (sel == null) { JOptionPane.showMessageDialog(null, "Seleccione una compra de la tabla para editar."); return; }
        if (mainTabPane != null) mainTabPane.getSelectionModel().select(3);
        if (cmbProveedor != null) cmbProveedor.setValue(sel.getProveedor());
        if (cmbMaterial != null) cmbMaterial.setValue(sel.getMaterial());
        if (txtCantidadCompra != null) txtCantidadCompra.setText(String.valueOf(sel.getCantidad()));
        if (txtPrecioUnitario != null) txtPrecioUnitario.setText(String.valueOf(sel.getPrecioUnitario()));
    }
    @FXML protected void FnBorrarCompra(ActionEvent event) {
        Compra sel = tableViewCompras != null ? tableViewCompras.getSelectionModel().getSelectedItem() : null;
        if (sel == null) { JOptionPane.showMessageDialog(null, "Seleccione una compra de la tabla para borrar."); return; }
        int confirm = JOptionPane.showConfirmDialog(null, "¿Eliminar la compra '" + sel.getCodigo() + "'?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        Connection conn = conexion.establecerConexion();
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM Compras WHERE codigo_oc = ?")) {
            ps.setString(1, sel.getCodigo());
            if (ps.executeUpdate() > 0) { JOptionPane.showMessageDialog(null, "Compra eliminada."); cargarCompras(); }
        } catch (SQLException e) { JOptionPane.showMessageDialog(null, "Error al borrar: " + e.getMessage()); }
    }
    @FXML protected void SeleccionarCompra(MouseEvent event) {
        if (tableViewCompras == null) return;
        Compra sel = tableViewCompras.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        if (cmbProveedor != null) cmbProveedor.setValue(sel.getProveedor());
        if (cmbMaterial != null) cmbMaterial.setValue(sel.getMaterial());
        if (txtCantidadCompra != null) txtCantidadCompra.setText(String.valueOf(sel.getCantidad()));
        if (txtPrecioUnitario != null) txtPrecioUnitario.setText(String.valueOf(sel.getPrecioUnitario()));
    }
    @FXML protected void FnNuevoCompra(ActionEvent event) { limpiarCompra(); }
    
    @FXML protected void FnBuscarCompra(ActionEvent event) {
        String busqueda = txtBuscarCompra != null ? txtBuscarCompra.getText().trim() : "";
        ObservableList<Compra> lista = FXCollections.observableArrayList();
        String sql;
        
        if (busqueda.isEmpty()) {
            sql = "SELECT c.codigo_oc, p.nombre, m.nombre, c.fecha_entrega, c.cantidad, c.precio_unitario, c.total, c.estado " +
                  "FROM Compras c " +
                  "JOIN Proveedor p ON c.proveedor_id = p.id_proveedor " +
                  "JOIN Inventario m ON c.material_id = m.id";
        } else {
            sql = "SELECT c.codigo_oc, p.nombre, m.nombre, c.fecha_entrega, c.cantidad, c.precio_unitario, c.total, c.estado " +
                  "FROM Compras c " +
                  "JOIN Proveedor p ON c.proveedor_id = p.id_proveedor " +
                  "JOIN Inventario m ON c.material_id = m.id " +
                  "WHERE c.codigo_oc LIKE '%" + busqueda + "%' OR p.nombre LIKE '%" + busqueda + "%'";
        }
        
        Connection conn = conexion.establecerConexion();
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) lista.add(new Compra(rs.getString(1), rs.getString(2), rs.getString(3), String.valueOf(rs.getDate(4)), rs.getDouble(5), rs.getDouble(6), rs.getDouble(7), rs.getString(8)));
            tableViewCompras.setItems(lista);
        } catch (SQLException e) { e.printStackTrace(); }
    }
    
    private void limpiarCompra() {
        if (cmbCategoriaCompra != null) cmbCategoriaCompra.setValue(null);
        if (cmbProveedor != null) cmbProveedor.setValue(null);
        if (cmbMaterial != null) cmbMaterial.setValue(null);
        if (txtCantidadCompra != null) txtCantidadCompra.clear();
        if (txtPrecioUnitario != null) txtPrecioUnitario.clear();
        if (dpFechaEntrega != null) dpFechaEntrega.setValue(null);
        if (cmbEstado != null) cmbEstado.setValue(null);
    }
    
    private void filtrarComprasPorMaterial(String material) {
        Connection conn = conexion.establecerConexion();
        try (Statement stmt = conn.createStatement()) {
            String sql;
            if ("Todos".equals(material)) {
                sql = "SELECT codigo_oc FROM Compras ORDER BY codigo_oc";
            } else {
                sql = "SELECT codigo_oc FROM Compras WHERE material_id IN (SELECT id FROM Inventario WHERE nombre = '" + material + "') ORDER BY codigo_oc";
            }
            
            ResultSet rs = stmt.executeQuery(sql);
            ObservableList<String> comprasFiltradas = FXCollections.observableArrayList();
            
            while (rs.next()) {
                comprasFiltradas.add(rs.getString("codigo_oc"));
            }
            
            // El combobox de compras existentes fue eliminado
            
            if (comprasFiltradas.isEmpty()) {
                System.out.println("⚠️ No hay compras del material: " + material);
            } else {
                System.out.println("✅ Filtradas " + comprasFiltradas.size() + " compras para el material: " + material);
            }
            
            rs.close();
        } catch (SQLException e) {
            System.err.println("❌ Error al filtrar compras por material: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private int obtenerProveedorId(String nombreProveedor) throws SQLException {
        String sql = "SELECT id_proveedor FROM Proveedor WHERE nombre = ?";
        Connection conn = conexion.establecerConexion();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombreProveedor);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_proveedor");
            }
        }
        throw new SQLException("Proveedor no encontrado: " + nombreProveedor);
    }
    
    private int obtenerMaterialId(String nombreMaterial) throws SQLException {
        String sql = "SELECT id FROM Inventario WHERE nombre = ?";
        Connection conn = conexion.establecerConexion();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombreMaterial);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        throw new SQLException("Material no encontrado: " + nombreMaterial);
    }

    // ==================== ÓRDENES ====================
    private void initOrdenes() {
        if (tableViewOrdenes != null) {
            colCodigoOrden.setCellValueFactory(new PropertyValueFactory<>("codigo"));
            colClienteOrden.setCellValueFactory(new PropertyValueFactory<>("cliente"));
            colDesglose.setCellValueFactory(new PropertyValueFactory<>("desglose"));
            colFechaInicio.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
            colFechaEstimada.setCellValueFactory(new PropertyValueFactory<>("fechaEstimada"));
            colEstadoOrden.setCellValueFactory(new PropertyValueFactory<>("estado"));
            colTecnico.setCellValueFactory(new PropertyValueFactory<>("tecnico"));
            cargarOrdenes();
        }
        cargarCombosOrdenes();
    }

    private void cargarCombosOrdenes() {
        System.out.println("🔍 Iniciando carga de combos para órdenes...");
        
        // Cargar combobox de órdenes existentes
        if (cmbOrdenExistente != null) {
            ObservableList<String> ordenes = FXCollections.observableArrayList();
            String sql = "SELECT codigo FROM OrdenTrabajo ORDER BY codigo";
            Connection conn = conexion.establecerConexion();
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) ordenes.add(rs.getString("codigo"));
                cmbOrdenExistente.setItems(ordenes);
                System.out.println("✅ Cargadas " + ordenes.size() + " órdenes en cmbOrdenExistente");
            } catch (SQLException e) {
                System.err.println("❌ Error al cargar órdenes: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        // Cargar combobox de categorías para filtrar
        if (cmbCategoriaOrden != null) {
            ObservableList<String> categorias = FXCollections.observableArrayList();
            categorias.addAll("Todos", "Pendiente", "En Producción", "Lista para Instalar", "Instalada", "Cancelada");
            cmbCategoriaOrden.setItems(categorias);
            System.out.println("✅ Cargadas " + categorias.size() + " categorías en cmbCategoriaOrden");
            
            // Agregar listener para filtrar órdenes por estado
            cmbCategoriaOrden.setOnAction(event -> {
                String categoriaSeleccionada = cmbCategoriaOrden.getValue();
                if (categoriaSeleccionada != null && cmbOrdenExistente != null) {
                    filtrarOrdenesPorEstado(categoriaSeleccionada);
                }
            });
        }
        
        if (cmbClienteOrden != null) {
            ObservableList<String> clientes = FXCollections.observableArrayList();
            String sql = "SELECT nombre FROM Cliente";
            Connection conn = conexion.establecerConexion();
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) clientes.add(rs.getString("nombre"));
                cmbClienteOrden.setItems(clientes);
                System.out.println("✅ Cargados " + clientes.size() + " clientes");
            } catch (SQLException e) { e.printStackTrace(); }
        }
        
        if (cmbDesglose != null) {
            ObservableList<String> desgloses = FXCollections.observableArrayList();
            String sql = "SELECT codigo FROM Desglose";
            Connection conn = conexion.establecerConexion();
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) desgloses.add(rs.getString("codigo"));
                cmbDesglose.setItems(desgloses);
                System.out.println("✅ Cargados " + desgloses.size() + " desgloses");
            } catch (SQLException e) { e.printStackTrace(); }
        }
        
        // Método mejorado para cargar técnicos con más depuración
        cargarTecnicosEnOrdenes();
        
        if (cmbEstadoOrden != null) {
            cmbEstadoOrden.setItems(FXCollections.observableArrayList("Pendiente", "En Proceso", "Completada", "Cancelada"));
            System.out.println("✅ Cargados estados de orden");
        }
    }
    
    private void cargarTecnicosEnOrdenes() {
        System.out.println("🔍 Cargando técnicos para cmbTecnico...");
        
        if (cmbTecnico == null) {
            System.err.println("❌ cmbTecnico es null - no se puede cargar");
            return;
        }
        
        ObservableList<String> tecnicos = FXCollections.observableArrayList();
        String sql = "SELECT nombre FROM Tecnico ORDER BY nombre";
        
        Connection conn = conexion.establecerConexion();
        if (conn == null) {
            System.err.println("❌ No se pudo establecer conexión a la base de datos");
            return;
        }
        
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    String nombre = rs.getString("nombre");
                    tecnicos.add(nombre);
                    System.out.println("  - Técnico encontrado: " + nombre);
                }
                
                cmbTecnico.setItems(tecnicos);
                
                if (tecnicos.isEmpty()) {
                    System.out.println("⚠️ No se encontraron técnicos en la base de datos");
                    System.out.println("💡 Sugerencia: Ejecute TestTecnicos.java para verificar la tabla");
                } else {
                    System.out.println("✅ Cargados " + tecnicos.size() + " técnicos en cmbTecnico");
                    
                    // Forzar refresh del combobox
                    cmbTecnico.getSelectionModel().clearSelection();
                    if (!tecnicos.isEmpty()) {
                        cmbTecnico.getSelectionModel().selectFirst();
                    }
                }
        } catch (SQLException e) {
            System.err.println("❌ Error SQL al cargar técnicos: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("❌ Error general al cargar técnicos: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private void filtrarOrdenesPorEstado(String estado) {
        Connection conn = conexion.establecerConexion();
        try (Statement stmt = conn.createStatement()) {
            String sql;
            if ("Todos".equals(estado)) {
                sql = "SELECT codigo FROM OrdenTrabajo ORDER BY codigo";
            } else {
                sql = "SELECT codigo FROM OrdenTrabajo WHERE estado = '" + estado + "' ORDER BY codigo";
            }
            
            ResultSet rs = stmt.executeQuery(sql);
            ObservableList<String> ordenesFiltradas = FXCollections.observableArrayList();
            
            while (rs.next()) {
                ordenesFiltradas.add(rs.getString("codigo"));
            }
            
            cmbOrdenExistente.setItems(ordenesFiltradas);
            
            if (ordenesFiltradas.isEmpty()) {
                System.out.println("⚠️ No hay órdenes con estado: " + estado);
            } else {
                System.out.println("✅ Filtradas " + ordenesFiltradas.size() + " órdenes para el estado: " + estado);
            }
            
            rs.close();
        } catch (SQLException e) {
            System.err.println("❌ Error al filtrar órdenes por estado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void cargarOrdenes() {
        ObservableList<OrdenTrabajo> lista = FXCollections.observableArrayList();
        // Ajustado: d.id -> d.id_desglose, t.id -> t.id_tecnico
        String sql = "SELECT o.codigo, c.nombre, d.codigo, o.fecha_inicio, o.fecha_estimada, o.estado, t.nombre " +
                     "FROM OrdenTrabajo o " +
                     "JOIN Cliente c ON o.cliente_id = c.id_cliente " +
                     "JOIN Desglose d ON o.desglose_id = d.id_desglose " +
                     "JOIN Tecnico t ON o.tecnico_id = t.id_tecnico";
        Connection conn = conexion.establecerConexion();
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) lista.add(new OrdenTrabajo(rs.getString(1), rs.getString(2), rs.getString(3), String.valueOf(rs.getDate(4)), String.valueOf(rs.getDate(5)), rs.getString(6), rs.getString(7)));
            tableViewOrdenes.setItems(lista);
        } catch (SQLException e) { e.printStackTrace(); }
    }
    @FXML protected void FnGuardarOrden(ActionEvent event) {
        if (cmbClienteOrden.getValue() == null || cmbDesglose.getValue() == null || cmbTecnico.getValue() == null) {
            JOptionPane.showMessageDialog(null, "Por favor, complete los campos obligatorios (Cliente, Desglose, Técnico).");
            return;
        }

        // Generar código automáticamente si el campo está vacío
        String codigo;
        if (txtCodigoOrden != null && !txtCodigoOrden.getText().isEmpty()) {
            codigo = txtCodigoOrden.getText();
        } else {
            try {
                int year = java.time.Year.now().getValue();
                String sqlCount = "SELECT COUNT(*) FROM OrdenTrabajo WHERE codigo LIKE 'ORD-" + year + "%'";
                int count = 0;
                Connection conn = conexion.establecerConexion();
                try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sqlCount)) {
                    if (rs.next()) count = rs.getInt(1);
                }
                codigo = String.format("ORD-%d-%03d", year, count + 1);
                if (txtCodigoOrden != null) txtCodigoOrden.setText(codigo);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al generar código: " + e.getMessage());
                return;
            }
        }

        String clienteNombre = cmbClienteOrden.getValue();
        String desgloseCodigo = cmbDesglose.getValue();
        String fechaInicio = dpFechaInicio.getValue() != null ? dpFechaInicio.getValue().toString() : null;
        String fechaEstimada = dpFechaEstimada.getValue() != null ? dpFechaEstimada.getValue().toString() : null;
        String estado = cmbEstadoOrden.getValue() != null ? cmbEstadoOrden.getValue() : "Pendiente";
        String tecnicoNombre = cmbTecnico.getValue();
        String observaciones = txtObservaciones != null && txtObservaciones.getText() != null ? txtObservaciones.getText() : "";

        int clienteId = obtenerIdGenerico("SELECT id_cliente FROM Cliente WHERE nombre = ?", clienteNombre);
        int desgloseId = obtenerIdGenerico("SELECT id_desglose FROM Desglose WHERE codigo = ?", desgloseCodigo);
        int tecnicoId = obtenerIdGenerico("SELECT id_tecnico FROM Tecnico WHERE nombre = ?", tecnicoNombre);

        if (clienteId == 0 || desgloseId == 0 || tecnicoId == 0) {
             JOptionPane.showMessageDialog(null, "No se encontraron los IDs en la base de datos.");
             return;
        }

        String sql = "INSERT INTO OrdenTrabajo (codigo, cliente_id, desglose_id, fecha_inicio, fecha_estimada, estado, tecnico_id, observaciones) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = conexion.establecerConexion();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, codigo);
            pstmt.setInt(2, clienteId);
            pstmt.setInt(3, desgloseId);
            if (fechaInicio != null) pstmt.setDate(4, java.sql.Date.valueOf(fechaInicio)); else pstmt.setNull(4, java.sql.Types.DATE);
            if (fechaEstimada != null) pstmt.setDate(5, java.sql.Date.valueOf(fechaEstimada)); else pstmt.setNull(5, java.sql.Types.DATE);
            pstmt.setString(6, estado);
            pstmt.setInt(7, tecnicoId);
            pstmt.setString(8, observaciones);

            int filas = pstmt.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Orden de Trabajo registrada correctamente.");
                registrarActividad("Orden creada: " + codigo + " | Cliente: " + cmbClienteOrden.getValue(), getUsuarioActual());
                FnNuevoOrden(event);
                cargarOrdenes();
                actualizarContadoresOrdenes(); // Actualizar contadores
                if (mainTabPane != null) mainTabPane.getSelectionModel().select(4);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @FXML protected void FnNuevoOrden(ActionEvent event) {
        if (txtCodigoOrden != null) txtCodigoOrden.clear();
        if (cmbClienteOrden != null) cmbClienteOrden.setValue(null);
        if (cmbDesglose != null) cmbDesglose.setValue(null);
        if (dpFechaInicio != null) dpFechaInicio.setValue(null);
        if (dpFechaEstimada != null) dpFechaEstimada.setValue(null);
        if (cmbEstadoOrden != null) cmbEstadoOrden.setValue(null);
        if (cmbTecnico != null) cmbTecnico.setValue(null);
        if (txtObservaciones != null) txtObservaciones.clear();
    }
    @FXML protected void FnEditarOrden(ActionEvent event) {
        OrdenTrabajo sel = tableViewOrdenes != null ? tableViewOrdenes.getSelectionModel().getSelectedItem() : null;
        if (sel == null) { JOptionPane.showMessageDialog(null, "Seleccione una orden para editar."); return; }
        if (mainTabPane != null) mainTabPane.getSelectionModel().select(3);
        if (txtCodigoOrden != null) txtCodigoOrden.setText(sel.getCodigo());
        if (cmbClienteOrden != null) cmbClienteOrden.setValue(sel.getCliente());
        if (cmbDesglose != null) cmbDesglose.setValue(sel.getDesglose());
        if (dpFechaInicio != null && sel.getFechaInicio() != null && !sel.getFechaInicio().equals("null")) dpFechaInicio.setValue(java.time.LocalDate.parse(sel.getFechaInicio()));
        if (dpFechaEstimada != null && sel.getFechaEstimada() != null && !sel.getFechaEstimada().equals("null")) dpFechaEstimada.setValue(java.time.LocalDate.parse(sel.getFechaEstimada()));
        if (cmbEstadoOrden != null) cmbEstadoOrden.setValue(sel.getEstado());
        if (cmbTecnico != null) cmbTecnico.setValue(sel.getTecnico());
    }
    @FXML protected void FnBorrarOrden(ActionEvent event) {
        OrdenTrabajo sel = tableViewOrdenes != null ? tableViewOrdenes.getSelectionModel().getSelectedItem() : null;
        if (sel == null) { JOptionPane.showMessageDialog(null, "Seleccione una orden para borrar."); return; }
        int confirm = JOptionPane.showConfirmDialog(null, "¿Eliminar la orden '" + sel.getCodigo() + "'?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        Connection conn = conexion.establecerConexion();
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM OrdenTrabajo WHERE codigo = ?")) {
            ps.setString(1, sel.getCodigo());
            if (ps.executeUpdate() > 0) { JOptionPane.showMessageDialog(null, "Orden eliminada."); cargarOrdenes(); actualizarContadoresOrdenes(); }
        } catch (SQLException e) { JOptionPane.showMessageDialog(null, "Error al borrar: " + e.getMessage()); }
    }
    @FXML protected void SeleccionarOrden(MouseEvent event) {
        if (tableViewOrdenes == null) return;
        OrdenTrabajo sel = tableViewOrdenes.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        if (txtCodigoOrden != null) txtCodigoOrden.setText(sel.getCodigo());
        if (cmbClienteOrden != null) cmbClienteOrden.setValue(sel.getCliente());
        if (cmbDesglose != null) cmbDesglose.setValue(sel.getDesglose());
        if (dpFechaInicio != null && sel.getFechaInicio() != null && !sel.getFechaInicio().equals("null")) dpFechaInicio.setValue(java.time.LocalDate.parse(sel.getFechaInicio()));
        if (dpFechaEstimada != null && sel.getFechaEstimada() != null && !sel.getFechaEstimada().equals("null")) dpFechaEstimada.setValue(java.time.LocalDate.parse(sel.getFechaEstimada()));
    }
    
    // ==================== INSTALACIONES ====================
    private void initInstalaciones() {
        if (tableViewInstalaciones != null) {
            colOrden.setCellValueFactory(new PropertyValueFactory<>("ordenTrabajo"));
            colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
            colHora.setCellValueFactory(new PropertyValueFactory<>("hora"));
            colTecnicoInst.setCellValueFactory(new PropertyValueFactory<>("tecnico"));
            colEstadoInst.setCellValueFactory(new PropertyValueFactory<>("estado"));
            cargarInstalaciones();
        }
        
        if (cmbOrdenTrabajo != null) {
            cmbOrdenTrabajo.setItems(fillCombo("SELECT codigo FROM OrdenTrabajo ORDER BY codigo"));
        }
        
        if (cmbTecnicoInst != null) {
            cmbTecnicoInst.setItems(fillCombo("SELECT nombre FROM Tecnico ORDER BY nombre"));
        }
        
        if (cmbEstadoInst != null) {
            cmbEstadoInst.setItems(FXCollections.observableArrayList("Pendiente", "En Proceso", "Completada", "Cancelada"));
        }
        
        if (cmbHora != null) {
            ObservableList<String> horas = FXCollections.observableArrayList();
            for (int i = 8; i <= 18; i++) {
                for (int j = 0; j < 60; j += 30) {
                    horas.add(String.format("%02d:%02d", i, j));
                }
            }
            cmbHora.setItems(horas);
        }
    }
    
    private void cargarInstalaciones() {
        ObservableList<Instalacion> lista = FXCollections.observableArrayList();
        String sql = "SELECT i.id_instalacion, o.codigo, i.fecha, i.hora, t.nombre, i.estado_instalacion, i.notas " +
                     "FROM Instalacion i " +
                     "JOIN OrdenTrabajo o ON i.orden_id = o.id_orden_trabajo " +
                     "JOIN Tecnico t ON i.tecnico_id = t.id_tecnico " +
                     "ORDER BY i.fecha DESC";
        Connection conn = conexion.establecerConexion();
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Instalacion inst = new Instalacion(
                    rs.getString("codigo"),
                    rs.getString("fecha"),
                    rs.getString("hora"),
                    rs.getString("nombre"),
                    rs.getString("estado_instalacion"),
                    rs.getString("notas")
                );
                inst.setIdInstalacion(rs.getInt("id_instalacion"));
                lista.add(inst);
            }
            tableViewInstalaciones.setItems(lista);
        } catch (SQLException e) { e.printStackTrace(); }
    }
    
    @FXML protected void FnGuardarInstalacion(ActionEvent event) {
        if (cmbOrdenTrabajo == null || cmbOrdenTrabajo.getValue() == null) {
            JOptionPane.showMessageDialog(null, "Seleccione una Orden de Trabajo.");
            return;
        }
        if (cmbTecnicoInst == null || cmbTecnicoInst.getValue() == null) {
            JOptionPane.showMessageDialog(null, "Seleccione un Técnico.");
            return;
        }
        if (dpFechaInst == null || dpFechaInst.getValue() == null) {
            JOptionPane.showMessageDialog(null, "Seleccione una Fecha.");
            return;
        }
        
        String ordenCodigo = cmbOrdenTrabajo.getValue();
        String tecnicoNombre = cmbTecnicoInst.getValue();
        String fecha = dpFechaInst.getValue().toString();
        String hora = cmbHora != null && cmbHora.getValue() != null ? cmbHora.getValue() : "09:00";
        String estado = cmbEstadoInst != null && cmbEstadoInst.getValue() != null ? cmbEstadoInst.getValue() : "Pendiente";
        String notas = taNotas != null ? taNotas.getText() : "";
        
        try {
            int ordenId = obtenerIdGenerico("SELECT id_orden_trabajo FROM OrdenTrabajo WHERE codigo = ?", ordenCodigo);
            int tecnicoId = obtenerIdGenerico("SELECT id_tecnico FROM Tecnico WHERE nombre = ?", tecnicoNombre);
            
            String sql = "INSERT INTO Instalacion (orden_id, tecnico_id, fecha, hora, estado_instalacion, notas, fecha_registro) VALUES (?, ?, ?, ?, ?, ?, GETDATE())";
            Connection conn = conexion.establecerConexion();
            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, ordenId);
                ps.setInt(2, tecnicoId);
                ps.setString(3, fecha);
                ps.setString(4, hora);
                ps.setString(5, estado);
                ps.setString(6, notas);
                ps.executeUpdate();
                
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    selectedInstalacionId = rs.getInt(1);
                }
                
                JOptionPane.showMessageDialog(null, "✅ Instalación registrada.");
                limpiarInstalacion();
                cargarInstalaciones();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
    
    @FXML protected void FnEditarInstalacion(ActionEvent event) {
        Instalacion sel = tableViewInstalaciones.getSelectionModel().getSelectedItem();
        if (sel == null) {
            JOptionPane.showMessageDialog(null, "Seleccione una instalación para editar.");
            return;
        }
        
        String ordenCodigo = cmbOrdenTrabajo.getValue();
        String tecnicoNombre = cmbTecnicoInst.getValue();
        String fecha = dpFechaInst.getValue().toString();
        String hora = cmbHora.getValue();
        String estado = cmbEstadoInst.getValue();
        String notas = taNotas.getText();
        
        try {
            int ordenId = obtenerIdGenerico("SELECT id_orden_trabajo FROM OrdenTrabajo WHERE codigo = ?", ordenCodigo);
            int tecnicoId = obtenerIdGenerico("SELECT id_tecnico FROM Tecnico WHERE nombre = ?", tecnicoNombre);
            
            String sql = "UPDATE Instalacion SET orden_id=?, tecnico_id=?, fecha=?, hora=?, estado_instalacion=?, notas=? WHERE id_instalacion=?";
            Connection conn = conexion.establecerConexion();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, ordenId);
                ps.setInt(2, tecnicoId);
                ps.setString(3, fecha);
                ps.setString(4, hora);
                ps.setString(5, estado);
                ps.setString(6, notas);
                ps.setInt(7, sel.getIdInstalacion());
                ps.executeUpdate();
                
                JOptionPane.showMessageDialog(null, "✅ Instalación actualizada.");
                limpiarInstalacion();
                cargarInstalaciones();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al editar: " + e.getMessage());
        }
    }
    
    @FXML protected void FnBorrarInstalacion(ActionEvent event) {
        Instalacion sel = tableViewInstalaciones.getSelectionModel().getSelectedItem();
        if (sel == null) {
            JOptionPane.showMessageDialog(null, "Seleccione una instalación para borrar.");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(null, 
            "¿Eliminar la instalación del " + sel.getFecha() + " a las " + sel.getHora() + "?", 
            "Confirmar", JOptionPane.YES_NO_OPTION);
        
        if (confirm != JOptionPane.YES_OPTION) return;
        
        Connection conn = conexion.establecerConexion();
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM Instalacion WHERE id_instalacion = ?")) {
            ps.setInt(1, sel.getIdInstalacion());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Instalación eliminada.");
            cargarInstalaciones();
            limpiarInstalacion();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al borrar: " + e.getMessage());
        }
    }
    
    @FXML protected void SeleccionarInstalacion(MouseEvent event) {
        Instalacion sel = tableViewInstalaciones.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        
        selectedInstalacionId = sel.getIdInstalacion();
        if (cmbOrdenTrabajo != null) cmbOrdenTrabajo.setValue(sel.getOrdenTrabajo());
        if (dpFechaInst != null) {
            try {
                dpFechaInst.setValue(LocalDate.parse(sel.getFecha()));
            } catch (Exception e) {}
        }
        if (cmbHora != null) cmbHora.setValue(sel.getHora());
        if (cmbTecnicoInst != null) cmbTecnicoInst.setValue(sel.getTecnico());
        if (cmbEstadoInst != null) cmbEstadoInst.setValue(sel.getEstado());
        if (taNotas != null) taNotas.setText(sel.getNotas() != null ? sel.getNotas() : "");
    }
    
    @FXML protected void FnNuevoInstalacion(ActionEvent event) { 
        limpiarInstalacion();
        selectedInstalacionId = -1;
    }
    
    private void limpiarInstalacion() {
        if (cmbOrdenTrabajo != null) cmbOrdenTrabajo.setValue(null);
        if (cmbTecnicoInst != null) cmbTecnicoInst.setValue(null);
        if (dpFechaInst != null) dpFechaInst.setValue(null);
        if (cmbHora != null) cmbHora.setValue(null);
        if (cmbEstadoInst != null) cmbEstadoInst.setValue(null);
        if (taNotas != null) taNotas.clear();
    }


    // ==================== INVENTARIO ====================
    private void initInventario() {
        if (tableViewInventario != null) {
            colInvId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colInvNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            colInvCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
            colInvCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
            colInvCosto.setCellValueFactory(new PropertyValueFactory<>("costoUnitario"));
            colInvPrecio.setCellValueFactory(new PropertyValueFactory<>("precioUnitario"));
            colInvUbicacion.setCellValueFactory(new PropertyValueFactory<>("ubicacion"));
            cargarInventario();
        }
        
        // Cargar combobox de materiales
        if (cmbInvMaterial != null) {
            ObservableList<String> materiales = FXCollections.observableArrayList();
            String sql = "SELECT nombre FROM Inventario ORDER BY nombre";
            Connection conn = conexion.establecerConexion();
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) materiales.add(rs.getString("nombre"));
                cmbInvMaterial.setItems(materiales);
                System.out.println("✅ Cargados " + materiales.size() + " materiales en cmbInvMaterial");
            } catch (SQLException e) {
                System.err.println("❌ Error al cargar materiales: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        // Cargar combobox de categorías
        if (cmbInvCategoria != null) {
            ObservableList<String> categorias = FXCollections.observableArrayList();
            categorias.addAll("Todos", "Perfiles", "Vidrios", "Herrajes", "Marcos", "Selladores");
            cmbInvCategoria.setItems(categorias);
            System.out.println("✅ Cargadas " + categorias.size() + " categorías en cmbInvCategoria");
            
            // Agregar listener para filtrar materiales por categoría
            cmbInvCategoria.setOnAction(event -> {
                String categoriaSeleccionada = cmbInvCategoria.getValue();
                if (categoriaSeleccionada != null && cmbInvMaterial != null) {
                    filtrarMaterialesPorCategoria(categoriaSeleccionada);
                }
            });
        }
    }
    
    private void filtrarMaterialesPorCategoria(String categoria) {
        Connection conn = conexion.establecerConexion();
        try (Statement stmt = conn.createStatement()) {
            String sql;
            if ("Todos".equals(categoria)) {
                sql = "SELECT nombre FROM Inventario ORDER BY nombre";
            } else {
                sql = "SELECT nombre FROM Inventario WHERE categoria = '" + categoria + "' ORDER BY nombre";
            }
            
            ResultSet rs = stmt.executeQuery(sql);
            ObservableList<String> materialesFiltrados = FXCollections.observableArrayList();
            
            while (rs.next()) {
                materialesFiltrados.add(rs.getString("nombre"));
            }
            
            cmbInvMaterial.setItems(materialesFiltrados);
            
            if (materialesFiltrados.isEmpty()) {
                System.out.println("⚠️ No hay materiales en la categoría: " + categoria);
            } else {
                System.out.println("✅ Filtrados " + materialesFiltrados.size() + " materiales para la categoría: " + categoria);
            }
            
            rs.close();
        } catch (SQLException e) {
            System.err.println("❌ Error al filtrar materiales por categoría: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void cargarInventario() {
        ObservableList<InventarioItem> lista = FXCollections.observableArrayList();
        Connection conn = conexion.establecerConexion();
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM Inventario")) {
            while (rs.next()) lista.add(new InventarioItem(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDouble(5), rs.getDouble(6), rs.getString(7)));
            tableViewInventario.setItems(lista);
        } catch (SQLException e) { e.printStackTrace(); }
    }
    @FXML protected void FnGuardarInventario(ActionEvent event) {
        if (cmbInvMaterial == null || cmbInvMaterial.getValue() == null) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un Material de la lista.");
            return;
        }
        if (txtInvCantidad == null || txtInvCantidad.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El campo Cantidad es obligatorio.");
            return;
        }
        if (txtInvPrecioUnit == null || txtInvPrecioUnit.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El campo Precio de Venta es obligatorio.");
            return;
        }

        try {
            String nombre = cmbInvMaterial.getValue();
            String categoria = cmbInvCategoria != null ? cmbInvCategoria.getValue() : "Sin categoría";
            // Limpiar texto no numérico antes de parsear
            String cantidadStr = txtInvCantidad.getText().trim().replaceAll("[^0-9]", "");
            int cantidad = cantidadStr.isEmpty() ? 0 : Integer.parseInt(cantidadStr);

            String precioUnitarioStr = txtInvPrecioUnit.getText().trim().replaceAll("[^0-9.]", "");
            double precioUnitario = precioUnitarioStr.isEmpty() ? 0.0 : Double.parseDouble(precioUnitarioStr);

            String costoUnitarioStr = txtInvCostoUnit != null && !txtInvCostoUnit.getText().isEmpty() ? 
                txtInvCostoUnit.getText().trim().replaceAll("[^0-9.]", "") : "";
            double costoUnitario = costoUnitarioStr.isEmpty() ? precioUnitario * 0.7 : Double.parseDouble(costoUnitarioStr);
            String ubicacion = txtInvUbicacion != null ? txtInvUbicacion.getText().trim() : "";

            String sql = "INSERT INTO Inventario (nombre, categoria, cantidad, costo_unitario, precio_unitario, ubicacion) VALUES (?, ?, ?, ?, ?, ?)";
            Connection conn = conexion.establecerConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, nombre);
                pstmt.setString(2, categoria);
                pstmt.setInt(3, cantidad);
                pstmt.setDouble(4, costoUnitario);
                pstmt.setDouble(5, precioUnitario);
                pstmt.setString(6, ubicacion);

                int filas = pstmt.executeUpdate();
                if (filas > 0) {
                    JOptionPane.showMessageDialog(null, "Material registrado correctamente.");
                    FnNuevoInventario(event);
                    cargarInventario();
                    if (mainTabPane != null) mainTabPane.getSelectionModel().select(6);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Los campos numéricos deben contener valores válidos.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar material: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML protected void FnNuevoInventario(ActionEvent event) {
        if (cmbInvMaterial != null) cmbInvMaterial.setValue(null);
        if (cmbInvCategoria != null) cmbInvCategoria.setValue(null);
        if (txtInvCantidad != null) txtInvCantidad.clear();
        if (txtInvCostoUnit != null) txtInvCostoUnit.clear();
        if (txtInvPrecioUnit != null) txtInvPrecioUnit.clear();
        if (txtInvUbicacion != null) txtInvUbicacion.clear();
    }

    @FXML protected void FnEditarInventario(ActionEvent event) {
        InventarioItem sel = tableViewInventario != null ? tableViewInventario.getSelectionModel().getSelectedItem() : null;
        if (sel == null) {
            JOptionPane.showMessageDialog(null, "Seleccione un material para editar.");
            return;
        }
        if (mainTabPane != null) mainTabPane.getSelectionModel().select(5);
        
        // Cargar datos en el formulario
        if (cmbInvMaterial != null) cmbInvMaterial.setValue(sel.getNombre());
        if (cmbInvCategoria != null) cmbInvCategoria.setValue(sel.getCategoria());
        if (txtInvCantidad != null) txtInvCantidad.setText(String.valueOf(sel.getCantidad()));
        if (txtInvCostoUnit != null) txtInvCostoUnit.setText(String.valueOf(sel.getCostoUnitario()));
        if (txtInvPrecioUnit != null) txtInvPrecioUnit.setText(String.valueOf(sel.getPrecioUnitario()));
        if (txtInvUbicacion != null) txtInvUbicacion.setText(sel.getUbicacion());
    }

    @FXML protected void FnBorrarInventario(ActionEvent event) {
        InventarioItem sel = tableViewInventario != null ? tableViewInventario.getSelectionModel().getSelectedItem() : null;
        if (sel == null) {
            JOptionPane.showMessageDialog(null, "Seleccione un material para borrar.");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(null, 
            "¿Eliminar el material '" + sel.getNombre() + "'?", 
            "Confirmar", JOptionPane.YES_NO_OPTION);
        
        if (confirm != JOptionPane.YES_OPTION) return;
        
        Connection conn = conexion.establecerConexion();
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM Inventario WHERE id = ?")) {
            
            ps.setInt(1, sel.getId());
            
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Material eliminado.");
                cargarInventario();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al borrar material: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML protected void SeleccionarInventario(MouseEvent event) {
        if (tableViewInventario == null) return;
        InventarioItem sel = tableViewInventario.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        
        // Cargar datos en el formulario al seleccionar
        if (cmbInvMaterial != null) cmbInvMaterial.setValue(sel.getNombre());
        if (cmbInvCategoria != null) cmbInvCategoria.setValue(sel.getCategoria());
        if (txtInvCantidad != null) txtInvCantidad.setText(String.valueOf(sel.getCantidad()));
        if (txtInvCostoUnit != null) txtInvCostoUnit.setText(String.valueOf(sel.getCostoUnitario()));
        if (txtInvPrecioUnit != null) txtInvPrecioUnit.setText(String.valueOf(sel.getPrecioUnitario()));
        if (txtInvUbicacion != null) txtInvUbicacion.setText(sel.getUbicacion());
    }

    // ==================== NÓMINA ====================
    private void initNomina() {
        if (tableViewNomina != null) {
            colNomId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colNomEmpleado.setCellValueFactory(new PropertyValueFactory<>("empleado"));
            colNomPuesto.setCellValueFactory(new PropertyValueFactory<>("puesto"));
            colNomSalarioBase.setCellValueFactory(new PropertyValueFactory<>("salarioBase"));
            colNomHorasExtras.setCellValueFactory(new PropertyValueFactory<>("horasExtras"));
            colNomDeducciones.setCellValueFactory(new PropertyValueFactory<>("deducciones"));
            colNomSalarioNeto.setCellValueFactory(new PropertyValueFactory<>("salarioNeto"));
            cargarNomina();
        }
        
        // Listeners para cálculo inmediato
        if (txtNomSalarioBase != null) txtNomSalarioBase.textProperty().addListener((obs, oldV, newV) -> calcularYMostrarSalarioNeto());
        if (txtNomHorasExtras != null) txtNomHorasExtras.textProperty().addListener((obs, oldV, newV) -> calcularYMostrarSalarioNeto());
        if (txtNomDeducciones != null) txtNomDeducciones.textProperty().addListener((obs, oldV, newV) -> calcularYMostrarSalarioNeto());
    }
    
    private void calcularYMostrarSalarioNeto() {
        try {
            double salarioBase = txtNomSalarioBase != null && !txtNomSalarioBase.getText().isEmpty() ? 
                Double.parseDouble(txtNomSalarioBase.getText().trim().replaceAll("[^0-9.]", "")) : 0;
            double horasExtras = txtNomHorasExtras != null && !txtNomHorasExtras.getText().isEmpty() ? 
                Double.parseDouble(txtNomHorasExtras.getText().trim().replaceAll("[^0-9.]", "")) : 0;
            double deducciones = txtNomDeducciones != null && !txtNomDeducciones.getText().isEmpty() ? 
                Double.parseDouble(txtNomDeducciones.getText().trim().replaceAll("[^0-9.]", "")) : 0;
            
            double valorHora = salarioBase / 160;
            double totalHorasExtras = horasExtras * (valorHora * 1.5);
            double salarioNeto = salarioBase + totalHorasExtras - deducciones;
            
            if (lblNomSalarioNeto != null) {
                lblNomSalarioNeto.setText(String.format("$%.2f", salarioNeto));
            }
        } catch (Exception e) {}
    }
    
    private void cargarNomina() {
        ObservableList<NominaItem> lista = FXCollections.observableArrayList();
        String sql = "SELECT n.id_nomina, n.empleado_id, e.nombre, e.puesto, n.salario_base, n.horas_extras, n.deducciones, n.salario_neto " +
                     "FROM Nomina n " +
                     "LEFT JOIN Empleado e ON n.empleado_id = e.id_empleado " +
                     "ORDER BY n.id_nomina DESC";
        Connection conn = conexion.establecerConexion();
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new NominaItem(
                    rs.getInt("id_nomina"),
                    rs.getInt("empleado_id"),
                    rs.getString("nombre") != null ? rs.getString("nombre") : "Empleado #" + rs.getInt("empleado_id"),
                    rs.getString("puesto") != null ? rs.getString("puesto") : "N/A",
                    rs.getDouble("salario_base"),
                    rs.getDouble("horas_extras"),
                    rs.getDouble("deducciones"),
                    rs.getDouble("salario_neto")
                ));
            }
            tableViewNomina.setItems(lista);
            System.out.println("✅ Cargados " + lista.size() + " registros de nómina");
        } catch (SQLException e) { 
            System.err.println("❌ Error al cargar nómina: " + e.getMessage());
            e.printStackTrace(); 
        }
    }
    
    @FXML protected void FnGuardarNomina(ActionEvent event) {
        if (txtNomEmpleado == null || txtNomEmpleado.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese el nombre del empleado.");
            return;
        }
        if (txtNomSalarioBase == null || txtNomSalarioBase.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese el salario base.");
            return;
        }
        
        try {
            String nombreEmpleado = txtNomEmpleado.getText().trim();
            String puesto = txtNomPuesto != null ? txtNomPuesto.getText().trim() : "Empleado";
            double salarioBase = Double.parseDouble(txtNomSalarioBase.getText().trim().replaceAll("[^0-9.]", ""));
            double horasExtras = txtNomHorasExtras != null && !txtNomHorasExtras.getText().isEmpty() ? 
                Double.parseDouble(txtNomHorasExtras.getText().trim().replaceAll("[^0-9.]", "")) : 0;
            double deducciones = txtNomDeducciones != null && !txtNomDeducciones.getText().isEmpty() ? 
                Double.parseDouble(txtNomDeducciones.getText().trim().replaceAll("[^0-9.]", "")) : 0;
            
            Connection conn = conexion.establecerConexion();
            
            // Intentar obtener ID del empleado
            int empleadoId = 1;
            String sqlBuscar = "SELECT id_empleado FROM Empleado WHERE nombre = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlBuscar)) {
                ps.setString(1, nombreEmpleado);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    empleadoId = rs.getInt("id_empleado");
                } else {
                    String sqlInsert = "INSERT INTO Empleado (nombre, puesto) VALUES (?, ?)";
                    try (PreparedStatement psInsert = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
                        psInsert.setString(1, nombreEmpleado);
                        psInsert.setString(2, puesto);
                        psInsert.executeUpdate();
                        ResultSet rsGen = psInsert.getGeneratedKeys();
                        if (rsGen.next()) {
                            empleadoId = rsGen.getInt(1);
                        }
                    }
                }
            }
            
            // Insertar nómina - SIN salario_neto (es columna calculada)
            // Usar primer día del mes para que sea una fecha válida (YYYY-MM-01)
            String periodo = java.time.YearMonth.now().atDay(1).toString();
            String sql = "INSERT INTO Nomina (empleado_id, periodo, salario_base, horas_extras, deducciones, estado_nomina) " +
                         "VALUES (?, ?, ?, ?, ?, 'Pendiente')";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, empleadoId);
                ps.setString(2, periodo);
                ps.setDouble(3, salarioBase);
                ps.setDouble(4, horasExtras);
                ps.setDouble(5, deducciones);
                int rows = ps.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(null, "✅ Nómina guardada correctamente.\n" +
                        "Empleado: " + nombreEmpleado + "\n" +
                        "Salario Neto Estimado: $" + String.format("%.2f", salarioBase + horasExtras * (salarioBase / 160 * 1.5) - deducciones));
                    registrarActividad("Nómina guardada: Empleado " + nombreEmpleado + " | Salario: $" + salarioBase, getUsuarioActual());
                    limpiarNomina();
                    cargarNomina();
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: Ingrese valores numéricos válidos.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
    
    @FXML protected void FnEditarNomina(ActionEvent event) {
        NominaItem sel = tableViewNomina.getSelectionModel().getSelectedItem();
        if (sel == null) {
            JOptionPane.showMessageDialog(null, "Seleccione un registro de nómina para editar.");
            return;
        }
        
        try {
            String nombreEmpleado = txtNomEmpleado.getText().trim();
            double salarioBase = parseDouble(txtNomSalarioBase.getText());
            double horasExtras = parseDouble(txtNomHorasExtras.getText());
            double deducciones = parseDouble(txtNomDeducciones.getText());
            
            Connection conn = conexion.establecerConexion();
            
            // Buscar o crear empleado por nombre
            int empleadoId = 1;
            String sqlBuscar = "SELECT id_empleado FROM Empleado WHERE nombre = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlBuscar)) {
                ps.setString(1, nombreEmpleado);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    empleadoId = rs.getInt("id_empleado");
                }
            }

            String sql = "UPDATE Nomina SET empleado_id=?, salario_base=?, horas_extras=?, deducciones=? WHERE id_nomina=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, empleadoId);
                ps.setDouble(2, salarioBase);
                ps.setDouble(3, horasExtras);
                ps.setDouble(4, deducciones);
                ps.setInt(5, sel.getId());
                ps.executeUpdate();
                
                JOptionPane.showMessageDialog(null, "✅ Nómina actualizada.");
                limpiarNomina();
                cargarNomina();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Valores numéricos inválidos.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
    
    @FXML protected void FnBorrarNomina(ActionEvent event) {
        NominaItem sel = tableViewNomina.getSelectionModel().getSelectedItem();
        if (sel == null) {
            JOptionPane.showMessageDialog(null, "Seleccione un registro de nómina para eliminar.");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(null, "¿Eliminar el registro de nómina?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        
        Connection conn = conexion.establecerConexion();
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM Nomina WHERE id_nomina = ?")) {
            ps.setInt(1, sel.getId());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Registro eliminado.");
            cargarNomina();
            limpiarNomina();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al borrar: " + e.getMessage());
        }
    }
    
    @FXML protected void FnNuevoEmpleado(ActionEvent event) { 
        limpiarNomina();
        selectedNominaId = -1;
    }

    @FXML protected void FnGuardarEmpleado(ActionEvent event) { FnGuardarNomina(event); }
    @FXML protected void FnEditarEmpleado(ActionEvent event) { FnEditarNomina(event); }
    @FXML protected void FnBorrarEmpleado(ActionEvent event) { FnBorrarNomina(event); }
    
    @FXML protected void SeleccionarNomina(MouseEvent event) {
        NominaItem sel = tableViewNomina.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        
        selectedNominaId = sel.getId();
        if (txtNomEmpleado != null) txtNomEmpleado.setText(sel.getEmpleado());
        if (txtNomPuesto != null) txtNomPuesto.setText(sel.getPuesto());
        if (txtNomSalarioBase != null) txtNomSalarioBase.setText(String.valueOf(sel.getSalarioBase()));
        if (txtNomHorasExtras != null) txtNomHorasExtras.setText(String.valueOf(sel.getHorasExtras()));
        if (txtNomDeducciones != null) txtNomDeducciones.setText(String.valueOf(sel.getDeducciones()));
    }
    
    private void limpiarNomina() {
        if (txtNomEmpleado != null) txtNomEmpleado.clear();
        if (txtNomPuesto != null) txtNomPuesto.clear();
        if (txtNomSalarioBase != null) txtNomSalarioBase.clear();
        if (txtNomHorasExtras != null) txtNomHorasExtras.clear();
        if (txtNomDeducciones != null) txtNomDeducciones.clear();
    }




    // ==================== CONTABILIDAD ====================
    private void initContabilidad() {
        if (tableViewContabilidad != null) {
            colContId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colContFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
            colContTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
            colContSubTipo.setCellValueFactory(new PropertyValueFactory<>("subTipo"));
            colContDesc.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
            colContMonto.setCellValueFactory(new PropertyValueFactory<>("monto"));
            cargarContabilidad();
        }
        
        if (cmbContTipo != null) {
            cmbContTipo.setItems(FXCollections.observableArrayList("Ingreso", "Gasto"));
        }
        
        if (dpContFecha != null) {
            dpContFecha.setValue(LocalDate.now());
        }
    }
    
    private void cargarContabilidad() {
        ObservableList<MovimientoContable> lista = FXCollections.observableArrayList();
        String sql = "SELECT id_movimiento, fecha, tipo, categoria, descripcion, monto FROM Contabilidad ORDER BY fecha DESC";
        Connection conn = conexion.establecerConexion();
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new MovimientoContable(
                    rs.getInt("id_movimiento"),
                    rs.getString("fecha"),
                    rs.getString("tipo"),
                    rs.getString("categoria"),
                    rs.getString("descripcion"),
                    rs.getDouble("monto")
                ));
            }
            tableViewContabilidad.setItems(lista);
            actualizarBalances();
        } catch (SQLException e) { e.printStackTrace(); }
    }
    
    private void actualizarBalances() {
        Connection conn = conexion.establecerConexion();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT ISNULL(SUM(monto), 0) FROM Contabilidad WHERE tipo = 'Ingreso'");
            double ingresos = rs.next() ? rs.getDouble(1) : 0;
            
            rs = stmt.executeQuery("SELECT ISNULL(SUM(monto), 0) FROM Contabilidad WHERE tipo = 'Gasto'");
            double gastos = rs.next() ? rs.getDouble(1) : 0;
            
            if (lblTotalIngresos != null) lblTotalIngresos.setText(String.format("Total Ingresos: $%.2f", ingresos));
            if (lblTotalGastos != null) lblTotalGastos.setText(String.format("Total Gastos: $%.2f", gastos));
            if (lblBalance != null) lblBalance.setText(String.format("Balance: $%.2f", ingresos - gastos));
        } catch (SQLException e) { e.printStackTrace(); }
    }
    
    @FXML protected void FnGuardarMovimiento(ActionEvent event) {
        if (dpContFecha == null || dpContFecha.getValue() == null) {
            JOptionPane.showMessageDialog(null, "Seleccione una fecha.");
            return;
        }
        if (cmbContTipo == null || cmbContTipo.getValue() == null) {
            JOptionPane.showMessageDialog(null, "Seleccione un tipo (Ingreso/Gasto).");
            return;
        }
        if (txtContMonto == null || txtContMonto.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese un monto.");
            return;
        }
        
        try {
            String fecha = dpContFecha.getValue() != null ? dpContFecha.getValue().toString() : java.time.LocalDate.now().toString();
            String tipo = cmbContTipo.getValue();
            String categoria = txtContSubTipo != null ? txtContSubTipo.getText().trim() : "";
            String descripcion = txtContDesc != null ? txtContDesc.getText().trim() : "";
            double monto = parseDouble(txtContMonto.getText());
            
            String sql = "INSERT INTO Contabilidad (fecha, tipo, categoria, descripcion, monto) VALUES (?, ?, ?, ?, ?)";
            Connection conn = conexion.establecerConexion();
            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, fecha);
                ps.setString(2, tipo);
                ps.setString(3, categoria);
                ps.setString(4, descripcion);
                ps.setDouble(5, monto);
                ps.executeUpdate();
                
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    selectedMovimientoId = rs.getInt(1);
                }
                
                JOptionPane.showMessageDialog(null, "✅ Movimiento guardado.");
                limpiarMovimiento();
                cargarContabilidad();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Monto inválido.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
    
    @FXML protected void FnEditarMovimiento(ActionEvent event) {
        MovimientoContable sel = tableViewContabilidad.getSelectionModel().getSelectedItem();
        if (sel == null) {
            JOptionPane.showMessageDialog(null, "Seleccione un movimiento para editar.");
            return;
        }
        
        try {
            String fecha = dpContFecha.getValue() != null ? dpContFecha.getValue().toString() : java.time.LocalDate.now().toString();
            String tipo = cmbContTipo.getValue();
            String categoria = txtContSubTipo.getText().trim();
            String descripcion = txtContDesc.getText().trim();
            double monto = parseDouble(txtContMonto.getText());
            
            String sql = "UPDATE Contabilidad SET fecha=?, tipo=?, categoria=?, descripcion=?, monto=? WHERE id_movimiento=?";
            Connection conn = conexion.establecerConexion();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, fecha);
                ps.setString(2, tipo);
                ps.setString(3, categoria);
                ps.setString(4, descripcion);
                ps.setDouble(5, monto);
                ps.setInt(6, sel.getId());
                ps.executeUpdate();
                
                JOptionPane.showMessageDialog(null, "✅ Movimiento actualizado.");
                limpiarMovimiento();
                cargarContabilidad();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Monto inválido.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
    
    @FXML protected void FnBorrarMovimiento(ActionEvent event) {
        MovimientoContable sel = tableViewContabilidad.getSelectionModel().getSelectedItem();
        if (sel == null) {
            JOptionPane.showMessageDialog(null, "Seleccione un movimiento para eliminar.");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(null, "¿Eliminar el movimiento?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        
        Connection conn = conexion.establecerConexion();
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM Contabilidad WHERE id_movimiento = ?")) {
            ps.setInt(1, sel.getId());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Movimiento eliminado.");
            cargarContabilidad();
            limpiarMovimiento();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al borrar: " + e.getMessage());
        }
    }
    
    @FXML protected void FnNuevoMovimiento(ActionEvent event) { 
        limpiarMovimiento();
        selectedMovimientoId = -1;
        if (dpContFecha != null) dpContFecha.setValue(LocalDate.now());
    }
    
    @FXML protected void SeleccionarMovimiento(MouseEvent event) {
        MovimientoContable sel = tableViewContabilidad.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        
        selectedMovimientoId = sel.getId();
        if (dpContFecha != null) {
            try {
                dpContFecha.setValue(LocalDate.parse(sel.getFecha()));
            } catch (Exception e) {}
        }
        if (cmbContTipo != null) cmbContTipo.setValue(sel.getTipo());
        if (txtContSubTipo != null) txtContSubTipo.setText(sel.getSubTipo());
        if (txtContDesc != null) txtContDesc.setText(sel.getDescripcion());
        if (txtContMonto != null) txtContMonto.setText(String.valueOf(sel.getMonto()));
    }
    
    private void limpiarMovimiento() {
        if (dpContFecha != null) dpContFecha.setValue(LocalDate.now());
        if (cmbContTipo != null) cmbContTipo.setValue(null);
        if (txtContSubTipo != null) txtContSubTipo.clear();
        if (txtContDesc != null) txtContDesc.clear();
        if (txtContMonto != null) txtContMonto.clear();
    }

    // ==================== CLIENTES ====================
    private void initClientes() {
        if (tableViewClientes != null) {
            colCliId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colCliNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            colCliCedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
            colCliTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
            colCliEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            colCliTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
            cargarClientes();
        }
        
        if (cmbCliTipo != null) {
            cmbCliTipo.setItems(FXCollections.observableArrayList("Física", "Jurídica"));
        }
    }
    
    private void cargarClientes() {
        ObservableList<ClienteCompleto> lista = FXCollections.observableArrayList();
        String sql = "SELECT id_cliente, nombre, cedula, telefono, email, tipo_cliente, direccion FROM Cliente ORDER BY nombre";
        Connection conn = conexion.establecerConexion();
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new ClienteCompleto(
                    rs.getInt("id_cliente"),
                    rs.getString("nombre"),
                    rs.getString("cedula"),
                    rs.getString("telefono"),
                    rs.getString("email"),
                    rs.getString("tipo_cliente"),
                    rs.getString("direccion"),
                    null
                ));
            }
            tableViewClientes.setItems(lista);
        } catch (SQLException e) { e.printStackTrace(); }
    }
    
    @FXML protected void FnGuardarCliente(ActionEvent event) {
        if (txtCliNombre == null || txtCliNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El nombre es obligatorio.");
            return;
        }
        
        String nombre = txtCliNombre.getText().trim();
        String cedula = txtCliCedula != null ? txtCliCedula.getText().trim() : "";
        String telefono = txtCliTelefono != null ? txtCliTelefono.getText().trim() : "";
        String email = txtCliEmail != null ? txtCliEmail.getText().trim() : "";
        String tipo = cmbCliTipo != null && cmbCliTipo.getValue() != null ? cmbCliTipo.getValue() : "Física";
        String direccion = txtCliDireccion != null ? txtCliDireccion.getText().trim() : "";
        
        String sql = "INSERT INTO Cliente (nombre, cedula, telefono, email, tipo_cliente, direccion, fecha_registro) VALUES (?, ?, ?, ?, ?, ?, GETDATE())";
        Connection conn = conexion.establecerConexion();
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, nombre);
            ps.setString(2, cedula);
            ps.setString(3, telefono);
            ps.setString(4, email);
            ps.setString(5, tipo);
            ps.setString(6, direccion);
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                selectedClienteId = rs.getInt(1);
            }
            
            JOptionPane.showMessageDialog(null, "✅ Cliente guardado.");
            limpiarCliente();
            cargarClientes();
            // Actualizar combos que usan clientes
            if (cmbCliente != null) cmbCliente.setItems(fillCombo("SELECT nombre FROM Cliente ORDER BY nombre"));
            if (cmbClienteOrden != null) cmbClienteOrden.setItems(fillCombo("SELECT nombre FROM Cliente ORDER BY nombre"));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
    
    @FXML protected void FnEditarCliente(ActionEvent event) {
        ClienteCompleto sel = tableViewClientes.getSelectionModel().getSelectedItem();
        if (sel == null) {
            JOptionPane.showMessageDialog(null, "Seleccione un cliente para editar.");
            return;
        }
        
        String nombre = txtCliNombre.getText().trim();
        String cedula = txtCliCedula != null ? txtCliCedula.getText().trim() : "";
        String telefono = txtCliTelefono != null ? txtCliTelefono.getText().trim() : "";
        String email = txtCliEmail != null ? txtCliEmail.getText().trim() : "";
        String tipo = cmbCliTipo != null && cmbCliTipo.getValue() != null ? cmbCliTipo.getValue() : "Física";
        String direccion = txtCliDireccion != null ? txtCliDireccion.getText().trim() : "";
        
        String sql = "UPDATE Cliente SET nombre=?, cedula=?, telefono=?, email=?, tipo_cliente=?, direccion=? WHERE id_cliente=?";
        Connection conn = conexion.establecerConexion();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, cedula);
            ps.setString(3, telefono);
            ps.setString(4, email);
            ps.setString(5, tipo);
            ps.setString(6, direccion);
            ps.setInt(7, sel.getId());
            ps.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "✅ Cliente actualizado.");
            limpiarCliente();
            cargarClientes();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
    
    @FXML protected void FnBorrarCliente(ActionEvent event) {
        ClienteCompleto sel = tableViewClientes.getSelectionModel().getSelectedItem();
        if (sel == null) {
            JOptionPane.showMessageDialog(null, "Seleccione un cliente para borrar.");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(null, "¿Eliminar al cliente '" + sel.getNombre() + "'?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        
        Connection conn = conexion.establecerConexion();
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM Cliente WHERE id_cliente = ?")) {
            ps.setInt(1, sel.getId());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cliente eliminado.");
            cargarClientes();
            limpiarCliente();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al borrar: " + e.getMessage());
        }
    }
    
    @FXML protected void FnNuevoCliente(ActionEvent event) { 
        limpiarCliente();
        selectedClienteId = -1;
    }
    
    @FXML protected void SeleccionarCliente(MouseEvent event) {
        ClienteCompleto sel = tableViewClientes.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        
        selectedClienteId = sel.getId();
        if (txtCliNombre != null) txtCliNombre.setText(sel.getNombre());
        if (txtCliCedula != null) txtCliCedula.setText(sel.getCedula());
        if (txtCliTelefono != null) txtCliTelefono.setText(sel.getTelefono());
        if (txtCliEmail != null) txtCliEmail.setText(sel.getEmail());
        if (cmbCliTipo != null) cmbCliTipo.setValue(sel.getTipo());
        if (txtCliDireccion != null) txtCliDireccion.setText(sel.getDireccion() != null ? sel.getDireccion() : "");
    }
    
    private void limpiarCliente() {
        if (txtCliNombre != null) txtCliNombre.clear();
        if (txtCliCedula != null) txtCliCedula.clear();
        if (txtCliTelefono != null) txtCliTelefono.clear();
        if (txtCliEmail != null) txtCliEmail.clear();
        if (txtCliDireccion != null) txtCliDireccion.clear();
        if (cmbCliTipo != null) cmbCliTipo.setValue(null);
    }

    // ==================== TÉCNICOS ====================
    private void initTecnicos() {
        if (tableViewTecnicos != null) {
            colTecId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colTecNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            colTecEspecialidad.setCellValueFactory(new PropertyValueFactory<>("especialidad"));
            colTecCalificacion.setCellValueFactory(new PropertyValueFactory<>("calificacion"));
            colTecTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
            colTecDisponibilidad.setCellValueFactory(new PropertyValueFactory<>("disponibilidad"));
            cargarTecnicos();
        }
        
        if (cmbTecEspecialidad != null) {
            cmbTecEspecialidad.setItems(FXCollections.observableArrayList("Instalación", "Mantenimiento", "Reparación", "Soldadura", "Diseño"));
        }
        
        if (cmbTecDisponibilidad != null) {
            cmbTecDisponibilidad.setItems(FXCollections.observableArrayList("Disponible", "No disponible"));
        }
    }
    
    private void cargarTecnicos() {
        ObservableList<TecnicoCompleto> lista = FXCollections.observableArrayList();
        String sql = "SELECT id_tecnico, nombre, especialidad, calificacion, telefono, disponibilidad FROM Tecnico ORDER BY nombre";
        Connection conn = conexion.establecerConexion();
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new TecnicoCompleto(
                    rs.getInt("id_tecnico"),
                    rs.getString("nombre"),
                    rs.getString("especialidad"),
                    rs.getDouble("calificacion"),
                    0,
                    rs.getString("telefono"),
                    rs.getBoolean("disponibilidad") ? "Disponible" : "No disponible"
                ));
            }
            tableViewTecnicos.setItems(lista);
        } catch (SQLException e) { e.printStackTrace(); }
    }
    
    @FXML protected void FnGuardarTecnico(ActionEvent event) {
        if (txtTecNombre == null || txtTecNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El nombre es obligatorio.");
            return;
        }
        
        String nombre = txtTecNombre.getText().trim();
        String especialidad = cmbTecEspecialidad != null && cmbTecEspecialidad.getValue() != null ? cmbTecEspecialidad.getValue() : "";
        double calificacion = 0;
        try {
            if (txtTecCalificacion != null && !txtTecCalificacion.getText().isEmpty()) {
                calificacion = Double.parseDouble(txtTecCalificacion.getText().trim());
            }
        } catch (NumberFormatException e) {}
        String telefono = txtTecTelefono != null ? txtTecTelefono.getText().trim() : "";
        boolean disponible = cmbTecDisponibilidad != null && "Disponible".equals(cmbTecDisponibilidad.getValue());
        
        String sql = "INSERT INTO Tecnico (nombre, especialidad, calificacion, telefono, disponibilidad) VALUES (?, ?, ?, ?, ?)";
        Connection conn = conexion.establecerConexion();
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, nombre);
            ps.setString(2, especialidad);
            ps.setDouble(3, calificacion);
            ps.setString(4, telefono);
            ps.setBoolean(5, disponible);
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                selectedTecnicoId = rs.getInt(1);
            }
            
            JOptionPane.showMessageDialog(null, "✅ Técnico guardado.");
            limpiarTecnico();
            cargarTecnicos();
            cargarTecnicosEnOrdenes();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
    
    @FXML protected void FnEditarTecnico(ActionEvent event) {
        TecnicoCompleto sel = tableViewTecnicos.getSelectionModel().getSelectedItem();
        if (sel == null) {
            JOptionPane.showMessageDialog(null, "Seleccione un técnico para editar.");
            return;
        }
        
        String nombre = txtTecNombre.getText().trim();
        String especialidad = cmbTecEspecialidad != null && cmbTecEspecialidad.getValue() != null ? cmbTecEspecialidad.getValue() : "";
        double calificacion = 0;
        try {
            if (txtTecCalificacion != null && !txtTecCalificacion.getText().isEmpty()) {
                calificacion = Double.parseDouble(txtTecCalificacion.getText().trim());
            }
        } catch (NumberFormatException e) {}
        String telefono = txtTecTelefono != null ? txtTecTelefono.getText().trim() : "";
        boolean disponible = cmbTecDisponibilidad != null && "Disponible".equals(cmbTecDisponibilidad.getValue());
        
        String sql = "UPDATE Tecnico SET nombre=?, especialidad=?, calificacion=?, telefono=?, disponibilidad=? WHERE id_tecnico=?";
        Connection conn = conexion.establecerConexion();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, especialidad);
            ps.setDouble(3, calificacion);
            ps.setString(4, telefono);
            ps.setBoolean(5, disponible);
            ps.setInt(6, sel.getId());
            ps.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "✅ Técnico actualizado.");
            limpiarTecnico();
            cargarTecnicos();
            cargarTecnicosEnOrdenes();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
    
    @FXML protected void FnBorrarTecnico(ActionEvent event) {
        TecnicoCompleto sel = tableViewTecnicos.getSelectionModel().getSelectedItem();
        if (sel == null) {
            JOptionPane.showMessageDialog(null, "Seleccione un técnico para borrar.");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(null, "¿Eliminar al técnico '" + sel.getNombre() + "'?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        
        Connection conn = conexion.establecerConexion();
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM Tecnico WHERE id_tecnico = ?")) {
            ps.setInt(1, sel.getId());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Técnico eliminado.");
            cargarTecnicos();
            cargarTecnicosEnOrdenes();
            limpiarTecnico();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al borrar: " + e.getMessage());
        }
    }
    
    @FXML protected void FnNuevoTecnico(ActionEvent event) { 
        limpiarTecnico();
        selectedTecnicoId = -1;
    }
    
    @FXML protected void SeleccionarTecnico(MouseEvent event) {
        TecnicoCompleto sel = tableViewTecnicos.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        
        selectedTecnicoId = sel.getId();
        if (txtTecNombre != null) txtTecNombre.setText(sel.getNombre());
        if (cmbTecEspecialidad != null) cmbTecEspecialidad.setValue(sel.getEspecialidad());
        if (txtTecCalificacion != null) txtTecCalificacion.setText(String.valueOf(sel.getCalificacion()));
        if (txtTecTelefono != null) txtTecTelefono.setText(sel.getTelefono());
        if (cmbTecDisponibilidad != null) cmbTecDisponibilidad.setValue(sel.getDisponibilidad());
    }
    
    private void limpiarTecnico() {
        if (txtTecNombre != null) txtTecNombre.clear();
        if (cmbTecEspecialidad != null) cmbTecEspecialidad.setValue(null);
        if (txtTecCalificacion != null) txtTecCalificacion.clear();
        if (txtTecTelefono != null) txtTecTelefono.clear();
        if (cmbTecDisponibilidad != null) cmbTecDisponibilidad.setValue(null);
    }

    // ==================== USUARIOS ====================
    private void initUsuarios() {
        if (tableViewUsuarios != null) {
            colUsuarioId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colUsuarioUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
            colUsuarioNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
            colUsuarioEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            colUsuarioRol.setCellValueFactory(new PropertyValueFactory<>("rol"));
            colUsuarioDepto.setCellValueFactory(new PropertyValueFactory<>("departamento"));
            colUsuarioUltimoAcceso.setCellValueFactory(new PropertyValueFactory<>("ultimoAcceso"));
            colUsuarioEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
            cargarUsuarios();
        }
        
        if (cmbUsuarioRol != null) {
            cmbUsuarioRol.setItems(FXCollections.observableArrayList("Administrador", "Ventas", "Producción", "Contabilidad", "Almacén", "Técnico"));
        }
        
        if (cmbUsuarioDepto != null) {
            cmbUsuarioDepto.setItems(FXCollections.observableArrayList("IT", "Ventas", "Producción", "Contabilidad", "Almacén", "Técnico"));
        }
    }
    
    private void cargarUsuarios() {
        ObservableList<Usuario> lista = FXCollections.observableArrayList();
        String sql = "SELECT id_usuario, usuario, nombre_completo, correo, rol, departamento, ultimo_acceso, estado FROM Usuario ORDER BY nombre_completo";
        Connection conn = conexion.establecerConexion();
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Usuario(
                    rs.getInt("id_usuario"),
                    rs.getString("usuario"),
                    rs.getString("nombre_completo"),
                    rs.getString("correo"),
                    rs.getString("rol") != null ? rs.getString("rol") : "Usuario",
                    rs.getString("departamento") != null ? rs.getString("departamento") : "General",
                    rs.getString("ultimo_acceso"),
                    rs.getString("estado")
                ));
            }
            tableViewUsuarios.setItems(lista);
        } catch (SQLException e) { e.printStackTrace(); }
    }
    
    @FXML protected void FnGuardarUsuario(ActionEvent event) {
        if (txtUsuarioUsername == null || txtUsuarioUsername.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El nombre de usuario es obligatorio.");
            return;
        }
        if (txtUsuarioNombre == null || txtUsuarioNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El nombre completo es obligatorio.");
            return;
        }
        
        String username = txtUsuarioUsername.getText().trim();
        String nombreCompleto = txtUsuarioNombre.getText().trim();
        String email = txtUsuarioEmail != null ? txtUsuarioEmail.getText().trim() : "";
        String password = (txtUsuarioPassword != null && !txtUsuarioPassword.getText().trim().isEmpty()) ? 
            txtUsuarioPassword.getText().trim() : "default123";
        String rol = cmbUsuarioRol != null && cmbUsuarioRol.getValue() != null ? cmbUsuarioRol.getValue() : "Usuario";
        String depto = cmbUsuarioDepto != null && cmbUsuarioDepto.getValue() != null ? cmbUsuarioDepto.getValue() : "General";
        
        String sql = "INSERT INTO Usuario (usuario, nombre_completo, correo, clave, rol, departamento, estado, fecha_registro) " +
                     "VALUES (?, ?, ?, ?, ?, ?, 'Activo', GETDATE())";
        Connection conn = conexion.establecerConexion();
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, username);
            ps.setString(2, nombreCompleto);
            ps.setString(3, email);
            ps.setString(4, password);
            ps.setString(5, rol);
            ps.setString(6, depto);
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                selectedUsuarioId = rs.getInt(1);
            }
            
            JOptionPane.showMessageDialog(null, "✅ Usuario guardado.");
            limpiarUsuario();
            cargarUsuarios();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
    
    @FXML protected void FnEditarUsuario(ActionEvent event) {
        Usuario sel = tableViewUsuarios.getSelectionModel().getSelectedItem();
        if (sel == null) {
            JOptionPane.showMessageDialog(null, "Seleccione un usuario para editar.");
            return;
        }
        
        String username = txtUsuarioUsername.getText().trim();
        String nombreCompleto = txtUsuarioNombre.getText().trim();
        String email = txtUsuarioEmail != null ? txtUsuarioEmail.getText().trim() : "";
        String rol = cmbUsuarioRol != null && cmbUsuarioRol.getValue() != null ? cmbUsuarioRol.getValue() : "Usuario";
        String depto = cmbUsuarioDepto != null && cmbUsuarioDepto.getValue() != null ? cmbUsuarioDepto.getValue() : "General";
        
        String sql = "UPDATE Usuario SET usuario=?, nombre_completo=?, correo=?, rol=?, departamento=?, estado=?";
        if (txtUsuarioPassword != null && !txtUsuarioPassword.getText().trim().isEmpty()) {
            sql += ", clave=?";
        }
        sql += " WHERE id_usuario=?";
        
        Connection conn = conexion.establecerConexion();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            int idx = 1;
            ps.setString(idx++, username);
            ps.setString(idx++, nombreCompleto);
            ps.setString(idx++, email);
            ps.setString(idx++, rol);
            ps.setString(idx++, depto);
            ps.setString(idx++, "Activo");
            
            if (txtUsuarioPassword != null && !txtUsuarioPassword.getText().trim().isEmpty()) {
                ps.setString(idx++, txtUsuarioPassword.getText().trim());
            }
            ps.setInt(idx, sel.getId());
            ps.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "✅ Usuario actualizado.");
            limpiarUsuario();
            cargarUsuarios();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
    
    @FXML protected void FnBorrarUsuario(ActionEvent event) {
        Usuario sel = tableViewUsuarios.getSelectionModel().getSelectedItem();
        if (sel == null) {
            JOptionPane.showMessageDialog(null, "Seleccione un usuario para borrar.");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(null, "¿Eliminar al usuario '" + sel.getUsername() + "'?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        
        Connection conn = conexion.establecerConexion();
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM Usuario WHERE id_usuario = ?")) {
            ps.setInt(1, sel.getId());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Usuario eliminado.");
            cargarUsuarios();
            limpiarUsuario();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al borrar: " + e.getMessage());
        }
    }
    
    @FXML protected void FnNuevoUsuario(ActionEvent event) { 
        limpiarUsuario();
        selectedUsuarioId = -1;
    }
    
    @FXML protected void SeleccionarUsuario(MouseEvent event) {
        Usuario sel = tableViewUsuarios.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        
        selectedUsuarioId = sel.getId();
        if (txtUsuarioUsername != null) txtUsuarioUsername.setText(sel.getUsername());
        if (txtUsuarioNombre != null) txtUsuarioNombre.setText(sel.getNombreCompleto());
        if (txtUsuarioEmail != null) txtUsuarioEmail.setText(sel.getEmail());
        if (cmbUsuarioRol != null) cmbUsuarioRol.setValue(sel.getRol());
        if (cmbUsuarioDepto != null) cmbUsuarioDepto.setValue(sel.getDepartamento());
        if (txtUsuarioPassword != null) txtUsuarioPassword.clear();
    }
    
    private void limpiarUsuario() {
        if (txtUsuarioUsername != null) txtUsuarioUsername.clear();
        if (txtUsuarioNombre != null) txtUsuarioNombre.clear();
        if (txtUsuarioEmail != null) txtUsuarioEmail.clear();
        if (txtUsuarioPassword != null) txtUsuarioPassword.clear();
        if (cmbUsuarioRol != null) cmbUsuarioRol.setValue(null);
        if (cmbUsuarioDepto != null) cmbUsuarioDepto.setValue(null);
    }


    // ==================== CONFIGURACIÓN (SMTP) ====================
    private void initConfiguracion() {
        if (tableViewConfiguracion != null) {
            colConfId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colConfClave.setCellValueFactory(new PropertyValueFactory<>("clave"));
            colConfValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
            colConfDesc.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
            cargarConfiguracion();
        }
    }
    private void cargarConfiguracion() {
        ObservableList<ConfiguracionItem> lista = FXCollections.observableArrayList();
        Connection conn = conexion.establecerConexion();
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM Configuracion")) {
            while (rs.next()) {
                lista.add(new ConfiguracionItem(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), ""));
            }
            tableViewConfiguracion.setItems(lista);
            tableViewConfiguracion.refresh();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar configuración: " + e.getMessage() + "\n\nVerifique que la tabla 'Configuracion' existe en la base de datos.");
            e.printStackTrace();
        }
    }

    @FXML private void SeleccionarConfiguracion(MouseEvent event) {
        ConfiguracionItem item = tableViewConfiguracion.getSelectionModel().getSelectedItem();
        if (item != null) { txtConfClave.setText(item.getClave()); txtConfValor.setText(item.getValor()); txtConfDesc.setText(item.getDescripcion()); }
    }
    @FXML protected void FnGuardarConfiguracion(ActionEvent event) {
        String sql = "INSERT INTO Configuracion (clave, valor, descripcion) VALUES (?, ?, ?)";
        Connection conn = conexion.establecerConexion();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, txtConfClave.getText().trim());
            pstmt.setString(2, txtConfValor.getText().trim());
            pstmt.setString(3, txtConfDesc.getText().trim());
            if (pstmt.executeUpdate() == 1) {
                JOptionPane.showMessageDialog(null, "Configuración guardada correctamente.");
                cargarConfiguracion();
                FnLimpiarConfiguracion(null);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML protected void FnEditarConfiguracion(ActionEvent event) {
        ConfiguracionItem item = tableViewConfiguracion.getSelectionModel().getSelectedItem();
        if (item == null) {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione un registro de la tabla para editar.");
            return;
        }
        // Usamos la clave original del item para el WHERE, por si el usuario cambia la clave en el texto
        String sql = "UPDATE Configuracion SET clave=?, valor=?, descripcion=? WHERE clave=?";
        Connection conn = conexion.establecerConexion();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, txtConfClave.getText().trim());
            pstmt.setString(2, txtConfValor.getText().trim());
            pstmt.setString(3, txtConfDesc.getText().trim());
            pstmt.setString(4, item.getClave()); // Identificador único
            if (pstmt.executeUpdate() == 1) {
                JOptionPane.showMessageDialog(null, "Configuración actualizada correctamente.");
                cargarConfiguracion();
                FnLimpiarConfiguracion(null);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al editar: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @FXML protected void FnBorrarConfiguracion(ActionEvent event) {
        ConfiguracionItem item = tableViewConfiguracion.getSelectionModel().getSelectedItem();
        if (item == null) {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione un registro de la tabla para borrar.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar este parámetro?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        Connection conn = conexion.establecerConexion();
        try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Configuracion WHERE clave=?")) {
            pstmt.setString(1, item.getClave());
            if (pstmt.executeUpdate() == 1) {
                JOptionPane.showMessageDialog(null, "Registro eliminado.");
                cargarConfiguracion();
                FnLimpiarConfiguracion(null);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al borrar: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @FXML protected void FnLimpiarConfiguracion(ActionEvent event) {
        if (txtConfClave != null) txtConfClave.clear(); if (txtConfValor != null) txtConfValor.clear(); if (txtConfDesc != null) txtConfDesc.clear();
    }

    @FXML protected void FnProbarCorreo(ActionEvent event) {
        String destinatario = "";
        // Intentamos obtener el correo directamente de la base de datos
        Connection conn = conexion.establecerConexion();
        try (PreparedStatement pstmt = conn.prepareStatement("SELECT valor FROM Configuracion WHERE clave = 'SMTP_USER'")) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) destinatario = rs.getString("valor");
        } catch (Exception e) { e.printStackTrace(); }

        if (destinatario == null || destinatario.isEmpty() || destinatario.contains("tu_correo")) {
            JOptionPane.showMessageDialog(null, "Por favor, guarda primero tu correo real en el campo SMTP_USER.");
            return;
        }

        JOptionPane.showMessageDialog(null, "Enviando correo de prueba a: " + destinatario + "...\n(Esto puede tardar unos segundos)");

        boolean exito = EmailSender.enviarCorreoPrueba(destinatario);

        if (exito) {
            JOptionPane.showMessageDialog(null, "✅ ¡Éxito! El correo ha sido enviado. Revisa tu bandeja de entrada (y la carpeta de Spam).");
        } else {
            JOptionPane.showMessageDialog(null, "❌ Error al enviar. Verifica tu conexión, que la clave de 16 letras sea correcta y que SMTP_AUTH/TLS estén en 'true'.");
        }
    }

    @FXML protected void FnGenerarReporteGrafico(ActionEvent event) {
        Map<String, Object> parameters = new HashMap<>();
        boolean algunoSeleccionado = false;

        if (chkFactura != null && chkFactura.isSelected()) {
            ReportManager.generarReporte("FACTURA", parameters);
            algunoSeleccionado = true;
        }
        if (chkDesglose != null && chkDesglose.isSelected()) {
            ReportManager.generarReporte("Desglose", parameters);
            algunoSeleccionado = true;
        }
        if (chkVentas != null && chkVentas.isSelected()) {
            ReportManager.generarReporte("REPORTE DE VENTAS", parameters);
            algunoSeleccionado = true;
        }
        
        // Actualizar Gráficos Visuales
        actualizarGraficosReportes();

        if (!algunoSeleccionado) {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione al menos un reporte para generar.\n\n" +
                    "Asegúrese de copiar sus archivos .jrprint a 'src/main/resources/reportes/'");
        }
    }

    private void actualizarGraficosReportes() {
        if (chartReportesPie != null) {
            ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
                new PieChart.Data("Ventas Directas", 45),
                new PieChart.Data("Ventas Online", 30),
                new PieChart.Data("Ventas Corporativas", 25)
            );
            chartReportesPie.setData(pieData);
        }

        if (chartReportesBar != null) {
            chartReportesBar.getData().clear();
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Desgloses 2024");
            series.getData().add(new XYChart.Data<>("Ene", 1200));
            series.getData().add(new XYChart.Data<>("Feb", 1800));
            series.getData().add(new XYChart.Data<>("Mar", 1500));
            series.getData().add(new XYChart.Data<>("Abr", 2200));
            chartReportesBar.getData().add(series);
        }
    }

    /**
     * Inicializa los gráficos del Dashboard usando JavaFX nativo
     */
    private void initDashboardCharts() {
        actualizarGraficosDashboard();
        actualizarKPIs();
        if (lblFechaActualizacion != null)
            lblFechaActualizacion.setText("Actualizado: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
    }

    private void actualizarGraficosDashboard() {
        if (chartOrdenesPie != null) {
            ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
            Connection conn = conexion.establecerConexion();
            try (Statement stmt = conn.createStatement(); 
                 ResultSet rs = stmt.executeQuery("SELECT estado, COUNT(*) as total FROM OrdenTrabajo GROUP BY estado")) {
                while (rs.next()) {
                    pieData.add(new PieChart.Data(rs.getString("estado"), rs.getInt("total")));
                }
            } catch (SQLException e) {
                pieData.addAll(new PieChart.Data("Pendiente", 5), new PieChart.Data("En Proceso", 10), new PieChart.Data("Completada", 20));
            }
            chartOrdenesPie.setData(pieData);
        }

        if (chartIngresosBar != null) {
            chartIngresosBar.getData().clear();
            XYChart.Series<String, Number> seriesIngresos = new XYChart.Series<>();
            seriesIngresos.setName("Ingresos");
            XYChart.Series<String, Number> seriesGastos = new XYChart.Series<>();
            seriesGastos.setName("Gastos");

            String[] meses = {"Ene", "Feb", "Mar", "Abr", "May", "Jun"};
            for (int i = 1; i <= 6; i++) {
                seriesIngresos.getData().add(new XYChart.Data<>(meses[i-1], obtenerMontoReal("Ingreso", i)));
                seriesGastos.getData().add(new XYChart.Data<>(meses[i-1], obtenerMontoReal("Gasto", i)));
            }
            chartIngresosBar.getData().addAll(seriesIngresos, seriesGastos);
        }

        if (chartVentasLine != null) {
            chartVentasLine.getData().clear();
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Ventas 2024");
            String[] meses = {"Ene", "Feb", "Mar", "Abr", "May", "Jun"};
            for (int i = 1; i <= 6; i++) {
                series.getData().add(new XYChart.Data<>(meses[i-1], obtenerMontoReal("Ingreso", i)));
            }
            chartVentasLine.getData().add(series);
        }

        if (chartOrdenesMesArea != null) {
            chartOrdenesMesArea.getData().clear();
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Órdenes Realizadas");
            String[] meses = {"Ene", "Feb", "Mar", "Abr", "May", "Jun"};
            Connection conn = conexion.establecerConexion();
            try (PreparedStatement pstmt = conn.prepareStatement("SELECT MONTH(fecha_inicio) as mes, COUNT(*) FROM OrdenTrabajo GROUP BY MONTH(fecha_inicio)")) {
                ResultSet rs = pstmt.executeQuery();
                Map<Integer, Integer> dataMap = new HashMap<>();
                while (rs.next()) dataMap.put(rs.getInt(1), rs.getInt(2));
                for (int i = 1; i <= 6; i++) {
                    series.getData().add(new XYChart.Data<>(meses[i-1], dataMap.getOrDefault(i, 0)));
                }
            } catch (SQLException e) {}
            chartOrdenesMesArea.getData().add(series);
        }

        if (chartTopProductosBar != null) {
            chartTopProductosBar.getData().clear();
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Top Productos");
            series.getData().add(new XYChart.Data<>("Ventana P65", 15));
            series.getData().add(new XYChart.Data<>("Puerta Corrediza", 12));
            series.getData().add(new XYChart.Data<>("Fijo Baño", 8));
            chartTopProductosBar.getData().add(series);
        }

        if (chartCategoriasPie != null) {
            ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
                new PieChart.Data("Residencial", 60),
                new PieChart.Data("Comercial", 25),
                new PieChart.Data("Industrial", 15)
            );
            chartCategoriasPie.setData(pieData);
        }
    }

    private double obtenerMontoReal(String tipo, int mes) {
        Connection conn = conexion.establecerConexion();
        try (PreparedStatement pstmt = conn.prepareStatement("SELECT SUM(monto) FROM Contabilidad WHERE tipo=? AND MONTH(fecha)=?")) {
            pstmt.setString(1, tipo);
            pstmt.setInt(2, mes);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getDouble(1);
        } catch (Exception e) {}
        return 0.0;
    }

    @FXML public void actualizarDashboard() {
        initDashboardCharts();
    }

    private void actualizarKPIs() {
        Connection conn = conexion.establecerConexion();
        try (Statement stmt = conn.createStatement()) {
            // Ventas Totales
            ResultSet rs = stmt.executeQuery("SELECT ISNULL(SUM(monto), 0) FROM Contabilidad WHERE tipo = 'Ingreso'");
            double ventas = rs.next() ? rs.getDouble(1) : 0;
            if (lblVentasTotales != null) lblVentasTotales.setText(String.format("$%,.0f", ventas));
            
            // Utilidad Neta
            rs = stmt.executeQuery("SELECT ISNULL(SUM(monto), 0) FROM Contabilidad WHERE tipo = 'Gasto'");
            double gastos = rs.next() ? rs.getDouble(1) : 0;
            double utilidad = ventas - gastos;
            if (lblUtilidadNeta != null) lblUtilidadNeta.setText(String.format("$%,.0f", utilidad));
            
            // Margen
            double margen = ventas > 0 ? (utilidad / ventas) * 100 : 0;
            if (lblVentasTrend != null) lblVentasTrend.setText(String.format("+%.1f%%", margen));
            
            // Órdenes
            rs = stmt.executeQuery("SELECT COUNT(*) FROM OrdenTrabajo");
            if (lblOrdenesTotales != null) lblOrdenesTotales.setText(String.valueOf(rs.next() ? rs.getInt(1) : 0));
            
            // Clientes
            rs = stmt.executeQuery("SELECT COUNT(*) FROM Cliente");
            if (lblClientesActivos != null) lblClientesActivos.setText(String.valueOf(rs.next() ? rs.getInt(1) : 0));
            
            // Eficiencia
            rs = stmt.executeQuery("SELECT (SELECT COUNT(*) FROM OrdenTrabajo WHERE estado = 'Completada') * 100.0 / NULLIF(COUNT(*), 0) FROM OrdenTrabajo");
            if (lblEficiencia != null) lblEficiencia.setText(String.format("%.1f%%", rs.next() ? rs.getDouble(1) : 0));
            
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // ==================== MÉTODOS PARA ACTIVIDAD DEL SISTEMA ====================

    /**
     * Carga las actividades desde la base de datos y las muestra en el panel
     */
    private void cargarActividades() {
        System.out.println("🔍 Iniciando carga de actividades...");
        
        if (contenedorActividad == null) {
            System.out.println("⚠️ contenedorActividad es null - verificar fx:id en el FXML");
            return;
        }
        
        String sql = "SELECT TOP 15 mensaje, usuario, " +
                     "CASE " +
                     "  WHEN DATEDIFF(MINUTE, fecha_hora, GETDATE()) < 1 THEN 'unos segundos' " +
                     "  WHEN DATEDIFF(MINUTE, fecha_hora, GETDATE()) < 60 " +
                     "    THEN CAST(DATEDIFF(MINUTE, fecha_hora, GETDATE()) AS VARCHAR) + ' minutos' " +
                     "  WHEN DATEDIFF(HOUR, fecha_hora, GETDATE()) < 24 " +
                     "    THEN CAST(DATEDIFF(HOUR, fecha_hora, GETDATE()) AS VARCHAR) + ' horas' " +
                     "  ELSE CAST(DATEDIFF(DAY, fecha_hora, GETDATE()) AS VARCHAR) + ' días' " +
                     "END as tiempo " +
                     "FROM ACTIVIDAD_FEED ORDER BY fecha_hora DESC";
        
        System.out.println("🔍 Ejecutando SQL: " + sql);
        
        Connection conn = conexion.establecerConexion();
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            
            contenedorActividad.getChildren().clear();
            int contador = 0;
            
            while (rs.next()) {
                String mensaje = rs.getString("mensaje");
                String usuario = rs.getString("usuario");
                String tiempo = rs.getString("tiempo");
                
                System.out.println("📝 Actividad: " + mensaje + " | " + usuario + " | " + tiempo);
                
                VBox item = crearItemActividad(mensaje, usuario, tiempo);
                contenedorActividad.getChildren().add(item);
                contador++;
            }
            
            if (lblTotalActividades != null) {
                lblTotalActividades.setText("Mostrando " + contador + " registros de actividad");
            }
            
            System.out.println("✅ Cargadas " + contador + " actividades");
            
        } catch (SQLException e) {
            System.err.println("❌ Error al cargar actividades: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Crea un item visual para cada actividad
     */
    private VBox crearItemActividad(String mensaje, String usuario, String tiempo) {
        VBox item = new VBox();
        item.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-padding: 15; " +
                      "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 5, 0, 0, 2);");
        item.setSpacing(8);
        
        // Icono según tipo de mensaje
        HBox headerBox = new HBox();
        headerBox.setSpacing(10);
        headerBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        
        Label iconLabel = new Label();
        iconLabel.setStyle("-fx-font-size: 18px;");
        
        if (mensaje.contains("Material") || mensaje.toLowerCase().contains("inventario")) {
            iconLabel.setText("📦");
        } else if (mensaje.contains("Orden") || mensaje.toLowerCase().contains("orden")) {
            iconLabel.setText("🔧");
        } else if (mensaje.contains("Código") || mensaje.toLowerCase().contains("desglose")) {
            iconLabel.setText("📋");
        } else if (mensaje.contains("Cliente")) {
            iconLabel.setText("👤");
        } else {
            iconLabel.setText("📌");
        }
        
        Label mensajeLabel = new Label(mensaje);
        mensajeLabel.setWrapText(true);
        mensajeLabel.setStyle("-fx-text-fill: #1a202c; -fx-font-size: 13px;");
        
        Label infoLabel = new Label("👤 " + usuario + "  •  🕐 Hace " + tiempo);
        infoLabel.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 11px;");
        
        headerBox.getChildren().addAll(iconLabel, mensajeLabel);
        item.getChildren().addAll(headerBox, infoLabel);
        
        return item;
    }

    /**
     * Refresca manualmente la lista de actividades
     */
    @FXML
    public void refrescarActividades() {
        cargarActividades();
    }

    /**
     * Registra una nueva actividad en la base de datos
     * LLAMAR ESTE MÉTODO DESDE CADA CRUD (Guardar, Editar, Borrar)
     */
    public void registrarActividad(String mensaje, String usuario) {
        String sql = "INSERT INTO ACTIVIDAD_FEED (mensaje, usuario, fecha_hora) VALUES (?, ?, GETDATE())";
        
        Connection conn = conexion.establecerConexion();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, mensaje);
            pstmt.setString(2, usuario != null ? usuario : sessionManager.getUsername());
            pstmt.executeUpdate();
            
            // Recargar actividades para mostrar el nuevo registro
            cargarActividades();
            
            System.out.println("✅ Actividad registrada: " + mensaje);
            
        } catch (SQLException e) {
            System.err.println("❌ Error al registrar actividad: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Obtiene el usuario actual de la sesión
     */
    private String getUsuarioActual() {
        String usuario = sessionManager.getUsername();
        return usuario != null && !usuario.isEmpty() ? usuario : "Admin";
    }

    /**
     * Inicializa el footer con hora en tiempo real de República Dominicana
     */
    private void initFooter() {
        if (lblHoraRD == null) {
            System.out.println("⚠️ lblHoraRD es null - footer no disponible en esta vista");
            return;
        }

        // Configurar zona horaria de República Dominicana (UTC-4)
        java.time.ZoneId zonaRD = java.time.ZoneId.of("America/Santo_Domingo");

        // Actualizar hora cada segundo
        horaTimeline = new javafx.animation.Timeline(
            new javafx.animation.KeyFrame(javafx.util.Duration.seconds(1), event -> {
                java.time.ZonedDateTime ahoraRD = java.time.ZonedDateTime.now(zonaRD);
                String horaFormateada = ahoraRD.format(java.time.format.DateTimeFormatter.ofPattern("hh:mm:ss a"));
                lblHoraRD.setText(horaFormateada);
            })
        );
        horaTimeline.setCycleCount(javafx.animation.Animation.INDEFINITE);
        horaTimeline.play();

        // Establecer fecha de última conexión
        if (lblUltimaConexion != null) {
            java.time.LocalDateTime ahora = java.time.LocalDateTime.now();
            lblUltimaConexion.setText(ahora.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        }

        System.out.println("✅ Footer inicializado con hora RD");
    }

    /**
     * Detener el timeline al cerrar la aplicación
     */
    public void detenerTimeline() {
        if (horaTimeline != null) {
            horaTimeline.stop();
        }
    }

    @FXML protected void FnFiltrarActividad(ActionEvent event) { /* Lógica */ }
    @FXML protected void FnLimpiarActividad(ActionEvent event) { /* Lógica */ }
    @FXML protected void FnExportarActividad(ActionEvent event) { /* Lógica */ }
    @FXML protected void FnCerrarSesion(ActionEvent event) { System.exit(0); }
}
