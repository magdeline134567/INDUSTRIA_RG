import java.nio.file.*;
import java.util.regex.*;

public class RebuildForms {
    public static void main(String[] args) throws Exception {
        Path path = Paths.get("src/main/resources/Basededatos/hello-view.fxml");
        String content = new String(Files.readAllBytes(path), "UTF-8");

        // We replace everything between 
        // <!-- RIGHT PANEL: FORMULARIO -->
        // <VBox ...>
        // ... (label & separator)
        // AND
        // </VBox>
        // </HBox>
        
        // This regex perfectly captures the inside of the RIGHT PANEL where we broke the GridPane
        Pattern p = Pattern.compile("(<!-- RIGHT PANEL: FORMULARIO -->\\s*<VBox style=\"-fx-padding: 40; -fx-spacing: 25;\" HBox\\.hgrow=\"ALWAYS\">\\s*<Label[^>]+/>\\s*<Separator[^>]+/>\\s*)([\\s\\S]*?)(</VBox>\\s*</HBox>\\s*</HBox>\\s*</ScrollPane>\\s*</Tab>)");
        Matcher m = p.matcher(content);
        
        StringBuffer sb = new StringBuffer();
        
        String[] forms = new String[] {
            // Note: Tab 0 was Desglose, it might not be matched if it was fully correct, or if it is matched, we should keep it.
            // But actually we want to rewrite all EXCEPT Desglose.
            "DESGLOSE", "ORDENES", "INVENTARIO", "COMPRAS", "NOMINA", "CONTABILIDAD", "CLIENTES", "TECNICOS", "USUARIOS", "INSTALACIONES"
        };
        
        int idx = 0;
        while (m.find()) {
            String pre = m.group(1);
            String existingForm = m.group(2);
            String post = m.group(3);
            
            // If it's Desglose (has txtCodigo and txtAncho), keep it!
            if (existingForm.contains("txtAncho")) {
                m.appendReplacement(sb, Matcher.quoteReplacement(m.group(0)));
                idx++;
                continue;
            }
            
            String newForm = "";
            if (existingForm.contains("txtCodigoOrden")) {
                newForm = buildGrid("Código", "txtCodigoOrden", "Fecha Inicio", "dpFechaInicio",
                                    "Fecha Estimada", "dpFechaEstimada", "Observaciones", "txtObservaciones",
                                    "Cliente", "cmbClienteOrden", "Desglose", "cmbDesglose",
                                    "Técnico", "cmbTecnico", "Estado", "cmbEstadoOrden");
            } else if (existingForm.contains("txtInvNombre") || (idx == 2)) {
                newForm = buildGrid("Nombre", "txtInvNombre", "Cantidad", "txtInvCantidad",
                                    "Costo Unit.", "txtInvCostoUnit", "Precio Unit.", "txtInvPrecioUnit",
                                    "Categoría", "cmbInvCategoria", "Ubicación", "txtInvUbicacion");
            } else if (existingForm.contains("txtCodigoCompra") || (idx == 3)) {
                newForm = buildGrid("Código", "txtCodigoCompra", "Cantidad", "txtCantidad",
                                    "Precio Unit.", "txtPrecioUnitario", "Fecha", "dpFechaEntrega",
                                    "Proveedor", "cmbProveedor", "Material", "cmbMaterial",
                                    "Estado", "cmbEstado");
            } else if (existingForm.contains("txtNomEmpleado") || (idx == 4)) {
                newForm = buildGrid("Empleado", "txtNomEmpleado", "Puesto", "txtNomPuesto",
                                    "Salario Base", "txtNomSalarioBase", "Horas Extras", "txtNomHorasExtras",
                                    "Deducciones", "txtNomDeducciones", "", "");
            } else if (existingForm.contains("dpContFecha") || (idx == 5)) {
                newForm = buildGrid("Fecha", "dpContFecha", "Tipo", "cmbContTipo",
                                    "Sub. Tipo", "txtContSubTipo", "Monto", "txtContMonto",
                                    "Descripción", "txtContDesc", "", "");
            } else if (existingForm.contains("txtCliNombre") || (idx == 6)) {
                newForm = buildGrid("Nombre", "txtCliNombre", "Cédula", "txtCliCedula",
                                    "Teléfono", "txtCliTelefono", "Email", "txtCliEmail",
                                    "Tipo", "cmbCliTipo", "", "");
            } else if (existingForm.contains("txtTecNombre") || (idx == 7)) {
                newForm = buildGrid("Nombre", "txtTecNombre", "Calificación", "txtTecCalificacion",
                                    "Teléfono", "txtTecTelefono", "Especialidad", "cmbTecEspecialidad",
                                    "Disponibilidad", "cmbTecDisponibilidad", "", "");
            } else if (existingForm.contains("txtUsuarioNombre") || (idx == 8)) {
                newForm = buildGrid("Nombre", "txtUsuarioNombre", "Usuario", "txtUsuarioUsername",
                                    "Email", "txtUsuarioEmail", "Contraseña", "txtUsuarioPassword",
                                    "Rol", "cmbUsuarioRol", "Depto", "cmbUsuarioDepto");
            } else if (existingForm.contains("cmbOrdenTrabajo") || (idx == 9)) {
                newForm = buildGrid("Orden", "cmbOrdenTrabajo", "Técnico", "cmbTecnicoInst",
                                    "Estado", "cmbEstadoInst", "Hora", "cmbHora",
                                    "Fecha", "dpFechaInst", "Notas", "taNotas");
            }
            
            // Generate Buttons
            String buttons = "<HBox spacing=\"15\" alignment=\"CENTER_RIGHT\" style=\"-fx-padding: 30 0 0 0;\">\n" +
                             "    <Button text=\"Limpiar\" style=\"-fx-background-color: transparent; -fx-border-color: #3a7ca5; -fx-border-radius: 8; -fx-text-fill: #3a7ca5; -fx-padding: 10 25; -fx-font-weight: bold;\"/>\n" +
                             "    <Button text=\"Guardar Registro\" style=\"-fx-background-color: #27ae60; -fx-text-fill: white; -fx-background-radius: 8; -fx-padding: 10 25; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, rgba(39,174,96,0.3), 10, 0, 0, 4);\"/>\n" +
                             "</HBox>\n";
            
            m.appendReplacement(sb, Matcher.quoteReplacement(pre + newForm + buttons + post));
            idx++;
        }
        m.appendTail(sb);

        Files.write(path, sb.toString().getBytes("UTF-8"));
        System.out.println("Rebuilt " + idx + " forms successfully.");
    }
    
