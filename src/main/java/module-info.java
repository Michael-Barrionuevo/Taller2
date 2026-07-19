module name {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires java.desktop;

    // Esto permite que el motor de JavaFX inicie tu MainApp
    opens app to javafx.graphics, javafx.fxml;
    
    // Esto es crucial si VistaPrincipal está en otro paquete
    opens vista to javafx.graphics, javafx.fxml;

    exports app;
}
