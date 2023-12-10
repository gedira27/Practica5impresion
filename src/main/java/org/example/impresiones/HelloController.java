package org.example.impresiones;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {

    private Label welcomeText;

    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}