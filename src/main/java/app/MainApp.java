package app;

import javafx.application.Application;
import javafx.stage.Stage;
import vista.VistaPrincipal;


public class MainApp extends Application {

    @SuppressWarnings("exports")
    @Override
    public void start(Stage stagePrimario) {
        VistaPrincipal vista = new VistaPrincipal(stagePrimario);
        vista.mostrar();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
