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
    private LocalDate fechaCreacion = LocalDate.now();
    private Cliente cliente;
    private final List<ElementoServicio> elementos = new ArrayList<>();
    private LocalDate fechaProgramada;
    private String observaciones;
    private PrioridadSolicitud prioridad = PrioridadSolicitud.MEDIA;

    /**
     * Define el código de la solicitud.
     */
    public SolicitudBuilder codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    /**
     * Define la fecha de creación.
     */
    public SolicitudBuilder fechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        return this;
    }

    /**
     * Define el cliente de la solicitud.
     */
    public SolicitudBuilder cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    /**
     * Agrega un servicio o paquete a la solicitud.
     */
    public SolicitudBuilder agregarElemento(ElementoServicio elemento) {
        if (elemento == null) {
            throw new IllegalArgumentException(
                    "El elemento de servicio no puede ser nulo."
            );
        }

        elementos.add(elemento);
        return this;
    }

    /**
     * Define la fecha programada para la solicitud.
     */
    public SolicitudBuilder fechaProgramada(LocalDate fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
        return this;
    }

    /**
     * Define las observaciones de la solicitud.
     */
    public SolicitudBuilder observaciones(String observaciones) {
        this.observaciones = observaciones;
        return this;
    }

    /**
     * Define la prioridad de la solicitud.
     */
    public SolicitudBuilder prioridad(PrioridadSolicitud prioridad) {
        if (prioridad == null) {
            throw new IllegalArgumentException(
                    "La prioridad no puede ser nula."
            );
        }

        this.prioridad = prioridad;
        return this;
    }

    /**
     * Construye y devuelve la solicitud.
     */
    public Solicitud construir() {
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