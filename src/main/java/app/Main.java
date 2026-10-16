package app;

import adapter.TecnicoInfo;
import controller.ControladorPaquetes;
import controller.ControladorPrincipal;
import controller.ControladorSolicitudes;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.ServicioTecnologico;
import view.VistaPaquetes;
import view.VistaSolicitudes;

/**
 * Clase principal de TechSolutions.
 * Construye la ventana inicial y permite acceder a los módulos.
 */
public class Main extends Application {

    private ControladorPrincipal controlador;
    private ControladorPaquetes controladorPaquetes;

    /**
     * Inicia la aplicación JavaFX.
     *
     * @param ventana ventana principal
     */
    @Override
    public void start(Stage ventana) {

        controlador = new ControladorPrincipal();

        controladorPaquetes = new ControladorPaquetes(
                controlador.getPaqueteService()
        );

        Label titulo = new Label("TechSolutions");
        titulo.setStyle(
                "-fx-font-size: 30px; "
                        + "-fx-font-weight: bold; "
                        + "-fx-text-fill: #23395B;"
        );

        Label subtitulo = new Label(
                "Sistema de gestión de servicios tecnológicos"
        );
        subtitulo.setStyle(
                "-fx-font-size: 14px; "
                        + "-fx-text-fill: #526477;"
        );
        subtitulo.setWrapText(true);

        Label etiquetaCatalogo = new Label(
                "Servicios disponibles"
        );
        etiquetaCatalogo.setStyle(
                "-fx-font-size: 18px; "
                        + "-fx-font-weight: bold; "
                        + "-fx-text-fill: #23395B;"
        );

        VBox listaServicios = new VBox(8);

        for (ServicioTecnologico servicioTecnologico :
                controlador.getCatalogoService().obtenerServicios()) {

            Label informacion = new Label(
                    servicioTecnologico.getNombre()
                            + " | $"
                            + String.format(
                            "%,.0f",
                            servicioTecnologico.calcularCosto()
                    )
            );

            informacion.setWrapText(true);
            informacion.setMaxWidth(Double.MAX_VALUE);
            informacion.setPadding(new Insets(10));

            informacion.setStyle(
                    "-fx-background-color: white; "
                            + "-fx-border-color: #D6DCE5; "
                            + "-fx-border-radius: 5; "
                            + "-fx-background-radius: 5;"
            );

            listaServicios.getChildren().add(informacion);
        }

        Button botonPaquetes = new Button("Gestionar paquetes");
        botonPaquetes.setMaxWidth(Double.MAX_VALUE);

        Button botonSolicitudes = new Button("Registrar solicitudes");
        botonSolicitudes.setMaxWidth(Double.MAX_VALUE);

        Button botonTecnicos = new Button("Consultar técnicos");
        botonTecnicos.setMaxWidth(Double.MAX_VALUE);

        String estiloBoton =
                "-fx-background-color: #1976D2; "
                        + "-fx-text-fill: white; "
                        + "-fx-font-weight: bold; "
                        + "-fx-padding: 11 15 11 15; "
                        + "-fx-background-radius: 5;";

        botonPaquetes.setStyle(estiloBoton);
        botonSolicitudes.setStyle(estiloBoton);
        botonTecnicos.setStyle(estiloBoton);

        /*
         * Abre la ventana de gestión de paquetes.
         */
        botonPaquetes.setOnAction(event -> {
            VistaPaquetes vistaPaquetes = new VistaPaquetes(
                    controladorPaquetes,
                    controlador.getCatalogoService()
            );

            vistaPaquetes.mostrar();
        });

        /*
         * Abre la ventana de registro de solicitudes.
         */
        botonSolicitudes.setOnAction(event -> {
            ControladorSolicitudes controladorSolicitudes =
                    new ControladorSolicitudes(
                            controlador.getCatalogoService(),
                            controlador.getClienteService(),
                            controlador.getPaqueteService(),
                            controlador.getSolicitudService(),
                            controlador.getTecnicoService()
                    );

            VistaSolicitudes vistaSolicitudes =
                    new VistaSolicitudes(controladorSolicitudes);

            vistaSolicitudes.mostrar();
        });

        /*
         * Consulta la disponibilidad de técnicos.
         */
        botonTecnicos.setOnAction(event -> {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);

            alerta.setTitle("Consulta de técnicos");
            alerta.setHeaderText("Técnicos disponibles");

            StringBuilder mensaje = new StringBuilder();


            TecnicoInfo tecnico = controlador.getTecnicoService().consultarTecnico("Mantenimiento");

            mensaje.append("Técnico: ")
                        .append(tecnico.getId())
                        .append("\nEstado: ")
                        .append(
                                tecnico.isDisponible()
                                        ? "Disponible"
                                        : "No disponible"
                        )
                        .append("\nCosto de desplazamiento: $")
                        .append(
                                String.format(
                                        "%,.0f",
                                        tecnico.getCostoDesplazamiento()
                                )
                        )
                        .append("\n\n");

            alerta.setContentText(mensaje.toString());
            alerta.showAndWait();
        });

        VBox contenedor = new VBox(
                12,
                titulo,
                subtitulo,
                etiquetaCatalogo,
                listaServicios,
                botonPaquetes,
                botonSolicitudes,
                botonTecnicos
        );

        contenedor.setPadding(new Insets(25));
        contenedor.setAlignment(Pos.TOP_CENTER);
        contenedor.setMaxWidth(Double.MAX_VALUE);

        VBox raiz = new VBox(contenedor);
        raiz.setStyle("-fx-background-color: #F4F6F8;");
        raiz.setAlignment(Pos.CENTER);

        double anchoPantalla = Screen.getPrimary()
                .getVisualBounds()
                .getWidth();

        double altoPantalla = Screen.getPrimary()
                .getVisualBounds()
                .getHeight();

        double anchoVentana = Math.min(700, anchoPantalla * 0.9);
        double altoVentana = Math.min(600, altoPantalla * 0.9);

        Scene escena = new Scene(
                raiz,
                anchoVentana,
                altoVentana
        );

        ventana.setTitle("TechSolutions");
        ventana.setScene(escena);
        ventana.setMinWidth(Math.min(450, anchoPantalla));
        ventana.setMinHeight(Math.min(400, altoPantalla));
        ventana.centerOnScreen();
        ventana.show();
    }

    /**
     * Método principal de ejecución.
     *
     * @param args argumentos de ejecución
     */
    public static void main(String[] args) {
        launch(args);
    }
}