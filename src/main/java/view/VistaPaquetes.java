package view;

import controller.ControladorPaquetes;
import composite.ElementoServicio;
import composite.PaqueteServicios;
import model.ServicioTecnologico;
import service.CatalogoService;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Ventana para crear y consultar paquetes de servicios.
 * Permite combinar servicios individuales y paquetes existentes.
 */
public class VistaPaquetes {

    private final ControladorPaquetes controlador;
    private final CatalogoService catalogoService;

    /**
     * Crea la vista con un controlador y un catálogo nuevos.
     */
    public VistaPaquetes() {
        this(new ControladorPaquetes(), new CatalogoService());
    }

    /**
     * Crea la vista con las dependencias proporcionadas.
     *
     * @param controlador controlador de paquetes
     * @param catalogoService catálogo de servicios
     */
    public VistaPaquetes(
            ControladorPaquetes controlador,
            CatalogoService catalogoService
    ) {
        if (controlador == null || catalogoService == null) {
            throw new IllegalArgumentException(
                    "El controlador y el catálogo son obligatorios."
            );
        }

        this.controlador = controlador;
        this.catalogoService = catalogoService;
    }

    /**
     * Construye y muestra la ventana de gestión de paquetes.
     */
    public void mostrar() {

        Stage ventana = new Stage();

        Label titulo = new Label("Gestión de paquetes");
        titulo.setStyle(
                "-fx-font-size: 24px; "
                        + "-fx-font-weight: bold; "
                        + "-fx-text-fill: #23395B;"
        );

        Label subtitulo = new Label(
                "Crea paquetes combinando servicios y otros paquetes."
        );
        subtitulo.setWrapText(true);

        TextField campoCodigo = new TextField();
        campoCodigo.setPromptText("Código del paquete (ej. PAQ-001)");

        TextField campoNombre = new TextField();
        campoNombre.setPromptText("Nombre del paquete");

        TextField campoDescripcion = new TextField();
        campoDescripcion.setPromptText("Descripción del paquete");

        campoCodigo.setMaxWidth(Double.MAX_VALUE);
        campoNombre.setMaxWidth(Double.MAX_VALUE);
        campoDescripcion.setMaxWidth(Double.MAX_VALUE);

        Label etiquetaServicios =
                new Label("1. Servicios disponibles");
        etiquetaServicios.setStyle("-fx-font-weight: bold;");

        VBox listaServicios = new VBox(8);
        List<CheckBox> casillasServicios = new ArrayList<>();

        for (ServicioTecnologico servicioTecnologico : catalogoService.obtenerServicios()) {

            CheckBox casilla = new CheckBox(
                    servicioTecnologico.getNombre()
                            + " | $"
                            + String.format(
                            "%,.0f",
                            servicioTecnologico.calcularCosto()
                    )
            );

            casilla.setUserData(servicioTecnologico);
            casillasServicios.add(casilla);
            listaServicios.getChildren().add(casilla);
        }

        ScrollPane desplazamientoServicios =
                crearDesplazamiento(listaServicios, 110);

        Label etiquetaPaquetesDisponibles =
                new Label("2. Paquetes existentes");
        etiquetaPaquetesDisponibles.setStyle(
                "-fx-font-weight: bold;"
        );

        Label ayudaPaquetes = new Label(
                "Selecciona los paquetes que quieras incluir en el nuevo."
        );
        ayudaPaquetes.setWrapText(true);

        VBox listaPaquetesDisponibles = new VBox(8);
        List<CheckBox> casillasPaquetes = new ArrayList<>();

        ScrollPane desplazamientoPaquetes =
                crearDesplazamiento(listaPaquetesDisponibles, 110);

        Label etiquetaPaquetesCreados =
                new Label("Paquetes registrados");
        etiquetaPaquetesCreados.setStyle(
                "-fx-font-size: 16px; "
                        + "-fx-font-weight: bold;"
        );

        VBox listaPaquetesCreados = new VBox(8);

        ScrollPane desplazamientoRegistrados =
                crearDesplazamiento(listaPaquetesCreados, 140);

        Button botonCrear = new Button("Crear paquete");
        botonCrear.setMaxWidth(Double.MAX_VALUE);
        botonCrear.setStyle(
                "-fx-background-color: #1976D2; "
                        + "-fx-text-fill: white; "
                        + "-fx-font-weight: bold; "
                        + "-fx-padding: 9 15 9 15;"
        );

        Label resultado = new Label();
        resultado.setWrapText(true);
        resultado.setStyle(
                "-fx-font-weight: bold; "
                        + "-fx-text-fill: #176B3A;"
        );

        /*
         * Actualiza los paquetes disponibles para incluir.
         */
        Runnable actualizarPaquetesDisponibles = () -> {

            listaPaquetesDisponibles.getChildren().clear();
            casillasPaquetes.clear();

            List<PaqueteServicios> paquetes =
                    controlador.obtenerPaquetes();

            if (paquetes.isEmpty()) {
                listaPaquetesDisponibles.getChildren().add(
                        new Label("Todavía no hay paquetes registrados.")
                );
            }

            for (PaqueteServicios paquete : paquetes) {

                CheckBox casilla = new CheckBox(
                        paquete.getCodigo()
                                + " - "
                                + paquete.getNombre()
                                + " | $"
                                + String.format(
                                "%,.0f",
                                paquete.calcularCosto()
                        )
                );

                casilla.setWrapText(true);
                casilla.setUserData(paquete);

                casillasPaquetes.add(casilla);
                listaPaquetesDisponibles.getChildren().add(casilla);
            }
        };

        botonCrear.setOnAction(event -> {

            try {
                String codigo = campoCodigo.getText().trim();
                String nombre = campoNombre.getText().trim();
                String descripcion =
                        campoDescripcion.getText().trim();

                if (codigo.isBlank()
                        || nombre.isBlank()
                        || descripcion.isBlank()) {

                    mostrarError(
                            "Completa el código, el nombre "
                                    + "y la descripción."
                    );
                    return;
                }

                boolean hayServicioSeleccionado =
                        casillasServicios.stream()
                                .anyMatch(CheckBox::isSelected);

                boolean hayPaqueteSeleccionado =
                        casillasPaquetes.stream()
                                .anyMatch(CheckBox::isSelected);

                if (!hayServicioSeleccionado
                        && !hayPaqueteSeleccionado) {

                    mostrarError(
                            "Selecciona al menos un servicio "
                                    + "o un paquete existente."
                    );
                    return;
                }

                PaqueteServicios paquete = controlador.crearPaquete(
                        codigo,
                        nombre,
                        descripcion
                );

                for (CheckBox casilla : casillasServicios) {

                    if (casilla.isSelected()) {
                        ElementoServicio servicio =
                                (ElementoServicio) casilla.getUserData();

                        controlador.agregarElemento(
                                paquete,
                                servicio
                        );
                    }
                }

                for (CheckBox casilla : casillasPaquetes) {

                    if (casilla.isSelected()) {
                        PaqueteServicios paqueteExistente =
                                (PaqueteServicios) casilla.getUserData();

                        controlador.agregarElemento(
                                paquete,
                                paqueteExistente
                        );
                    }
                }

                controlador.registrarPaquete(paquete);

                resultado.setText(
                        "Paquete creado correctamente: "
                                + paquete.getNombre()
                                + "\nCosto total: $"
                                + String.format(
                                "%,.0f",
                                controlador.calcularCosto(paquete)
                        )
                );

                actualizarListaPaquetes(listaPaquetesCreados);
                actualizarPaquetesDisponibles.run();

                campoCodigo.clear();
                campoNombre.clear();
                campoDescripcion.clear();

                for (CheckBox casilla : casillasServicios) {
                    casilla.setSelected(false);
                }

                for (CheckBox casilla : casillasPaquetes) {
                    casilla.setSelected(false);
                }

            } catch (IllegalArgumentException
                     | IllegalStateException e) {
                mostrarError(e.getMessage());
            }
        });

        actualizarListaPaquetes(listaPaquetesCreados);
        actualizarPaquetesDisponibles.run();

        VBox formulario = new VBox(
                10,
                titulo,
                subtitulo,
                new Separator(),
                campoCodigo,
                campoNombre,
                campoDescripcion,
                etiquetaServicios,
                desplazamientoServicios,
                etiquetaPaquetesDisponibles,
                ayudaPaquetes,
                desplazamientoPaquetes,
                botonCrear,
                resultado,
                new Separator(),
                etiquetaPaquetesCreados,
                desplazamientoRegistrados
        );

        formulario.setPadding(new Insets(18));
        formulario.setSpacing(10);
        formulario.setMaxWidth(Double.MAX_VALUE);

        ScrollPane desplazamientoPrincipal =
                new ScrollPane(formulario);

        desplazamientoPrincipal.setFitToWidth(true);
        desplazamientoPrincipal.setPannable(true);
        desplazamientoPrincipal.setHbarPolicy(
                ScrollPane.ScrollBarPolicy.NEVER
        );
        desplazamientoPrincipal.setVbarPolicy(
                ScrollPane.ScrollBarPolicy.AS_NEEDED
        );

        VBox raiz = new VBox(desplazamientoPrincipal);
        raiz.setStyle("-fx-background-color: #F4F6F8;");
        raiz.setFillWidth(true);

        double anchoPantalla = Screen.getPrimary()
                .getVisualBounds()
                .getWidth();

        double altoPantalla = Screen.getPrimary()
                .getVisualBounds()
                .getHeight();

        double anchoVentana = Math.min(600, anchoPantalla * 0.9);
        double altoVentana = Math.min(650, altoPantalla * 0.9);

        Scene escena = new Scene(
                raiz,
                anchoVentana,
                altoVentana
        );

        ventana.setTitle("TechSolutions - Gestión de paquetes");
        ventana.setScene(escena);
        ventana.setMinWidth(Math.min(450, anchoPantalla));
        ventana.setMinHeight(Math.min(400, altoPantalla));

        ventana.sizeToScene();
        ventana.centerOnScreen();
        ventana.show();
    }

