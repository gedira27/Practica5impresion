package org.example.impresiones;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class HelloController extends Stage{
    private int contador = 1;
    private BorderPane borderPane;
    private Label statusIndicator;
    public HelloController() {
        crearUI();
        Scene scene = new Scene(borderPane);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/org/example/appimpresiones/estilos/styles.css")).toExternalForm());
        this.setScene(scene);
    }

    private void crearUI() {
        // Creando un objeto de la clase TableViewExample
        VistaTabla tableViewExample = new VistaTabla();
        // Creando un objeto BorderPane
        borderPane = new BorderPane();

        // Colocando la tabla en la posición Center del BorderPane
        borderPane.setCenter(VistaTabla.tabla);

        // Crear un botón para agregar nuevos elementos
        Button addButton = new Button("Agregar Nuevo Elemento");
        // Agregando un evento al botón
        addButton.setOnAction(e -> {
            // Creando un objeto DataItem para la tabla
            VistaTabla.DataItem dataItem = new VistaTabla.DataItem(
                    contador,
                    generarNombre(),
                    ((int)(Math.random() * 100)),
                    LocalDate.now(),
                    0.0
            );
            // Agregando el objeto a un ArrayList tipo Queue (Cola)
            VistaTabla.listaProcesos.add(dataItem);
            // // Agregando el objeto a la tabla
            VistaTabla.tabla.getItems().add(dataItem);
            // aumentando el contador del id
            contador++;
        });

        // Crear un botón para pausar las barras de progreso
        Button pauseButton = new Button("Pausar/Reanudar");
        // Agregando un evento al botón
        pauseButton.setOnAction(e -> {
            // Activar | Desactivar los procesos
            tableViewExample.on();
            // Cambiando el color del punto para saber si está on u off
            // OFF
            if (tableViewExample.isFlag()) {
                statusIndicator.setText("●");
                statusIndicator.setTextFill(Color.RED);
            } else {
                // ON
                statusIndicator.setText("●");
                statusIndicator.setTextFill(Color.GREEN);
            }
        });

        // Crear el layout para mostrar el estado del servicio
        HBox statusBox = new HBox();

        // Creando una etiqueta para el texto
        Label statusLabel = new Label("Estado: ");
        // Creando una etiqueta para almacenar el botón de estado
        statusIndicator = new Label("●");
        statusIndicator.setTextFill(Color.GREEN);

        // Agregando la etiqueta
        statusBox.getChildren().addAll(statusLabel, statusIndicator);

        // Crear el diseño principal
        VBox root = new VBox();
        root.setSpacing(10);

        // Agregando todo a un vbox
        root.getChildren().addAll(addButton, pauseButton, statusBox);

        // Agregando el vbox a el bottom del BorderPane
        borderPane.setBottom(root);

        // Inicia el Thread para manejar procesos
        ///tableViewExample.start();
    }

    // método para generar nombres
    private String generarNombre() {
        String[] extensiones = {"pdf", "jpeg", "jpg", "png", "jpe", "tiff", "tif", "docx", "xlsx", "pptx"};
        // generar numero random para seleccionar la extension
        int idExtension = (int) (Math.random() * extensiones.length);
        // generar fecha y hora
        // Obtener la fecha y hora actual
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Formatear la fecha y hora
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        return formattedDateTime+extensiones[idExtension];
    }
}