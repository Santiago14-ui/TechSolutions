package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage ventana) {
        Label titulo = new Label("TechSolutions");
        Label mensaje = new Label("Sistema de gestión de servicios técnicos");

        VBox contenedor = new VBox(15);
        contenedor.getChildren().addAll(titulo, mensaje);
        contenedor.setStyle("-fx-padding: 30; -fx-alignment: center;");

        Scene escena = new Scene(contenedor, 600, 400);

        ventana.setTitle("TechSolutions");
        ventana.setScene(escena);
        ventana.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