    /**
     * Crea un panel desplazable para mostrar una lista.
     *
     * @param contenido elementos de la lista
     * @param altura altura visible preferida
     * @return panel con desplazamiento
     */
    private ScrollPane crearDesplazamiento(
            VBox contenido,
            double altura
    ) {
        ScrollPane desplazamiento = new ScrollPane(contenido);

        desplazamiento.setFitToWidth(true);
        desplazamiento.setPannable(true);
        desplazamiento.setPrefHeight(altura);
        desplazamiento.setMaxWidth(Double.MAX_VALUE);
        desplazamiento.setHbarPolicy(
                ScrollPane.ScrollBarPolicy.NEVER
        );
        desplazamiento.setVbarPolicy(
                ScrollPane.ScrollBarPolicy.AS_NEEDED
        );

        return desplazamiento;
    }

    /**
     * Actualiza la lista visual de paquetes registrados.
     *
     * @param listaPaquetes contenedor donde se muestran los paquetes
     */
    private void actualizarListaPaquetes(VBox listaPaquetes) {

        listaPaquetes.getChildren().clear();

        List<PaqueteServicios> paquetes =
                controlador.obtenerPaquetes();

        if (paquetes.isEmpty()) {
            listaPaquetes.getChildren().add(
                    new Label("No hay paquetes registrados todavía.")
            );
            return;
        }

        for (PaqueteServicios paquete : paquetes) {

            Label codigo = new Label(paquete.getCodigo());
            codigo.setStyle(
                    "-fx-font-weight: bold; "
                            + "-fx-text-fill: #23395B;"
            );

            Label nombre = new Label(paquete.getNombre());
            nombre.setStyle("-fx-font-size: 14px;");

            Label costo = new Label(
                    "Costo total: $"
                            + String.format(
                            "%,.0f",
                            paquete.calcularCosto()
                    )
            );

            costo.setStyle(
                    "-fx-text-fill: #176B3A; "
                            + "-fx-font-weight: bold;"
            );

            VBox tarjeta = new VBox(4, codigo, nombre, costo);
            tarjeta.setPadding(new Insets(10));
            tarjeta.setStyle(
                    "-fx-background-color: white; "
                            + "-fx-border-color: #D6DCE5; "
                            + "-fx-border-radius: 5; "
                            + "-fx-background-radius: 5;"
            );

            listaPaquetes.getChildren().add(tarjeta);
        }
    }

    /**
     * Muestra una alerta cuando ocurre un error.
     *
     * @param mensaje descripción del error
     */
    private void mostrarError(String mensaje) {

        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText("No se pudo crear el paquete");
        alerta.setContentText(
                mensaje == null
                        ? "Ocurrió un error inesperado."
                        : mensaje
        );

        alerta.showAndWait();
    }
}