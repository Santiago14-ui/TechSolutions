package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Clase principal de la aplicación SmartGym.
 *
 * Se encarga de iniciar la aplicación JavaFX
 * y cargar el menú principal del sistema.
 */
public class App extends Application {

    /**
     * Inicia la aplicación JavaFX.
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(App.class.getResource("/css/TechSolutions.css").toExternalForm());
        stage.setTitle("TechSolutions - sistema de gestion");
        stage.setMinWidth(1050);
        stage.setMinHeight(700);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Punto de entrada de la aplicación.
     */
    public static void main(String[] args) {launch();}
}