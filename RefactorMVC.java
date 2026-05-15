import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class RefactorMVC {
    public static void main(String[] args) throws IOException {
        String baseDir = "src/main/java/besededatos";
        String resourcesDir = "src/main/resources/Basededatos";
        String moduleInfo = "src/main/java/module-info.java";

        List<String> models = Arrays.asList("Cliente.java", "ClienteCompleto.java", "Compra.java", "ConfiguracionItem.java", "Desglose.java", "Instalacion.java", "InventarioItem.java", "MovimientoContable.java", "NominaItem.java", "OrdenTrabajo.java", "TecnicoCompleto.java", "Usuario.java");
        List<String> controllers = Arrays.asList("ClienteController.java", "MainController.java", "MenuController.java");
        List<String> config = Arrays.asList("Conexion.java", "Conexion2.java", "SessionManager.java");

        new File(baseDir + "/models").mkdirs();
        new File(baseDir + "/controllers").mkdirs();
        new File(baseDir + "/config").mkdirs();

        for (String file : models) {
            Path src = Paths.get(baseDir, file);
            Path dst = Paths.get(baseDir, "models", file);
            if (Files.exists(src)) {
                String content = new String(Files.readAllBytes(src), StandardCharsets.UTF_8);
                content = content.replace("package besededatos;", "package besededatos.models;\n\nimport besededatos.config.*;\n");
                Files.write(dst, content.getBytes(StandardCharsets.UTF_8));
                Files.delete(src);
            }
        }

        for (String file : controllers) {
            Path src = Paths.get(baseDir, file);
            Path dst = Paths.get(baseDir, "controllers", file);
            if (Files.exists(src)) {
                String content = new String(Files.readAllBytes(src), StandardCharsets.UTF_8);
                content = content.replace("package besededatos;", "package besededatos.controllers;\n\nimport besededatos.models.*;\nimport besededatos.config.*;\nimport besededatos.utils.*;\n");
                Files.write(dst, content.getBytes(StandardCharsets.UTF_8));
                Files.delete(src);
            }
        }

        for (String file : config) {
            Path src = Paths.get(baseDir, file);
            Path dst = Paths.get(baseDir, "config", file);
            if (Files.exists(src)) {
                String content = new String(Files.readAllBytes(src), StandardCharsets.UTF_8);
                content = content.replace("package besededatos;", "package besededatos.config;\n\nimport besededatos.models.*;\n");
                Files.write(dst, content.getBytes(StandardCharsets.UTF_8));
                Files.delete(src);
            }
        }

        // Remaining files in baseDir (like HelloApplication.java)
        File[] remaining = new File(baseDir).listFiles((dir, name) -> name.endsWith(".java"));
        if (remaining != null) {
            for (File file : remaining) {
                Path src = file.toPath();
                String content = new String(Files.readAllBytes(src), StandardCharsets.UTF_8);
                if (content.contains("package besededatos;")) {
                    content = content.replace("package besededatos;", "package besededatos;\n\nimport besededatos.models.*;\nimport besededatos.controllers.*;\nimport besededatos.config.*;\n");
                    Files.write(src, content.getBytes(StandardCharsets.UTF_8));
                }
            }
        }

        // FXML files
        File[] fxmls = new File(resourcesDir).listFiles((dir, name) -> name.endsWith(".fxml"));
        if (fxmls != null) {
            for (File fxml : fxmls) {
                Path src = fxml.toPath();
                String content = new String(Files.readAllBytes(src), StandardCharsets.UTF_8);
                content = content.replace("fx:controller=\"besededatos.MainController\"", "fx:controller=\"besededatos.controllers.MainController\"");
                content = content.replace("fx:controller=\"besededatos.ClienteController\"", "fx:controller=\"besededatos.controllers.ClienteController\"");
                content = content.replace("fx:controller=\"besededatos.MenuController\"", "fx:controller=\"besededatos.controllers.MenuController\"");
                Files.write(src, content.getBytes(StandardCharsets.UTF_8));
            }
        }

        // module-info.java
        Path modPath = Paths.get(moduleInfo);
        if (Files.exists(modPath)) {
            String content = new String(Files.readAllBytes(modPath), StandardCharsets.UTF_8);
            if (!content.contains("opens besededatos.controllers")) {
                String newOpens = "    opens besededatos.controllers to javafx.fxml, javafx.graphics, javafx.base;\n    opens besededatos.models to javafx.base;\n    exports besededatos.controllers;\n    exports besededatos.models;\n    exports besededatos.config;\n    exports besededatos.utils;";
                content = content.replace("    opens besededatos to javafx.fxml, javafx.graphics, javafx.base;", "    opens besededatos to javafx.fxml, javafx.graphics, javafx.base;\n" + newOpens);
                Files.write(modPath, content.getBytes(StandardCharsets.UTF_8));
            }
        }

        System.out.println("Refactorizado a MVC exitosamente usando Java.");
    }
}
