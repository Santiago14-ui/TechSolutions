
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import model.Cliente;
import model.PrioridadSolicitud;
import model.ServicioTecnologico;
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

    // Técnicos
    @FXML private ComboBox<String> especialidadCombo;
    @FXML private Label tecnicoIdLabel;
    @FXML private Label tecnicoEstadoLabel;
    @FXML private Label tecnicoCostoLabel;

    // Paquetes
    @FXML private TextField paqueteCodigoField;
    @FXML private TextField paqueteNombreField;
    @FXML private TextArea paqueteDescripcionField;
    @FXML private ComboBox<ServicioTecnologico> paqueteServicioCombo;
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

        solicitudPrioridadCombo.setItems(
                FXCollections.observableArrayList(
                        PrioridadSolicitud.values()));

        paqueteServicioCombo.setConverter(
                new StringConverter<>() {
                    @Override
                    public String toString(ServicioTecnologico servicio) {
                        if (servicio == null) {
                            return "";
                        }
                        return servicio.getNombre()
                                + " · " + servicio.getCodigo();
                    }

                    @Override
                    public ServicioTecnologico fromString(String texto) {
                        return null;
                    }
                });

        solicitudClienteCombo.setConverter(new StringConverter<>() {
            @Override
            public String toString(Cliente cliente) {
                if (cliente == null) {
                    return "";
                }
                return cliente.getNombre()
                        + " · " + cliente.getDocumento();
            }

            @Override
            public Cliente fromString(String texto) {
                return null;
            }
        });

        solicitudElementoCombo.setConverter(
                new StringConverter<>() {
                    @Override
                    public String toString(ElementoServicio elemento) {
                        if (elemento == null) {
                            return "";
                        }
                        return elemento.getNombre();
                    }

                    @Override
                    public ElementoServicio fromString(String texto) {
                        return null;
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
    private void irClientes() {
        mainTabs.getSelectionModel().select(1);
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

        paqueteServicioCombo.setItems(
                FXCollections.observableArrayList(servicios));

        serviciosCountLabel.setText(
                servicios.size() + " servicios");

        actualizarElementosSolicitud();
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
        mainTabs.getSelectionModel().select(2);
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

    // =====================================================
    // PAQUETES
    // =====================================================

    @FXML
    private void crearPaquete() {
        String codigo = paqueteCodigoField.getText();
        String nombre = paqueteNombreField.getText();
        String descripcion = paqueteDescripcionField.getText();
        ServicioTecnologico servicio = paqueteServicioCombo.getValue();

        if (vacio(codigo) || vacio(nombre) || vacio(descripcion)) {
            mostrarAviso(
                    "Datos incompletos",
                    "Completa el código, nombre y descripción del paquete."
            );
            return;
        }

        if (servicio == null) {
            mostrarAviso(
                    "Falta un servicio",
                    "Selecciona el servicio que incluirá el paquete."
            );
            return;
        }

        try {
            PaqueteServicios paquete = new PaqueteServicios(
                    codigo.trim(),
                    nombre.trim(),
                    descripcion.trim()
            );

            paquete.agregarElemento(servicio);
            paquetesController.registrarPaquete(paquete);

            cargarPaquetes();
            actualizarElementosSolicitud();
            actualizarDashboard();
            limpiarPaquete();

            mostrarConfirmacion(
                    "Paquete creado",
                    "El paquete se registró correctamente."
            );

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

        actualizarDashboard();
    }

    @FXML
    private void limpiarPaquete() {
        paqueteCodigoField.clear();
        paqueteNombreField.clear();
        paqueteDescripcionField.clear();
        paqueteServicioCombo.getSelectionModel().clearSelection();
    }

    @FXML
    private void irPaquetes() {
        mainTabs.getSelectionModel().select(4);
    }

    // =====================================================
    // SOLICITUDES
    // =====================================================

    private void actualizarElementosSolicitud() {
        List<ElementoServicio> elementos = new ArrayList<>();

        elementos.addAll(catalogoService.obtenerServicios());
        elementos.addAll(paquetesController.obtenerPaquetes());

        solicitudElementoCombo.setItems(
                FXCollections.observableArrayList(elementos));
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
        mainTabs.getSelectionModel().select(5);
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
