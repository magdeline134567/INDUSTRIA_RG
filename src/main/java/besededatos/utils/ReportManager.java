package besededatos.utils;

import besededatos.config.Conexion;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Map;

public class ReportManager {

    /**
     * Carga y muestra un archivo .jrprint ya generado.
     * 
     * @param reportName Nombre del archivo .jrprint (sin extensión) dentro de /reportes/
     */
    public static void generarReporte(String reportName, Map<String, Object> parameters) {
        try {
            // 1. Cargar el archivo .jrprint
            String reportPath = "/reportes/" + reportName + ".jrprint";
            InputStream reportStream = ReportManager.class.getResourceAsStream(reportPath);

            if (reportStream == null) {
                System.err.println("Archivo .jrprint no encontrado en: " + reportPath);
                System.err.println("Asegúrese de colocar sus archivos .jrprint en src/main/resources/reportes/");
                return;
            }

            // 2. Cargar el objeto JasperPrint desde el archivo
            JasperPrint jasperPrint = (JasperPrint) net.sf.jasperreports.engine.util.JRLoader.loadObject(reportStream);

            // 3. Mostrar el reporte en el visor
            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setTitle("Visor de Reportes - " + reportName);
            viewer.setVisible(true);

        } catch (JRException e) {
            e.printStackTrace();
            System.err.println("Error al cargar el archivo .jrprint: " + e.getMessage());
        }
    }
}
