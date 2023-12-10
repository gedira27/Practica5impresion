package org.example.impresiones;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class TableViewWithProgressIndicator extends Application {
    // Obtener el botón
    private final ButtonThread buttonThread = new ButtonThread();
    public static TableView<ElementoTabla> tabla = new TableView<>();
    private final Integer width = 800;
    public static void main(String[] args) {
        launch(args);
    }

    public TableViewWithProgressIndicator() {
        buttonThread.start();
    }

    @Override
    public void start(Stage primaryStage) {

        tabla.setPrefWidth(width);
        // Configurar el ajuste automático de las columnas
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Crear las columnas
        TableColumn<ElementoTabla, String> numColum = new TableColumn<>("No. Archivo");
        numColum.setCellValueFactory(new PropertyValueFactory<>("no"));

        TableColumn<ElementoTabla, String> nameColumn = new TableColumn<>("Nombre");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<ElementoTabla, String> hojasColumn = new TableColumn<>("Hojas");
        hojasColumn.setCellValueFactory(new PropertyValueFactory<>("hojas"));

        TableColumn<ElementoTabla, Double> progressColumn = new TableColumn<>("Progreso");
        progressColumn.setCellValueFactory(new PropertyValueFactory<>("progress"));

        TableColumn<ElementoTabla, Double> actionColumn = new TableColumn<>("Acción");
        actionColumn.setCellValueFactory(new PropertyValueFactory<>("progress"));
        actionColumn.setCellFactory(createProgressBarCellFactory());

        // Agregar las columnas a la tabla
        tabla.getColumns().addAll(numColum, nameColumn, hojasColumn, progressColumn, actionColumn);

        // Configurar y mostrar la ventana
        VBox vbox = new VBox(new ScrollPane(tabla), buttonThread.hBox);
        vbox.setAlignment(Pos.CENTER);
        vbox.setFillWidth(true);
        vbox.setPadding(new Insets(20));
        Scene scene = new Scene(vbox, width, 600);
        primaryStage.setTitle("Simulación de una impresora");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        // Método para asegurarnos de cerrar completamente el botón al cerrar la ventana
        primaryStage.setOnCloseRequest(event -> {
            // Realizar operaciones de cierre aquí
            System.out.println("Cerrando la aplicación");

            // Asegúrate de que la aplicación se cierre completamente
            Platform.exit();
            System.exit(0);
        });
    }
    
    private Callback<TableColumn<ElementoTabla, Double>, TableCell<ElementoTabla, Double>> createProgressBarCellFactory() {
        return column -> new TableCell<ElementoTabla, Double>() {
            private final ProgressIndicator progressIndicator = new ProgressIndicator();
            {
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                setGraphic(progressIndicator);
            }

            @Override
            protected void updateItem(Double progress, boolean empty) {
                super.updateItem(progress, empty);

                if (empty || progress == null) {
                    setGraphic(null);
                } else {
                    progressIndicator.setProgress(progress);
                }
            }
        };
    }


}

