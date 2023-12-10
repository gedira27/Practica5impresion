module org.example.impresiones {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.impresiones to javafx.fxml;
    exports org.example.impresiones;
}