package org.example.impresiones;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ElementoTabla {
    private final int numHojas;
    private final Integer no;
    private final String nombre;
    private final DoubleProperty progress;
    private String hojas;
    private final Integer time = 10;
    public ElementoTabla(Integer no, double progress, int hojas) {
        this.no = no;
        this.nombre = generarNombre();
        this.progress = new SimpleDoubleProperty(progress);
        this.hojas = hojas+"";
        this.numHojas = hojas;
    }

    public String getName() {
        return nombre;
    }

    public Double getProgress() {
        return progress.get();
    }

    public DoubleProperty progressProperty() {
        return progress;
    }

    public Integer getNo() {
        return no;
    }

    public String getNombre() {
        return nombre;
    }

    public String getHojas() {
        return hojas;
    }

    public void setProgress(double progress) {
        this.progress.set(progress);
    }

    public void onAnimation() {
        animate();
    }

    private void animate() {
        // Se cambia el proceso a que ya existe una animación en proceso
        ButtonThread.onAnimation = true;
        // Se crea la animacion con 10s
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(progress, 0.0)),
                new KeyFrame(Duration.seconds(time), new KeyValue(progress, 1.0))
        );
        // Se configura el proceso final cuando termina la animación
        timeline.setOnFinished(event -> {
            // se cambia la variable para saber que no hay ninguna animación actualmente
            ButtonThread.onAnimation = false;
            // método poll para traer el elemento cabeza y eliminarlo
            // Se elimina el elemento de la tabla
            TableViewWithProgressIndicator.tabla.getItems().remove(ButtonThread.listaElementos.poll());
        });

        // Animación para cambiar hojas (no sirve)
        Timeline labelUpdateTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> updateHojas(0)),
                new KeyFrame(Duration.seconds(1), e -> updateHojas(.1)),
                new KeyFrame(Duration.seconds(2), e -> updateHojas(.2)),
                new KeyFrame(Duration.seconds(3), e -> updateHojas(.30)),
                new KeyFrame(Duration.seconds(4), e -> updateHojas(.40)),
                new KeyFrame(Duration.seconds(5), e -> updateHojas(.50)),
                new KeyFrame(Duration.seconds(6), e -> updateHojas(.60)),
                new KeyFrame(Duration.seconds(7), e -> updateHojas(.70)),
                new KeyFrame(Duration.seconds(8), e -> updateHojas(.80)),
                new KeyFrame(Duration.seconds(9), e -> updateHojas(.90)),
                new KeyFrame(Duration.seconds(1), e -> updateHojas(1.0))
        );

        labelUpdateTimeline.setCycleCount(1);

        // se configura para que la animación nada más se realice 1 vez
        timeline.setCycleCount(1);
        // se inicia la animación
        timeline.play();
        labelUpdateTimeline.play();
    }

    private void updateHojas(double value) {
        // System.out.println("HACE ALGO");
        this.hojas = ((int)(numHojas*value))+"/"+numHojas;
        // System.out.println(hojas);
    }

    private String generarNombre() {
        String[] extensiones = {"png", "jpe", "xlsx", "pptx","pdf", "jpeg", "jpg", "tiff", "tif", "docx",};
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
