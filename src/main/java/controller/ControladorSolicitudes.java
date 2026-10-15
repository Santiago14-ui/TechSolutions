
package controller;

import builder.SolicitudBuilder;
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
import service.CatalogoService;
import service.ClienteService;
import service.PaqueteService;
import service.SolicitudService;
import service.TecnicoService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Controla las operaciones relacionadas con las solicitudes.
 */
public class ControladorSolicitudes {

    private final CatalogoService catalogoService;
    private final ClienteService clienteService;
    private final PaqueteService paqueteService;
    private final SolicitudService solicitudService;
    private final TecnicoService tecnicoService;

    private final List<ElementoServicio> elementosSeleccionados;

    /**
     * Inicializa el controlador con los servicios de la aplicación.
     */
    public ControladorSolicitudes(
            CatalogoService catalogoService,
            ClienteService clienteService,
            PaqueteService paqueteService,
            SolicitudService solicitudService,
            TecnicoService tecnicoService) {

        if (catalogoService == null
                || clienteService == null
                || paqueteService == null
                || solicitudService == null
                || tecnicoService == null) {

            throw new IllegalArgumentException(
                    "Los servicios del controlador son obligatorios."
            );
        }

        this.catalogoService = catalogoService;
        this.clienteService = clienteService;
        this.paqueteService = paqueteService;
        this.solicitudService = solicitudService;
        this.tecnicoService = tecnicoService;

        this.elementosSeleccionados = new ArrayList<>();
    }

    /**
     * Obtiene los clientes precargados.
     */
    public List<Cliente> obtenerClientes() {
        return clienteService.obtenerClientes();
    }

    /**
     * Obtiene los servicios del catálogo.
     */
    public List<Servicio> obtenerServicios() {
        return catalogoService.obtenerServicios();
    }

    /**
     * Obtiene los paquetes registrados.
     */
    public List<PaqueteServicios> obtenerPaquetes() {
        return paqueteService.obtenerPaquetes();
    }

    /**
     * Agrega un servicio o paquete a la selección actual.
     */
    public void agregarElemento(ElementoServicio elemento) {
        if (elemento == null) {
            throw new IllegalArgumentException(
                    "Debe seleccionar un servicio o paquete."
            );
        }

        elementosSeleccionados.add(elemento);
    }

    /**
     * Limpia los elementos de la selección actual.
     */
    public void limpiarElementosSeleccionados() {
        elementosSeleccionados.clear();
    }

    /**
     * Obtiene una copia de los elementos seleccionados.
     */
    public List<ElementoServicio> obtenerElementosSeleccionados() {
        return new ArrayList<>(elementosSeleccionados);
    }

    /**
     * Calcula el subtotal sin complementos.
     */
    public double calcularCostoElementos() {
        double total = 0;

        for (ElementoServicio elemento : elementosSeleccionados) {
            total += elemento.calcularCosto();
        }

        return total;
    }

    /**
     * Calcula el costo aplicando los complementos seleccionados.
     */
    public double calcularCostoConComplementos(
            boolean atencionPrioritaria,
            boolean soporte30Dias,
            boolean atencionDomicilio) {

        double total = 0;

        for (ElementoServicio elemento : elementosSeleccionados) {
            ElementoServicio elementoDecorado = elemento;

            if (atencionPrioritaria) {
                elementoDecorado =
                        new AtencionPrioritaria(elementoDecorado);
            }

            if (soporte30Dias) {
                elementoDecorado =
                        new Soporte30Dias(elementoDecorado);
            }

            if (atencionDomicilio) {
                elementoDecorado =
                        new AtencionDomicilio(elementoDecorado);
            }

            total += elementoDecorado.calcularCosto();
        }

        return total;
    }

    /**
     * Conserva la firma original para las llamadas existentes.
     * Por defecto, registra una solicitud no presencial.
     */
    public Solicitud registrarSolicitud(
            String codigo,
            String documentoCliente,
            LocalDate fechaProgramada,
            String observaciones,
            PrioridadSolicitud prioridad) {

        return registrarSolicitud(
                codigo,
                documentoCliente,
                fechaProgramada,
                observaciones,
                prioridad,
                false,
                null
        );
    }

    /**
     * Registra una solicitud mediante Builder, incluyendo
     * la modalidad de atención y el costo de desplazamiento.
     */
    public Solicitud registrarSolicitud(
            String codigo,
            String documentoCliente,
            LocalDate fechaProgramada,
            String observaciones,
            PrioridadSolicitud prioridad,
            boolean atencionPresencial,
            TecnicoInfo tecnico) {

        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException(
                    "El código de la solicitud es obligatorio."
            );
        }

        Cliente cliente =
                clienteService.buscarPorDocumento(documentoCliente);

        if (cliente == null) {
            throw new IllegalArgumentException(
                    "Debe seleccionar un cliente válido."
            );
        }

        if (elementosSeleccionados.isEmpty()) {
            throw new IllegalArgumentException(
                    "Debe agregar al menos un servicio o paquete."
            );
        }

        if (prioridad == null) {
            throw new IllegalArgumentException(
                    "Debe seleccionar una prioridad."
            );
        }

        if (atencionPresencial
                && (tecnico == null || !tecnico.isDisponible())) {
            throw new IllegalArgumentException(
                    "Debe seleccionar un técnico disponible para la atención presencial."
            );
        }

        SolicitudBuilder builder = new SolicitudBuilder()
                .codigo(codigo)
                .fechaCreacion(LocalDate.now())
                .cliente(cliente)
                .fechaProgramada(fechaProgramada)
                .observaciones(observaciones)
                .prioridad(prioridad);

        for (ElementoServicio elemento : elementosSeleccionados) {
            builder.agregarElemento(elemento);
        }

        if (atencionPresencial) {
            builder.atencionPresencial(
                    tecnico.getId(),
                    tecnico.getCostoDesplazamiento()
            );
        } else {
            builder.atencionNoPresencial();
        }

        Solicitud solicitud = builder.construir();

        solicitudService.registrarSolicitud(solicitud);

        elementosSeleccionados.clear();

        return solicitud;
    }

    /**
     * Busca una solicitud por su código.
     */
    public Solicitud buscarPorCodigo(String codigo) {
        return solicitudService.buscarPorCodigo(codigo);
    }

    /**
     * Obtiene las solicitudes registradas.
     */
    public List<Solicitud> obtenerSolicitudes() {
        return solicitudService.obtenerSolicitudes();
    }

    /**
     * Consulta un técnico mediante el Adapter.
     */
    public TecnicoInfo consultarTecnico(int codigoEspecialidad) {
        return tecnicoService.consultarTecnico(codigoEspecialidad);
    }

    /**
     * Calcula el costo total de una solicitud.
     */
    public double calcularCostoTotal(
            Solicitud solicitud,
            boolean atencionPresencial,
            TecnicoInfo tecnico) {

        return solicitudService.calcularCostoTotal(
                solicitud,
                atencionPresencial,
                tecnico
        );
    }
}
