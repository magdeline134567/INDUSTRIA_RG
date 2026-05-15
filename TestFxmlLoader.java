import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class TestFxmlLoader extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        File file = new File("src/main/resources/Basededatos/hello-view.fxml");
        URL location = file.toURI().toURL();
        System.out.println("Cargando: " + location);
        try {
            FXMLLoader loader = new FXMLLoader(location);
            loader.load();
            System.out.println("CARGA EXITOSA");
            System.exit(0);
        } catch (Exception e) {
            System.out.println("ERROR AL CARGAR:");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
