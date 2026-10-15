package builder;

import model.Cliente;
import model.ElementoServicio;
import model.PrioridadSolicitud;
import model.Solicitud;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Construye objetos Solicitud paso a paso mediante el patrón Builder.
 */
public class SolicitudBuilder {

    private String codigo;
    private LocalDate fechaCreacion;
    private Cliente cliente;
    private final List<ElementoServicio> elementos;
    private LocalDate fechaProgramada;
    private String observaciones;
    private PrioridadSolicitud prioridad;

    /**
     * Inicializa el constructor con los valores predeterminados.
     */
    public SolicitudBuilder() {
        this.fechaCreacion = LocalDate.now();
        this.elementos = new ArrayList<>();
        this.prioridad = PrioridadSolicitud.MEDIA;
        this.observaciones = "";
    }

    /**
     * Establece el código de la solicitud.
     *
     * @param codigo código único de la solicitud
     * @return este constructor
     */
    public SolicitudBuilder codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    /**
     * Establece la fecha de creación.
     *
     * @param fechaCreacion fecha en que se crea la solicitud
     * @return este constructor
     */
    public SolicitudBuilder fechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        return this;
    }

    /**
     * Establece el cliente de la solicitud.
     *
     * @param cliente cliente asociado a la solicitud
     * @return este constructor
     */
    public SolicitudBuilder cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    /**
     * Agrega un servicio o paquete a la solicitud.
     *
     * @param elemento servicio o paquete que se agregará
     * @return este constructor
     */
    public SolicitudBuilder agregarElemento(ElementoServicio elemento) {
        if (elemento == null) {
            throw new IllegalArgumentException(
                    "El elemento de la solicitud no puede ser nulo."
            );
        }

        this.elementos.add(elemento);
        return this;
    }

    /**
     * Establece la fecha programada para la atención.
     *
     * @param fechaProgramada fecha programada, puede ser nula
     * @return este constructor
     */
    public SolicitudBuilder fechaProgramada(LocalDate fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
        return this;
    }

    /**
     * Establece las observaciones de la solicitud.
     *
     * @param observaciones observaciones adicionales
     * @return este constructor
     */
    public SolicitudBuilder observaciones(String observaciones) {
        this.observaciones = observaciones;
        return this;
    }

    /**
     * Establece la prioridad de la solicitud.
     *
     * @param prioridad prioridad asignada
     * @return este constructor
     */
    public SolicitudBuilder prioridad(PrioridadSolicitud prioridad) {
        this.prioridad = prioridad;
        return this;
    }

    /**
     * Construye y devuelve la solicitud con los datos configurados.
     *
     * @return solicitud construida
     */
    public Solicitud construir() {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException(
                    "El código de la solicitud es obligatorio."
            );
        }

        if (fechaCreacion == null) {
            throw new IllegalArgumentException(
                    "La fecha de creación es obligatoria."
            );
        }

        if (cliente == null) {
            throw new IllegalArgumentException(
                    "El cliente es obligatorio."
            );
        }

        if (elementos.isEmpty()) {
            throw new IllegalArgumentException(
                    "Debe agregar al menos un servicio o paquete."
            );
        }

        if (prioridad == null) {
            throw new IllegalArgumentException(
                    "La prioridad de la solicitud es obligatoria."
            );
        }

        return new Solicitud(
                codigo,
                fechaCreacion,
                cliente,
                elementos,
                fechaProgramada,
                observaciones,
                prioridad
        );
    }
}