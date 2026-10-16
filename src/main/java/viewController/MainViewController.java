
package viewController;

import adapter.LegacySupportAdapter;
import adapter.TecnicoInfo;
import composite.ElementoServicio;
import composite.PaqueteServicios;
import controller.ClientesController;
import controller.PaquetesController;
import controller.ServiciosController;
import controller.SolicitudesController;
import controller.TecnicosController;
import external.LegacySupport;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.VBox;
import model.Cliente;
import model.PrioridadSolicitud;
import model.ServicioTecnologico;
import model.Servicio;
import model.SolicitudServicio;
import service.CatalogoService;
import service.ClienteService;
import service.PaqueteService;
import service.SolicitudServicioService;
import service.TecnicoService;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainViewController {

    // Servicios compartidos
    private final ClienteService clienteService;
    private final CatalogoService catalogoService;
    private final PaqueteService paqueteService;
    private final SolicitudServicioService solicitudService;
    private final List<Servicio> serviciosContratados = new ArrayList<>();

    // Controladores de la aplicación
    private final ClientesController clientesController;
    private final ServiciosController serviciosController;
    private final PaquetesController paquetesController;
    private final SolicitudesController solicitudesController;
    private final TecnicosController tecnicosController;

    private final NumberFormat formatoMoneda =
            NumberFormat.getCurrencyInstance(new Locale("es", "CO"));

    // Navegación
    @FXML private TabPane mainTabs;

    // Dashboard
    @FXML private Label totalClientesLabel;
    @FXML private Label totalServiciosLabel;
    @FXML private Label totalPaquetesLabel;
    @FXML private Label totalSolicitudesLabel;

    // Clientes
    @FXML private TextField buscarClienteField;
    @FXML private TableView<Cliente> clientesTable;
    @FXML private TableColumn<Cliente, String> clienteDocumentoCol;
    @FXML private TableColumn<Cliente, String> clienteNombreCol;
    @FXML private TableColumn<Cliente, String> clienteCorreoCol;
    @FXML private Label clientesCountLabel;

    // Catálogo
    @FXML private TextField buscarServicioField;
    @FXML private TableView<ServicioTecnologico> serviciosTable;
    @FXML private TableColumn<ServicioTecnologico, String> servicioCodigoCol;
    @FXML private TableColumn<ServicioTecnologico, String> servicioNombreCol;
    @FXML private TableColumn<ServicioTecnologico, String> servicioDescripcionCol;
    @FXML private TableColumn<ServicioTecnologico, Number> servicioCostoCol;
    @FXML private Label serviciosCountLabel;

    // Crear servicios tecnológicos
    @FXML private TextField nuevoServicioCodigoField;
    @FXML private TextField nuevoServicioTipoField;
    @FXML private TextArea nuevoServicioDescripcionArea;

    // Técnicos
    @FXML private ComboBox<String> especialidadCombo;
    @FXML private Label tecnicoIdLabel;
    @FXML private Label tecnicoEstadoLabel;
    @FXML private Label tecnicoCostoLabel;

    // Servicios contratados
    @FXML private ComboBox<Cliente> servicioClienteCombo;
    @FXML private ListView<ElementoServicio> servicioElementosListView;
    @FXML private ComboBox<String> servicioTecnicoCombo;
    @FXML private ListView<String> serviciosContratadosListView;

    // Paquetes
    @FXML private TextField paqueteCodigoField;
    @FXML private TextField paqueteNombreField;
    @FXML private TextArea paqueteDescripcionField;
    @FXML private ListView<ElementoServicio> paqueteElementosListView;
    @FXML private TableView<PaqueteServicios> paquetesTable;
    @FXML private TableColumn<PaqueteServicios, String> paqueteTablaCodigoCol;
    @FXML private TableColumn<PaqueteServicios, String> paqueteTablaNombreCol;
    @FXML private TableColumn<PaqueteServicios, String> paqueteTablaDescripcionCol;
    @FXML private TableColumn<PaqueteServicios, Number> paqueteTablaCostoCol;
    @FXML private Label paquetesCountLabel;

    // Solicitudes
    @FXML private TextField solicitudCodigoField;
    @FXML private ComboBox<Cliente> solicitudClienteCombo;
    @FXML private ComboBox<ElementoServicio> solicitudElementoCombo;
    @FXML private ComboBox<PrioridadSolicitud> solicitudPrioridadCombo;
    @FXML private DatePicker solicitudFechaPicker;
    @FXML private TextArea solicitudObservacionesArea;

    @FXML private TableView<SolicitudServicio> solicitudesTable;
    @FXML private TableColumn<SolicitudServicio, String> solicitudTablaCodigoCol;
    @FXML private TableColumn<SolicitudServicio, String> solicitudTablaClienteCol;
    @FXML private TableColumn<SolicitudServicio, LocalDate> solicitudTablaFechaCol;
    @FXML private TableColumn<SolicitudServicio, PrioridadSolicitud> solicitudTablaPrioridadCol;
    @FXML private TableColumn<SolicitudServicio, Number> solicitudTablaCostoCol;
    @FXML private Label solicitudesCountLabel;

    public MainViewController() {
        clienteService = new ClienteService();
        catalogoService = new CatalogoService();
        paqueteService = new PaqueteService();
        solicitudService = new SolicitudServicioService();

        LegacySupportAdapter adapter =
                new LegacySupportAdapter(LegacySupport.getInstance());

        TecnicoService tecnicoService = new TecnicoService(adapter);

        clientesController = new ClientesController(clienteService);
        serviciosController = new ServiciosController(catalogoService);
        paquetesController = new PaquetesController(paqueteService);
        solicitudesController =
                new SolicitudesController(solicitudService);
        tecnicosController = new TecnicosController(tecnicoService);
    }

    @FXML
    private void initialize() {
        configurarTablas();
        configurarCombos();

        cargarClientes();
        cargarServicios();
        cargarPaquetes();
        cargarSolicitudes();

        actualizarDashboard();
    }

    // =====================================================
    // CONFIGURACIÓN
    // =====================================================

    private void configurarTablas() {
        clienteDocumentoCol.setCellValueFactory(
                new PropertyValueFactory<>("documento"));
        clienteNombreCol.setCellValueFactory(
                new PropertyValueFactory<>("nombre"));
        clienteCorreoCol.setCellValueFactory(
                new PropertyValueFactory<>("correo"));

        servicioCodigoCol.setCellValueFactory(
                new PropertyValueFactory<>("codigo"));
        servicioNombreCol.setCellValueFactory(
                new PropertyValueFactory<>("nombre"));
        servicioDescripcionCol.setCellValueFactory(
                new PropertyValueFactory<>("descripcion"));

        servicioCostoCol.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(
                        data.getValue().getCostoBase()));

        servicioCostoCol.setCellFactory(column ->
                new TableCell<>() {
                    @Override
                    protected void updateItem(Number costo, boolean empty) {
                        super.updateItem(costo, empty);
                        setText(empty || costo == null
                                ? null : moneda(costo.doubleValue()));
                    }
                });

        paqueteTablaCodigoCol.setCellValueFactory(
                new PropertyValueFactory<>("codigo"));
        paqueteTablaNombreCol.setCellValueFactory(
                new PropertyValueFactory<>("nombre"));
        paqueteTablaDescripcionCol.setCellValueFactory(
                new PropertyValueFactory<>("descripcion"));

        paqueteTablaCostoCol.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(
                        data.getValue().calcularCosto()));

        paqueteTablaCostoCol.setCellFactory(column ->
                new TableCell<>() {
                    @Override
                    protected void updateItem(Number costo, boolean empty) {
                        super.updateItem(costo, empty);
                        setText(empty || costo == null
                                ? null : moneda(costo.doubleValue()));
                    }
                });

        solicitudTablaCodigoCol.setCellValueFactory(
                new PropertyValueFactory<>("codigoUnico"));

        solicitudTablaClienteCol.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(
                        data.getValue().getCliente().getNombre()));

        solicitudTablaFechaCol.setCellValueFactory(
                new PropertyValueFactory<>("fechaCreacion"));

        solicitudTablaPrioridadCol.setCellValueFactory(
                new PropertyValueFactory<>("prioridad"));

        solicitudTablaCostoCol.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(
                        data.getValue().getCostoTotal()));

        solicitudTablaCostoCol.setCellFactory(column ->
                new TableCell<>() {
                    @Override
                    protected void updateItem(Number costo, boolean empty) {
                        super.updateItem(costo, empty);
                        setText(empty || costo == null
                                ? null : moneda(costo.doubleValue()));
                    }
                });
    }

    private void configurarCombos() {
        especialidadCombo.setItems(FXCollections.observableArrayList(
                "Mantenimiento",
                "Instalación de redes",
                "Recuperación de información"
        ));

        servicioTecnicoCombo.setItems(FXCollections.observableArrayList(
                "Mantenimiento",
                "Instalación de redes",
                "Recuperación de información"
        ));

        solicitudPrioridadCombo.setItems(
                FXCollections.observableArrayList(PrioridadSolicitud.values()));

        solicitudClienteCombo.setConverter(new StringConverter<>() {
            @Override
            public String toString(Cliente cliente) {
                return cliente == null ? "" :
                        cliente.getNombre() + " · " + cliente.getDocumento();
            }

            @Override
            public Cliente fromString(String texto) {
                return null;
            }
        });

        servicioClienteCombo.setConverter(new StringConverter<>() {
            @Override
            public String toString(Cliente cliente) {
                return cliente == null ? "" :
                        cliente.getNombre() + " · " + cliente.getDocumento();
            }

            @Override
            public Cliente fromString(String texto) {
                return null;
            }
        });

        solicitudElementoCombo.setConverter(new StringConverter<>() {
            @Override
            public String toString(ElementoServicio elemento) {
                return elemento == null ? "" :
                        elemento.getNombre() + " · " + moneda(elemento.calcularCosto());
            }

            @Override
            public ElementoServicio fromString(String texto) {
                return null;
            }
        });

        paqueteElementosListView.getSelectionModel()
                .setSelectionMode(SelectionMode.MULTIPLE);
        servicioElementosListView.getSelectionModel()
                .setSelectionMode(SelectionMode.MULTIPLE);

        configurarListaSeleccionMultiple(paqueteElementosListView);
        configurarListaSeleccionMultiple(servicioElementosListView);

        // La lista de servicios registrados también usa filas oscuras y legibles.
        serviciosContratadosListView.setStyle(
                "-fx-background-color: #0e1628; " +
                "-fx-control-inner-background: #0e1628; " +
                "-fx-border-color: #2b3854; -fx-border-radius: 10; " +
                "-fx-background-radius: 10;");
        serviciosContratadosListView.setCellFactory(lista -> new ListCell<>() {
            @Override
            protected void updateItem(String detalle, boolean empty) {
                super.updateItem(detalle, empty);
                setText(empty || detalle == null ? null : detalle);
                setWrapText(true);
                setPadding(new Insets(12, 14, 12, 14));
                setStyle("-fx-background-color: " +
                        (isSelected() ? "#26395e" : "#121a2d") + "; " +
                        "-fx-text-fill: #d6deef; " +
                        "-fx-border-color: transparent transparent #202b42 transparent; " +
                        "-fx-font-size: 12px;");
            }
        });
    }

    // =====================================================
    // CLIENTES
    // =====================================================

    @FXML
    public void cargarClientes() {
        clientesTable.setItems(
                FXCollections.observableArrayList(
                        clientesController.obtenerClientes()));

        solicitudClienteCombo.setItems(
                FXCollections.observableArrayList(
                        clientesController.obtenerClientes()));
        servicioClienteCombo.setItems(
                FXCollections.observableArrayList(
                        clientesController.obtenerClientes()));

        clientesCountLabel.setText(
                clientesTable.getItems().size() + " registros");

        actualizarDashboard();
    }

    @FXML
    private void buscarCliente() {
        String documento = buscarClienteField.getText();

        if (documento == null || documento.isBlank()) {
            cargarClientes();
            return;
        }

        Cliente cliente =
                clientesController.buscarPorDocumento(documento.trim());

        if (cliente == null) {
            clientesTable.getItems().clear();
            clientesCountLabel.setText("0 registros");
            mostrarAviso(
                    "Cliente no encontrado",
                    "No existe un cliente con ese documento."
            );
            return;
        }

        clientesTable.setItems(
                FXCollections.observableArrayList(cliente));

        clientesCountLabel.setText("1 registro");
    }

    @FXML
    private void crearCliente() {
        Dialog<Cliente> dialogo = new Dialog<>();
        dialogo.setTitle("Nuevo cliente");
        dialogo.setHeaderText("Registrar un cliente en TechSolutions");

        ButtonType guardar = new ButtonType(
                "Guardar cliente", ButtonBar.ButtonData.OK_DONE);
        dialogo.getDialogPane().getButtonTypes().addAll(guardar, ButtonType.CANCEL);

        TextField documento = new TextField();
        documento.setPromptText("Número de documento");
        TextField nombre = new TextField();
        nombre.setPromptText("Nombre completo");
        TextField correo = new TextField();
        correo.setPromptText("Correo electrónico");

        VBox formulario = new VBox(10,
                new Label("Documento"), documento,
                new Label("Nombre completo"), nombre,
                new Label("Correo electrónico"), correo);
        formulario.setPadding(new Insets(16));
        dialogo.getDialogPane().setContent(formulario);

        dialogo.setResultConverter(boton -> {
            if (boton == guardar) {
                return new Cliente(
                        documento.getText().trim(),
                        nombre.getText().trim(),
                        correo.getText().trim());
            }
            return null;
        });

        dialogo.showAndWait().ifPresent(cliente -> {
            if (vacio(cliente.getDocumento()) ||
                    vacio(cliente.getNombre()) ||
                    vacio(cliente.getCorreo())) {
                mostrarAviso("Datos incompletos",
                        "Completa el documento, nombre y correo electrónico.");
                return;
            }
            try {
                clientesController.registrarCliente(cliente);
                cargarClientes();
                actualizarDashboard();
                mostrarConfirmacion("Cliente registrado",
                        "El cliente " + cliente.getNombre() +
                                " se registró correctamente.");
            } catch (RuntimeException e) {
                mostrarError("No se pudo registrar el cliente", e.getMessage());
            }
        });
    }

    @FXML
    private void irClientes() {
        seleccionarPestana("Clientes");
    }

    // =====================================================
    // CATÁLOGO DE SERVICIOS
    // =====================================================

    @FXML
    public void cargarServicios() {
        List<ServicioTecnologico> servicios =
                serviciosController.obtenerServicios();

        serviciosTable.setItems(
                FXCollections.observableArrayList(servicios));

        serviciosCountLabel.setText(
                servicios.size() + " servicios");

        actualizarListasDeElementos();
        actualizarDashboard();
    }

    @FXML
    private void buscarServicio() {
        String codigo = buscarServicioField.getText();

        if (codigo == null || codigo.isBlank()) {
            cargarServicios();
            return;
        }

        ServicioTecnologico servicio =
                serviciosController.buscarPorCodigo(codigo.trim());

        if (servicio == null) {
            serviciosTable.getItems().clear();
            serviciosCountLabel.setText("0 servicios");
            mostrarAviso(
                    "Servicio no encontrado",
                    "No se encontró un servicio con ese código."
            );
            return;
        }

        serviciosTable.setItems(
                FXCollections.observableArrayList(servicio));

        serviciosCountLabel.setText("1 servicio");
    }

    @FXML
    private void irServicios() {
        seleccionarPestana("Catálogo");
    }

    @FXML
    private void crearServicioTecnologico() {
        try {
            ServicioTecnologico servicio = catalogoService.crearServicio(
                    nuevoServicioCodigoField.getText(),
                    nuevoServicioDescripcionArea.getText(),
                    nuevoServicioTipoField.getText().trim());

            cargarServicios();
            nuevoServicioCodigoField.clear();
            nuevoServicioDescripcionArea.clear();
            nuevoServicioTipoField.clear();

            mostrarConfirmacion("Servicio creado",
                    "Se registró " + servicio.getNombre() +
                            " con código " + servicio.getCodigo() +
                            ".\nCosto base: " + moneda(servicio.getCostoBase()));
        } catch (RuntimeException e) {
            mostrarError("No se pudo crear el servicio", e.getMessage());
        }
    }

    // =====================================================
    // TÉCNICOS
    // =====================================================

    @FXML
    private void consultarTecnico() {
        String especialidad = especialidadCombo.getValue();

        if (especialidad == null) {
            mostrarAviso(
                    "Falta la especialidad",
                    "Selecciona una especialidad antes de consultar."
            );
            return;
        }

        try {
            TecnicoInfo tecnico =
                    tecnicosController.consultarTecnico(especialidad);

            tecnicoIdLabel.setText(tecnico.getId());

            tecnicoEstadoLabel.setText(
                    tecnico.isDisponible()
                            ? "Disponible"
                            : "No disponible");

            tecnicoCostoLabel.setText(
                    moneda(tecnico.getCostoDesplazamiento()));

            tecnicoEstadoLabel.setStyle(
                    tecnico.isDisponible()
                            ? "-fx-text-fill: #49dfa4;"
                            : "-fx-text-fill: #ff8b8b;");

        } catch (RuntimeException e) {
            mostrarError(
                    "Error al consultar el técnico",
                    e.getMessage()
            );
        }
    }

    /**
     * Configura una lista con casillas visibles. Así se pueden marcar varios
     * servicios sin depender de Ctrl + clic.
     */
    private void configurarListaSeleccionMultiple(ListView<ElementoServicio> lista) {
        lista.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lista.setStyle(
                "-fx-background-color: #0e1628; " +
                "-fx-control-inner-background: #0e1628; " +
                "-fx-border-color: #2b3854; -fx-border-radius: 10; " +
                "-fx-background-radius: 10;");

        lista.setCellFactory(lv -> new ListCell<>() {
            private final CheckBox checkBox = new CheckBox();

            {
                checkBox.setStyle("-fx-text-fill: #d6deef; -fx-font-size: 11px;");
                checkBox.setMaxWidth(Double.MAX_VALUE);
                checkBox.setOnAction(event -> {
                    int indice = getIndex();
                    if (indice < 0 || indice >= getListView().getItems().size()) {
                        return;
                    }
                    if (checkBox.isSelected()) {
                        getListView().getSelectionModel().select(indice);
                    } else {
                        getListView().getSelectionModel().clearSelection(indice);
                    }
                    getListView().refresh();
                });
            }

            @Override
            protected void updateItem(ElementoServicio elemento, boolean empty) {
                super.updateItem(elemento, empty);
                if (empty || elemento == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    checkBox.setText(elemento.getNombre() + "  ·  " +
                            moneda(elemento.calcularCosto()));
                    checkBox.setSelected(getListView().getSelectionModel()
                            .getSelectedIndices().contains(getIndex()));
                    setGraphic(checkBox);
                    setText(null);
                }
                setPadding(new Insets(7, 10, 7, 10));
                setStyle("-fx-background-color: " +
                        (isSelected() ? "#26395e" : "#0e1628") + "; " +
                        "-fx-border-color: transparent transparent #202b42 transparent;");
            }
        });
    }

    // =====================================================
    // PAQUETES
    // =====================================================

    @FXML
    private void crearPaquete() {
        String codigo = paqueteCodigoField.getText();
        String nombre = paqueteNombreField.getText();
        String descripcion = paqueteDescripcionField.getText();
        List<ElementoServicio> seleccionados = new ArrayList<>(
                paqueteElementosListView.getSelectionModel().getSelectedItems());

        if (vacio(codigo) || vacio(nombre) || vacio(descripcion)) {
            mostrarAviso("Datos incompletos",
                    "Completa el código, nombre y descripción del paquete.");
            return;
        }

        if (seleccionados.isEmpty()) {
            mostrarAviso("Faltan elementos",
                    "Selecciona al menos un servicio o paquete para incluir.");
            return;
        }

        try {
            PaqueteServicios paquete = new PaqueteServicios(
                    codigo.trim(), nombre.trim(), descripcion.trim());

            for (ElementoServicio elemento : seleccionados) {
                paquete.agregarElemento(elemento);
            }

            paquetesController.registrarPaquete(paquete);
            cargarPaquetes();
            actualizarElementosSolicitud();
            actualizarDashboard();
            limpiarPaquete();

            mostrarConfirmacion("Paquete creado",
                    "El paquete se registró con " + seleccionados.size() +
                            " elemento(s).\nCosto total: " +
                            moneda(paquete.calcularCosto()));
        } catch (RuntimeException e) {
            mostrarError("No se pudo crear el paquete", e.getMessage());
        }
    }

    @FXML
    private void cargarPaquetes() {
        List<PaqueteServicios> paquetes =
                paquetesController.obtenerPaquetes();

        paquetesTable.setItems(
                FXCollections.observableArrayList(paquetes));

        paquetesCountLabel.setText(
                paquetes.size() + " paquetes");

        actualizarListasDeElementos();
        actualizarDashboard();
    }

    @FXML
    private void limpiarPaquete() {
        paqueteCodigoField.clear();
        paqueteNombreField.clear();
        paqueteDescripcionField.clear();
        paqueteElementosListView.getSelectionModel().clearSelection();
        paqueteElementosListView.refresh();
    }

    @FXML
    private void verComposicionPaquete() {
        PaqueteServicios paquete = paquetesTable.getSelectionModel().getSelectedItem();
        if (paquete == null) {
            mostrarAviso("Ningún paquete seleccionado",
                    "Selecciona un paquete de la tabla para ver sus elementos.");
            return;
        }

        StringBuilder contenido = new StringBuilder();
        contenido.append(paquete.getNombre()).append("\n");
        contenido.append(paquete.getDescripcion()).append("\n\n");
        contenido.append("Elementos incluidos:\n");
        agregarComposicion(contenido, paquete, 0);
        contenido.append("\nCosto total: ").append(moneda(paquete.calcularCosto()));

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Composición del paquete");
        alerta.setHeaderText(paquete.getNombre());
        alerta.setContentText(contenido.toString());
        alerta.getDialogPane().setMinWidth(430);
        alerta.showAndWait();
    }

    private void agregarComposicion(
            StringBuilder contenido, ElementoServicio elemento, int nivel) {
        String prefijo = "   ".repeat(nivel);
        if (elemento instanceof PaqueteServicios paquete) {
            contenido.append(prefijo).append("▣ ")
                    .append(paquete.getNombre()).append(" — ")
                    .append(moneda(paquete.calcularCosto())).append("\n");
            for (ElementoServicio hijo : paquete.getElementos()) {
                agregarComposicion(contenido, hijo, nivel + 1);
            }
        } else {
            contenido.append(prefijo).append("• ")
                    .append(elemento.getNombre()).append(" — ")
                    .append(moneda(elemento.calcularCosto())).append("\n");
        }
    }

    @FXML
    private void irPaquetes() {
        seleccionarPestana("Paquetes");
    }

    @FXML
    private void crearServicioContratado() {
        Cliente cliente = servicioClienteCombo.getValue();
        List<ElementoServicio> elementos = new ArrayList<>(
                servicioElementosListView.getSelectionModel().getSelectedItems());
        String especialidad = servicioTecnicoCombo.getValue();

        if (cliente == null) {
            mostrarAviso("Falta el cliente", "Selecciona el cliente del servicio.");
            return;
        }
        if (elementos.isEmpty()) {
            mostrarAviso("Faltan elementos",
                    "Selecciona al menos un servicio o paquete.");
            return;
        }
        if (especialidad == null) {
            mostrarAviso("Falta el técnico",
                    "Selecciona la especialidad del técnico.");
            return;
        }

        try {
            TecnicoInfo tecnico = tecnicosController.consultarTecnico(especialidad);
            Servicio servicio = new Servicio(cliente, elementos, tecnico);
            serviciosContratados.add(servicio);

            String detalle = cliente.getNombre() +
                    " | Técnico: " + tecnico.getId() +
                    " | Elementos: " + elementos.size() +
                    " | Total: " + moneda(servicio.getCostoFinal());
            serviciosContratadosListView.getItems().add(detalle);

            mostrarConfirmacion("Servicio registrado",
                    "Cliente: " + cliente.getNombre() +
                            "\nTécnico: " + tecnico.getId() +
                            "\nCosto final: " + moneda(servicio.getCostoFinal()));

            servicioClienteCombo.getSelectionModel().clearSelection();
            servicioElementosListView.getSelectionModel().clearSelection();
            servicioElementosListView.refresh();
            servicioTecnicoCombo.getSelectionModel().clearSelection();
        } catch (RuntimeException e) {
            mostrarError("No se pudo registrar el servicio", e.getMessage());
        }
    }

    // =====================================================
    // SOLICITUDES
    // =====================================================

    private void actualizarElementosSolicitud() {
        List<ElementoServicio> elementos = obtenerElementosDisponibles();
        solicitudElementoCombo.setItems(
                FXCollections.observableArrayList(elementos));
    }

    private List<ElementoServicio> obtenerElementosDisponibles() {
        List<ElementoServicio> elementos = new ArrayList<>();
        elementos.addAll(catalogoService.obtenerServicios());
        elementos.addAll(paquetesController.obtenerPaquetes());
        return elementos;
    }

    private void actualizarListasDeElementos() {
        List<ElementoServicio> elementos = obtenerElementosDisponibles();
        paqueteElementosListView.setItems(
                FXCollections.observableArrayList(elementos));
        servicioElementosListView.setItems(
                FXCollections.observableArrayList(elementos));
        actualizarElementosSolicitud();
    }

    @FXML
    private void crearSolicitud() {
        String codigo = solicitudCodigoField.getText();
        Cliente cliente = solicitudClienteCombo.getValue();
        ElementoServicio elemento = solicitudElementoCombo.getValue();

        if (vacio(codigo)) {
            mostrarAviso(
                    "Falta el código",
                    "Introduce un código único para la solicitud."
            );
            return;
        }

        if (cliente == null) {
            mostrarAviso(
                    "Falta el cliente",
                    "Selecciona el cliente asociado a la solicitud."
            );
            return;
        }

        if (elemento == null) {
            mostrarAviso(
                    "Falta el servicio",
                    "Selecciona el servicio o paquete solicitado."
            );
            return;
        }

        try {
            List<ElementoServicio> elementos = new ArrayList<>();
            elementos.add(elemento);

            SolicitudServicio.Builder builder =
                    new SolicitudServicio.Builder(
                            codigo.trim(),
                            LocalDate.now(),
                            cliente,
                            elementos
                    );

            PrioridadSolicitud prioridad =
                    solicitudPrioridadCombo.getValue();

            if (prioridad != null) {
                builder.prioridad(prioridad);
            }

            LocalDate fecha = solicitudFechaPicker.getValue();

            if (fecha != null) {
                builder.fechaProgramada(fecha);
            }

            String observaciones =
                    solicitudObservacionesArea.getText();

            if (!vacio(observaciones)) {
                builder.observacionesTecnicas(observaciones.trim());
            }

            SolicitudServicio solicitud = builder.build();

            solicitudesController.registrarSolicitud(solicitud);

            cargarSolicitudes();
            actualizarDashboard();
            limpiarSolicitud();

            mostrarConfirmacion(
                    "Solicitud registrada",
                    "La solicitud se guardó correctamente.\nCosto calculado: "
                            + moneda(solicitud.getCostoTotal())
            );

        } catch (RuntimeException e) {
            mostrarError(
                    "No se pudo registrar la solicitud",
                    e.getMessage()
            );
        }
    }

    @FXML
    private void cargarSolicitudes() {
        List<SolicitudServicio> solicitudes =
                solicitudesController.obtenerSolicitudes();

        solicitudesTable.setItems(
                FXCollections.observableArrayList(solicitudes));

        solicitudesCountLabel.setText(
                solicitudes.size() + " solicitudes");

        actualizarDashboard();
    }

    @FXML
    private void limpiarSolicitud() {
        solicitudCodigoField.clear();
        solicitudClienteCombo.getSelectionModel().clearSelection();
        solicitudElementoCombo.getSelectionModel().clearSelection();
        solicitudPrioridadCombo.getSelectionModel().clearSelection();
        solicitudFechaPicker.setValue(null);
        solicitudObservacionesArea.clear();
    }

    @FXML
    private void irSolicitudes() {
        seleccionarPestana("Solicitudes");
    }

    private void seleccionarPestana(String nombre) {
        for (Tab tab : mainTabs.getTabs()) {
            if (tab.getText() != null &&
                    tab.getText().trim().equalsIgnoreCase(nombre)) {
                mainTabs.getSelectionModel().select(tab);
                return;
            }
        }
    }

    // =====================================================
    // DASHBOARD Y UTILIDADES
    // =====================================================

    private void actualizarDashboard() {
        if (totalClientesLabel != null) {
            totalClientesLabel.setText(
                    String.valueOf(clienteService.obtenerClientes().size()));
        }

        if (totalServiciosLabel != null) {
            totalServiciosLabel.setText(
                    String.valueOf(catalogoService.obtenerServicios().size()));
        }

        if (totalPaquetesLabel != null) {
            totalPaquetesLabel.setText(
                    String.valueOf(paqueteService.obtenerPaquetes().size()));
        }

        if (totalSolicitudesLabel != null) {
            totalSolicitudesLabel.setText(
                    String.valueOf(solicitudService.obtenerSolicitudes().size()));
        }
    }

    private String moneda(double valor) {
        return formatoMoneda.format(valor);
    }

    private boolean vacio(String texto) {
        return texto == null || texto.isBlank();
    }

    private void mostrarAviso(String titulo, String mensaje) {
        mostrarAlerta(Alert.AlertType.WARNING, titulo, mensaje);
    }

    private void mostrarError(String titulo, String mensaje) {
        mostrarAlerta(
                Alert.AlertType.ERROR,
                titulo,
                mensaje == null || mensaje.isBlank()
                        ? "Ocurrió un error inesperado."
                        : mensaje
        );
    }

    private void mostrarConfirmacion(String titulo, String mensaje) {
        mostrarAlerta(Alert.AlertType.INFORMATION, titulo, mensaje);
    }

    private void mostrarAlerta(
            Alert.AlertType tipo,
            String titulo,
            String mensaje) {

        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
