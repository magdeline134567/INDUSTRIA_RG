package besededatos.utils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import java.io.File;
import java.io.IOException;

public class ChartGenerator {

    public static void generarGraficoStock() {
        // Crear dataset con datos ficticios o para conectar a la DB luego
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Motor Eléctrico", 15);
        dataset.setValue("Rodamiento SKF", 150);
        dataset.setValue("Taladro Percutor", 30);
        dataset.setValue("Sensor Inductivo", 45);

        // Crear el gráfico usando JFreeChart
        JFreeChart chart = ChartFactory.createPieChart(
                "Stock de Productos - INDUSTRIA_RG", // Título
                dataset, // Datos
                true,    // Leyenda
                true,    // Tooltips
                false    // URLs
        );

        try {
            // Guardar el gráfico como una imagen PNG
            File imageFile = new File("reporte_stock.png");
            ChartUtils.saveChartAsPNG(imageFile, chart, 800, 600);
            System.out.println("✅ Gráfico generado exitosamente en: " + imageFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("❌ Error al generar el gráfico: " + e.getMessage());
        }
    }
}
