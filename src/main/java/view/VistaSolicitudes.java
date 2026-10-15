package view;

import controller.ControladorSolicitudes;
import decorator.AtencionDomicilio;
import decorator.AtencionPrioritaria;
import decorator.Soporte30Dias;
import model.Cliente;
import model.ElementoServicio;
import model.PrioridadSolicitud;
import model.PaqueteServicios;
import model.Servicio;
import model.Solicitud;
import model.TecnicoInfo;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Ventana para registrar y consultar solicitudes de servicio.
 * Permite seleccionar servicios, paquetes, complementos
 * y atención presencial con consulta de técnicos.
 */
public class VistaSolicitudes {

    private final ControladorSolicitudes controlador;

    /**
     * Inicializa la vista con su controlador.
     *
     * @param controlador controlador de solicitudes
     */
    public VistaSolicitudes(ControladorSolicitudes controlador) {
        if (controlador == null) {
            throw new IllegalArgumentException(
                    "El controlador de solicitudes es obligatorio."
            );
        }

        this.controlador = controlador;
    }

    /**
     * Construye y muestra la ventana de solicitudes.
     */
    public void mostrar() {
        Stage ventana = new Stage();

        Label titulo = new Label("Registro de solicitudes");
        titulo.setStyle(
                "-fx-font-size: 24px; "
                        + "-fx-font-weight: bold; "
                        + "-fx-text-fill: #23395B;"
        );

        Label subtitulo = new Label(
                "Registra solicitudes, selecciona complementos "
                        + "y consulta los costos."
        );
        subtitulo.setWrapText(true);

        // Código de solicitud.

        TextField campoCodigo = new TextField();
        campoCodigo.setPromptText("Código (ej. SOL-001)");

        // Clientes.

        ComboBox<Cliente> selectorCliente = new ComboBox<>();
        selectorCliente.getItems().addAll(
                controlador.obtenerClientes()
        );
        selectorCliente.setPromptText("Selecciona un cliente");
        selectorCliente.setMaxWidth(Double.MAX_VALUE);

        selectorCliente.setConverter(
                new javafx.util.StringConverter<>() {
                    @Override
                    public String toString(Cliente cliente) {
                        if (cliente == null) {
                            return "";
                        }

                        return cliente.getNombre()
                                + " - "
                                + cliente.getDocumento();
                    }

                    @Override
                    public Cliente fromString(String texto) {
                        return null;
                    }
                }
        );

        // Fecha y prioridad.

        DatePicker selectorFecha = new DatePicker();
        selectorFecha.setPromptText("Fecha programada");
        selectorFecha.setMaxWidth(Double.MAX_VALUE);

        ComboBox<PrioridadSolicitud> selectorPrioridad =
                new ComboBox<>();

        selectorPrioridad.getItems().addAll(
                PrioridadSolicitud.values()
        );
        selectorPrioridad.setValue(PrioridadSolicitud.MEDIA);
        selectorPrioridad.setMaxWidth(Double.MAX_VALUE);

        // Observaciones.

        TextArea campoObservaciones = new TextArea();
        campoObservaciones.setPromptText(
                "Observaciones (opcional)"
        );
        campoObservaciones.setWrapText(true);
        campoObservaciones.setPrefRowCount(3);

        // Servicios disponibles.

        Label etiquetaServicios =
                new Label("1. Servicios disponibles");

        etiquetaServicios.setStyle("-fx-font-weight: bold;");

        VBox listaServicios = new VBox(8);
        List<CheckBox> casillasServicios = new ArrayList<>();

        for (Servicio servicio : controlador.obtenerServicios()) {
            CheckBox casilla = new CheckBox(
                    servicio.getNombre()
                            + " | $"
                            + String.format(
                            "%,.0f",
                            servicio.calcularCosto()
                    )
            );

            casilla.setUserData(servicio);
            casillasServicios.add(casilla);
            listaServicios.getChildren().add(casilla);
        }

        // Paquetes disponibles.

        Label etiquetaPaquetes =
                new Label("2. Paquetes disponibles");

        etiquetaPaquetes.setStyle("-fx-font-weight: bold;");

        VBox listaPaquetes = new VBox(8);
        List<CheckBox> casillasPaquetes = new ArrayList<>();

        for (PaqueteServicios paquete : controlador.obtenerPaquetes()) {
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
            listaPaquetes.getChildren().add(casilla);
        }

        if (casillasPaquetes.isEmpty()) {
            listaPaquetes.getChildren().add(
                    new Label("No hay paquetes registrados.")
            );
        }

        // Complementos del patrón Decorator.

        Label etiquetaComplementos =
                new Label("3. Complementos opcionales");

        etiquetaComplementos.setStyle("-fx-font-weight: bold;");

        CheckBox casillaPrioritaria = new CheckBox(
                "Atención prioritaria | $35.000 por elemento"
        );

        CheckBox casillaSoporte = new CheckBox(
                "Soporte técnico por 30 días | $50.000 por elemento"
        );

        CheckBox casillaDomicilio = new CheckBox(
                "Atención a domicilio | $25.000 por elemento"
        );

        // Atención presencial mediante Adapter.

        Label etiquetaAtencion =
                new Label("4. Modalidad de atención");

        etiquetaAtencion.setStyle("-fx-font-weight: bold;");

        CheckBox casillaPresencial = new CheckBox(
                "Solicitar atención presencial"
        );

        ComboBox<Integer> selectorEspecialidad = new ComboBox<>();
        selectorEspecialidad.getItems().addAll(1, 2, 3);
        selectorEspecialidad.setPromptText(
                "Selecciona la especialidad del técnico"
        );
        selectorEspecialidad.setMaxWidth(Double.MAX_VALUE);
        selectorEspecialidad.setDisable(true);

        selectorEspecialidad.setConverter(
                new javafx.util.StringConverter<>() {
                    @Override
                    public String toString(Integer codigo) {
                        if (codigo == null) {
                            return "";
                        }

                        return switch (codigo) {
                            case 1 -> "Especialidad 1";
                            case 2 -> "Especialidad 2";
                            case 3 -> "Especialidad 3";
                            default -> "Especialidad " + codigo;
                        };
                    }

                    @Override
                    public Integer fromString(String texto) {
                        return null;
                    }
                }
        );

        Label informacionTecnico = new Label(
                "Atención no presencial. Sin costo de desplazamiento."
        );
        informacionTecnico.setWrapText(true);

        // Costo estimado.

        Label etiquetaCosto = new Label("Costo estimado: $0");
        etiquetaCosto.setStyle(
                "-fx-font-size: 16px; "
                        + "-fx-font-weight: bold; "
                        + "-fx-text-fill: #176B3A;"
        );

        // Resultado del registro.

        Label resultado = new Label();
        resultado.setWrapText(true);
        resultado.setStyle(
                "-fx-font-weight: bold; "
                        + "-fx-text-fill: #176B3A;"
        );

        /*
         * Actualiza la selección y calcula el costo.
         * La consulta del técnico solo se realiza para atención presencial.
         */
        Runnable actualizarCosto = () -> {
            cargarElementosSeleccionados(
                    casillasServicios,
                    casillasPaquetes
            );

            double total = controlador.calcularCostoConComplementos(
                    casillaPrioritaria.isSelected(),
                    casillaSoporte.isSelected(),
                    casillaDomicilio.isSelected()
            );

            if (casillaPresencial.isSelected()) {
                Integer especialidad = selectorEspecialidad.getValue();

                if (especialidad == null) {
                    informacionTecnico.setText(
                            "Selecciona una especialidad para consultar "
                                    + "la disponibilidad."
                    );

                    etiquetaCosto.setText(
                            "Costo estimado: pendiente de consultar técnico"
                    );
                    return;
                }

                try {
                    TecnicoInfo tecnico =
                            controlador.consultarTecnico(especialidad);

                    if (tecnico == null) {
                        informacionTecnico.setText(
                                "No se obtuvo información del técnico."
                        );
                        etiquetaCosto.setText(
                                "Costo estimado: no disponible"
                        );
                        return;
                    }

                    if (!tecnico.isDisponible()) {
                        informacionTecnico.setText(
                                "El técnico " + tecnico.getId()
                                        + " no está disponible."
                        );
                        etiquetaCosto.setText(
                                "Costo estimado: no disponible"
                        );
                        return;
                    }

                    total += tecnico.getCostoDesplazamiento();

                    informacionTecnico.setText(
                            "Técnico: " + tecnico.getId()
                                    + " | Disponible"
                                    + " | Desplazamiento: $"
                                    + String.format(
                                    "%,.0f",
                                    tecnico.getCostoDesplazamiento()
                            )
                    );

                } catch (IllegalArgumentException
                         | IllegalStateException e) {

                    informacionTecnico.setText(e.getMessage());
                    etiquetaCosto.setText(
                            "Costo estimado: no disponible"
                    );
                    return;
                }
            } else {
                informacionTecnico.setText(
                        "Atención no presencial. "
                                + "Sin costo de desplazamiento."
                );
            }

            etiquetaCosto.setText(
                    "Costo estimado: $"
                            + String.format("%,.0f", total)
            );
        };

        // Eventos de servicios, paquetes y complementos.

        for (CheckBox casilla : casillasServicios) {
            casilla.setOnAction(
                    event -> actualizarCosto.run()
            );
        }

        for (CheckBox casilla : casillasPaquetes) {
            casilla.setOnAction(
                    event -> actualizarCosto.run()
            );
        }

        casillaPrioritaria.setOnAction(
                event -> actualizarCosto.run()
        );

        casillaSoporte.setOnAction(
                event -> actualizarCosto.run()
        );

        casillaDomicilio.setOnAction(
                event -> actualizarCosto.run()
        );

        casillaPresencial.setOnAction(event -> {
            selectorEspecialidad.setDisable(
                    !casillaPresencial.isSelected()
            );

            if (!casillaPresencial.isSelected()) {
                selectorEspecialidad.setValue(null);
            }

            actualizarCosto.run();
        });

        selectorEspecialidad.setOnAction(
                event -> actualizarCosto.run()
        );

        // Lista de solicitudes registradas.

        Label etiquetaRegistradas =
                new Label("Solicitudes registradas");

        etiquetaRegistradas.setStyle(
                "-fx-font-size: 16px; "
                        + "-fx-font-weight: bold;"
        );

        VBox listaRegistradas = new VBox(8);

        Button botonActualizar = new Button(
                "Actualizar lista de solicitudes"
        );
        botonActualizar.setMaxWidth(Double.MAX_VALUE);

        Runnable actualizarLista = () -> {
            listaRegistradas.getChildren().clear();

            List<Solicitud> solicitudes =
                    controlador.obtenerSolicitudes();

            if (solicitudes.isEmpty()) {
                listaRegistradas.getChildren().add(
                        new Label(
                                "Todavía no hay solicitudes registradas."
                        )
                );
                return;
            }

            for (Solicitud solicitud : solicitudes) {
                Label codigo = new Label(solicitud.getCodigo());
                codigo.setStyle(
                        "-fx-font-weight: bold; "
                                + "-fx-text-fill: #23395B;"
                );

                Label cliente = new Label(
                        "Cliente: "
                                + solicitud.getCliente().getNombre()
                );
                cliente.setWrapText(true);

                Label prioridad = new Label(
                        "Prioridad: " + solicitud.getPrioridad()
                );

                Label fecha = new Label(
                        "Fecha programada: "
                                + (solicitud.getFechaProgramada() == null
                                ? "Sin programar"
                                : solicitud.getFechaProgramada())
                );

                Label elementos = new Label(
                        "Elementos: "
                                + obtenerNombresElementos(solicitud)
                );
                elementos.setWrapText(true);

                Label costo = new Label(
                        "Costo de servicios y complementos: $"
                                + String.format(
                                "%,.0f",
                                solicitud.calcularCostoTotal()
                        )
                );

                costo.setStyle(
                        "-fx-font-weight: bold; "
                                + "-fx-text-fill: #176B3A;"
                );

                VBox tarjeta = new VBox(
                        4,
                        codigo,
                        cliente,
                        prioridad,
                        fecha,
                        elementos,
                        costo
                );

                tarjeta.setPadding(new Insets(10));
                tarjeta.setStyle(
                        "-fx-background-color: white; "
                                + "-fx-border-color: #D6DCE5; "
                                + "-fx-border-radius: 5; "
                                + "-fx-background-radius: 5;"
                );

                listaRegistradas.getChildren().add(tarjeta);
            }
        };

        botonActualizar.setOnAction(
                event -> actualizarLista.run()
        );

        // Registro de solicitud.

        Button botonRegistrar = new Button("Registrar solicitud");
        botonRegistrar.setMaxWidth(Double.MAX_VALUE);
        botonRegistrar.setStyle(
                "-fx-background-color: #1976D2; "
                        + "-fx-text-fill: white; "
                        + "-fx-font-weight: bold; "
                        + "-fx-padding: 9 15 9 15;"
        );

        botonRegistrar.setOnAction(event -> {
            try {
                String codigo = campoCodigo.getText().trim();
                Cliente cliente = selectorCliente.getValue();
                LocalDate fecha = selectorFecha.getValue();
                PrioridadSolicitud prioridad =
                        selectorPrioridad.getValue();

                if (codigo.isBlank()) {
                    mostrarError(
                            "Ingresa el código de la solicitud."
                    );
                    return;
                }

                if (cliente == null) {
                    mostrarError("Selecciona un cliente.");
                    return;
                }

                if (fecha != null
                        && fecha.isBefore(LocalDate.now())) {
                    mostrarError(
                            "La fecha programada no puede "
                                    + "ser anterior a hoy."
                    );
                    return;
                }

                if (prioridad == null) {
                    mostrarError("Selecciona una prioridad.");
                    return;
                }

                cargarElementosSeleccionados(
                        casillasServicios,
                        casillasPaquetes
                );

                List<ElementoServicio> seleccionados =
                        controlador.obtenerElementosSeleccionados();

                if (seleccionados.isEmpty()) {
                    mostrarError(
                            "Selecciona al menos un servicio o paquete."
                    );
                    return;
                }

                // Validar el técnico antes de registrar.

                TecnicoInfo tecnicoSeleccionado = null;

                if (casillaPresencial.isSelected()) {
                    Integer especialidad =
                            selectorEspecialidad.getValue();

                    if (especialidad == null) {
                        mostrarError(
                                "Selecciona la especialidad del técnico."
                        );
                        return;
                    }

                    tecnicoSeleccionado =
                            controlador.consultarTecnico(especialidad);

                    if (tecnicoSeleccionado == null
                            || !tecnicoSeleccionado.isDisponible()) {
                        mostrarError(
                                "El técnico seleccionado no está disponible. "
                                        + "Elige otra especialidad o desactiva "
                                        + "la atención presencial."
                        );
                        return;
                    }
                }

                // Aplicar cada complemento una sola vez.

                List<ElementoServicio> elementosDecorados =
                        new ArrayList<>();

                for (ElementoServicio elemento : seleccionados) {
                    ElementoServicio decorado = elemento;

                    if (casillaPrioritaria.isSelected()) {
                        decorado = new AtencionPrioritaria(decorado);
                    }

                    if (casillaSoporte.isSelected()) {
                        decorado = new Soporte30Dias(decorado);
                    }

                    if (casillaDomicilio.isSelected()) {
                        decorado = new AtencionDomicilio(decorado);
                    }

                    elementosDecorados.add(decorado);
                }

                controlador.limpiarElementosSeleccionados();

                for (ElementoServicio elemento : elementosDecorados) {
                    controlador.agregarElemento(elemento);
                }

                Solicitud solicitud = controlador.registrarSolicitud(
                        codigo,
                        cliente.getDocumento(),
                        fecha,
                        campoObservaciones.getText().trim(),
                        prioridad,
                        casillaPresencial.isSelected(),
                        tecnicoSeleccionado
                );

                // El costo de desplazamiento ya queda incluido en la solicitud.
                double costoFinal = solicitud.calcularCostoTotal();

                resultado.setText(
                        "Solicitud registrada correctamente.\n"
                                + "Código: " + solicitud.getCodigo()
                                + "\nCosto total: $"
                                + String.format("%,.0f", costoFinal)
                );

                // Limpiar el formulario.

                campoCodigo.clear();
                selectorCliente.setValue(null);
                selectorFecha.setValue(null);
                selectorPrioridad.setValue(PrioridadSolicitud.MEDIA);
                campoObservaciones.clear();

                for (CheckBox casilla : casillasServicios) {
                    casilla.setSelected(false);
                }

                for (CheckBox casilla : casillasPaquetes) {
                    casilla.setSelected(false);
                }

                casillaPrioritaria.setSelected(false);
                casillaSoporte.setSelected(false);
                casillaDomicilio.setSelected(false);
                casillaPresencial.setSelected(false);

                selectorEspecialidad.setValue(null);
                selectorEspecialidad.setDisable(true);

                informacionTecnico.setText(
                        "Atención no presencial. "
                                + "Sin costo de desplazamiento."
                );

                etiquetaCosto.setText("Costo estimado: $0");

                actualizarLista.run();

            } catch (IllegalArgumentException
                     | IllegalStateException e) {
                mostrarError(e.getMessage());
            }
        });

        // Formulario principal.

        VBox formulario = new VBox(
                10,
                titulo,
                subtitulo,
                new Separator(),
                new Label("Código de solicitud"),
                campoCodigo,
                new Label("Cliente"),
                selectorCliente,
                new Label("Fecha programada (opcional)"),
                selectorFecha,
                new Label("Prioridad"),
                selectorPrioridad,
                new Label("Observaciones"),
                campoObservaciones,
                new Separator(),
                etiquetaServicios,
                crearDesplazamiento(listaServicios, 130),
                etiquetaPaquetes,
                crearDesplazamiento(listaPaquetes, 130),
                etiquetaComplementos,
                casillaPrioritaria,
                casillaSoporte,
                casillaDomicilio,
                new Separator(),
                etiquetaAtencion,
                casillaPresencial,
                selectorEspecialidad,
                informacionTecnico,
                etiquetaCosto,
                botonRegistrar,
                resultado,
                new Separator(),
                etiquetaRegistradas,
                botonActualizar,
                crearDesplazamiento(listaRegistradas, 180)
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
        double altoVentana = Math.min(700, altoPantalla * 0.9);

        Scene escena = new Scene(
                raiz,
                anchoVentana,
                altoVentana
        );

        ventana.setTitle("TechSolutions - Registro de solicitudes");
        ventana.setScene(escena);
        ventana.setMinWidth(Math.min(450, anchoPantalla));
        ventana.setMinHeight(Math.min(400, altoPantalla));
        ventana.centerOnScreen();
        ventana.show();

        actualizarLista.run();
    }

    /**
     * Carga en el controlador los servicios y paquetes seleccionados.
     *
     * @param casillasServicios casillas de servicios
     * @param casillasPaquetes casillas de paquetes
     */
    private void cargarElementosSeleccionados(
            List<CheckBox> casillasServicios,
            List<CheckBox> casillasPaquetes) {

        controlador.limpiarElementosSeleccionados();

        for (CheckBox casilla : casillasServicios) {
            if (casilla.isSelected()) {
                controlador.agregarElemento(
                        (ElementoServicio) casilla.getUserData()
                );
            }
        }

        for (CheckBox casilla : casillasPaquetes) {
            if (casilla.isSelected()) {
                controlador.agregarElemento(
                        (ElementoServicio) casilla.getUserData()
                );
            }
        }
    }

    /**
     * Obtiene los nombres de los elementos de una solicitud.
     *
     * @param solicitud solicitud consultada
     * @return nombres de los servicios y paquetes
     */
    private String obtenerNombresElementos(Solicitud solicitud) {
        List<String> nombres = new ArrayList<>();

        for (ElementoServicio elemento : solicitud.getElementos()) {
            nombres.add(elemento.getNombre());
        }

        return String.join(", ", nombres);
    }

    /**
     * Crea un panel desplazable.
     *
     * @param contenido elementos que se mostrarán
     * @param altura altura preferida
     * @return panel con desplazamiento
     */
    private ScrollPane crearDesplazamiento(
            VBox contenido,
            double altura) {

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
     * Muestra una alerta de error.
     *
     * @param mensaje descripción del error
     */
    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText(
                "No se pudo completar la operación"
        );
        alerta.setContentText(
                mensaje == null
                        ? "Ocurrió un error inesperado."
                        : mensaje
        );
        alerta.showAndWait();
    }
}
