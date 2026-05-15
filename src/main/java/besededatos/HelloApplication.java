package besededatos;

import besededatos.models.*;
import besededatos.controllers.*;
import besededatos.config.*;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/pantallas/logincliente.fxml"));

        if (fxmlLoader.getLocation() == null) {
            System.err.println("ERROR: No se encuentra logincliente.fxml");
            System.exit(1);
        }

        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("INDUSTRIA R.G - Sistema de Gestión Integral");
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}