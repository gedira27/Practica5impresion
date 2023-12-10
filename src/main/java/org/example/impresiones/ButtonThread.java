package org.example.impresiones;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ButtonThread extends Thread{
    public static Queue<ElementoTabla> listaElementos = new ConcurrentLinkedQueue<>();
    private boolean btnActivo = true;
    public static boolean onAnimation = false;
    private Label lblOn;
    public HBox hBox;
    private Integer contador = 1;
    public ButtonThread() {
        Button btnOn = new Button("Pausar/Resumir");
        btnOn.setOnAction(actionEvent -> {
            // si activo está On
            if (btnActivo) {
                lblOn.setText("OFF");
                lblOn.setTextFill(Color.RED);
                btnActivo = false;
            } else {// si no, está apagado
                lblOn.setText("ON");
                lblOn.setTextFill(Color.GREEN);
                btnActivo = true;
            }
        });

        Button btnAdd = new Button("Agregar una impresion");
        btnAdd.setOnAction(actionEvent -> {
            // Agregar datos de ejemplo
            ElementoTabla t1 = new ElementoTabla(contador, 0.0, ((int) (Math.random()*100)));
            TableViewWithProgressIndicator.tabla.getItems().add(t1);
            listaElementos.add(t1);
            contador++;
        });

        lblOn = new Label("On");
        lblOn.setTextFill(Color.GREEN);

        hBox = new HBox(btnOn, lblOn, btnAdd);
        hBox.setSpacing(30);
    }
    @Override
    public void run()
    {
        super.run();
        while(true) {
            System.out.println("Entro");
            // si el botón está en on
            // si el arreglo de elemntos está on (si hay valores en la tabla)
            // si no hay una animación existente
            // entonces activa la siguiente animación
            if(btnActivo && !listaElementos.isEmpty() && !onAnimation) {
                Platform.runLater(() -> {
                    assert listaElementos.peek() != null;
                    listaElementos.peek().onAnimation();
                });
            }

            // espera 1s
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
