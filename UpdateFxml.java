import java.nio.file.*;
import java.io.IOException;

public class UpdateFxml {
    public static void main(String[] args) {
        try {
            Path path = Paths.get("src/main/resources/Basededatos/hello-view.fxml");
            String content = new String(Files.readAllBytes(path), "UTF-8");
            
            // Regex to find start node VBox
            content = content.replaceFirst(
                "<VBox stylesheets=\"@styles.css\"\\s+style=\"[^\"]+\"\\s+xmlns=\"http://javafx.com/javafx/25\"\\s+xmlns:fx=\"http://javafx.com/fxml/1\"\\s+fx:controller=\"besededatos.MainController\">\\s+<padding><Insets bottom=\"15\" left=\"15\" right=\"15\" top=\"15\" /></padding>\\s+<children>",
                "<HBox stylesheets=\"@styles.css\" xmlns=\"http://javafx.com/javafx/25\" xmlns:fx=\"http://javafx.com/fxml/1\" fx:controller=\"besededatos.MainController\">\n    <children>\n        <!-- MENÚ LATERAL -->\n        <fx:include source=\"SideMenu.fxml\" />\n        <!-- CONTENIDO PRINCIPAL -->\n        <VBox HBox.hgrow=\"ALWAYS\" style=\"-fx-background-color: linear-gradient(to bottom, #f0f7ff, #e3f2fd);\">\n            <padding><Insets bottom=\"15\" left=\"15\" right=\"15\" top=\"15\" /></padding>\n            <children>"
            );
            
            // Remove everything between <!-- ===== HEADER ===== --> and <!-- ===== TAB PANE ===== -->
            content = content.replaceAll("(?s)\\s*<!-- ===== HEADER ===== -->.*?<!-- ===== TAB PANE ===== -->", "\n            <!-- ===== TAB PANE ===== -->");
            
            // Replace the last </VBox> with </VBox>\n    </children>\n</HBox>
            int lastIndex = content.lastIndexOf("</VBox>");
            if (lastIndex != -1) {
                content = content.substring(0, lastIndex) + "</VBox>\n            </children>\n        </VBox>\n    </children>\n</HBox>" + content.substring(lastIndex + 7);
            }
            
            Files.write(path, content.getBytes("UTF-8"));
            System.out.println("Done updating FXML");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
