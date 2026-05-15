package besededatos;

import besededatos.models.*;
import besededatos.controllers.*;
import besededatos.config.*;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication2 extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication2.class.getResource("/Basededatos/hello-view.fxml")
        );

        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("INDUSTRIA R.G - Sistema de Gestión Integral");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}