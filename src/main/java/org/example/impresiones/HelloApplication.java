package org.example.impresiones;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        HelloController helloController = new HelloController();
        stage.setTitle("Hello!");
        stage.setScene(helloController.getScene());
        stage.show();
        // Método para asegurarnos de cerrar completamente el botón al cerrar la ventana
        stage.setOnCloseRequest(event -> {
            // Realizar operaciones de cierre aquí
            System.out.println("Cerrando la aplicación");

            // Asegúrate de que la aplicación se cierre completamente
            Platform.exit();
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        launch();
    }
}