    private static String buildGrid(String... fields) {
        StringBuilder sb = new StringBuilder();
        sb.append("<GridPane hgap=\"20\" vgap=\"15\">\n");
        sb.append("  <columnConstraints>\n");
        sb.append("    <ColumnConstraints minWidth=\"120\" halignment=\"RIGHT\" />\n");
        sb.append("    <ColumnConstraints hgrow=\"ALWAYS\" />\n");
        sb.append("    <ColumnConstraints minWidth=\"120\" halignment=\"RIGHT\" />\n");
        sb.append("    <ColumnConstraints hgrow=\"ALWAYS\" />\n");
        sb.append("  </columnConstraints>\n");
        
        int row = 0;
        for (int i = 0; i < fields.length; i += 4) {
            String lbl1 = fields[i];
            String fx1 = fields[i+1];
            String lbl2 = (i+2 < fields.length) ? fields[i+2] : "";
            String fx2 = (i+3 < fields.length) ? fields[i+3] : "";
            
            if (!lbl1.isEmpty()) {
                sb.append("  <Label text=\"").append(lbl1).append(":\" style=\"-fx-font-weight: bold; -fx-text-fill: #34495e;\" GridPane.columnIndex=\"0\" GridPane.rowIndex=\"").append(row).append("\" />\n");
                sb.append("  ").append(getControl(fx1)).append(" GridPane.columnIndex=\"1\" GridPane.rowIndex=\"").append(row).append("\" />\n");
            }
            if (!lbl2.isEmpty()) {
                sb.append("  <Label text=\"").append(lbl2).append(":\" style=\"-fx-font-weight: bold; -fx-text-fill: #34495e;\" GridPane.columnIndex=\"2\" GridPane.rowIndex=\"").append(row).append("\" />\n");
                sb.append("  ").append(getControl(fx2)).append(" GridPane.columnIndex=\"3\" GridPane.rowIndex=\"").append(row).append("\" />\n");
            }
            row++;
        }
        sb.append("</GridPane>\n");
        return sb.toString();
    }
    
    private static String getControl(String fxId) {
        if (fxId.startsWith("txt") && fxId.toLowerCase().contains("password")) {
            return "<PasswordField fx:id=\"" + fxId + "\" maxWidth=\"Infinity\" style=\"-fx-padding: 8; -fx-background-radius: 6; -fx-border-color: #bdc3c7; -fx-border-radius: 6;\"";
        } else if (fxId.startsWith("txt")) {
            return "<TextField fx:id=\"" + fxId + "\" maxWidth=\"Infinity\" style=\"-fx-padding: 8; -fx-background-radius: 6; -fx-border-color: #bdc3c7; -fx-border-radius: 6;\"";
        } else if (fxId.startsWith("cmb")) {
            return "<ComboBox fx:id=\"" + fxId + "\" maxWidth=\"Infinity\" style=\"-fx-padding: 8; -fx-background-radius: 6; -fx-background-color: #f7f9f9; -fx-border-color: #bdc3c7; -fx-border-radius: 6;\" promptText=\"Seleccionar...\"";
        } else if (fxId.startsWith("dp")) {
            return "<DatePicker fx:id=\"" + fxId + "\" maxWidth=\"Infinity\" style=\"-fx-padding: 8; -fx-background-radius: 6;\"";
        } else if (fxId.startsWith("ta")) {
            return "<TextArea fx:id=\"" + fxId + "\" maxWidth=\"Infinity\" prefRowCount=\"3\" style=\"-fx-padding: 8; -fx-background-radius: 6; -fx-border-color: #bdc3c7; -fx-border-radius: 6;\"";
        }
        return "<TextField fx:id=\"" + fxId + "\" maxWidth=\"Infinity\"";
    }
}
