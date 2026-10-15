package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa una solicitud de servicios técnicos.
 */
public class Solicitud {

    private final String codigo;
    private final LocalDate fechaCreacion;
    private final Cliente cliente;
    private final List<ElementoServicio> elementos;
    private final LocalDate fechaProgramada;
    private final String observaciones;
    private final PrioridadSolicitud prioridad;

    /**
     * Construye una solicitud y valida sus datos obligatorios.
     *
     * @param codigo código único de la solicitud
     * @param fechaCreacion fecha de creación
     * @param cliente cliente que realiza la solicitud
     * @param elementos servicios o paquetes solicitados
     * @param fechaProgramada fecha programada, si existe
     * @param observaciones observaciones adicionales
     * @param prioridad prioridad de la solicitud
     */
    public Solicitud(
            String codigo,
            LocalDate fechaCreacion,
            Cliente cliente,
            List<ElementoServicio> elementos,
            LocalDate fechaProgramada,
            String observaciones,
            PrioridadSolicitud prioridad
    ) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException(
                    "El código es obligatorio."
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

        if (elementos == null || elementos.isEmpty()) {
            throw new IllegalArgumentException(
                    "Debe incluir al menos un elemento de servicio."
            );
        }

        if (elementos.contains(null)) {
            throw new IllegalArgumentException(
                    "Los elementos no pueden ser nulos."
            );
        }

        if (prioridad == null) {
            throw new IllegalArgumentException(
                    "La prioridad es obligatoria."
            );
        }

        this.codigo = codigo.trim();
        this.fechaCreacion = fechaCreacion;
        this.cliente = cliente;
        this.elementos = new ArrayList<>(elementos);
        this.fechaProgramada = fechaProgramada;
        this.observaciones = observaciones;
        this.prioridad = prioridad;
    }

    /**
     * Calcula el costo de los elementos de la solicitud.
     *
     * @return costo total de los servicios y paquetes
     */
    public double calcularCostoTotal() {
        double total = 0;

        for (ElementoServicio elemento : elementos) {
            total += elemento.calcularCosto();
        }

        return total;
    }

    /**
     * @return código de la solicitud
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @return fecha de creación
     */
    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * @return cliente asociado
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * @return lista no modificable de elementos
     */
    public List<ElementoServicio> getElementos() {
        return Collections.unmodifiableList(elementos);
    }

    /**
     * @return fecha programada o null si no se estableció
     */
    public LocalDate getFechaProgramada() {
        return fechaProgramada;
    }

    /**
     * @return observaciones adicionales
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * @return prioridad de la solicitud
     */
    public PrioridadSolicitud getPrioridad() {
        return prioridad;
    }
}