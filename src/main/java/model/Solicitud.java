
package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa una solicitud de servicios técnicos.
 */
public class Solicitud {

    private String codigo;
    private LocalDate fechaCreacion;
    private Cliente cliente;
    private List<ElementoServicio> elementos;
    private LocalDate fechaProgramada;
    private String observaciones;
    private String prioridad;

    public Solicitud(
            String codigo,
            LocalDate fechaCreacion,
            Cliente cliente,
            List<ElementoServicio> elementos,
            LocalDate fechaProgramada,
            String observaciones,
            String prioridad) {

        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El código es obligatorio.");
        }

        if (fechaCreacion == null) {
            throw new IllegalArgumentException("La fecha de creación es obligatoria.");
        }

        if (cliente == null) {
            throw new IllegalArgumentException("El cliente es obligatorio.");
        }

        if (elementos == null || elementos.isEmpty()) {
            throw new IllegalArgumentException("Debe incluir al menos un elemento de servicio.");
        }

        if (elementos.contains(null)) {
            throw new IllegalArgumentException("Los elementos no pueden ser nulos.");
        }

        this.codigo = codigo;
        this.fechaCreacion = fechaCreacion;
        this.cliente = cliente;
        this.elementos = new ArrayList<>(elementos);
        this.fechaProgramada = fechaProgramada;
        this.observaciones = observaciones;
        this.prioridad = prioridad;
    }

    public double calcularCostoTotal() {
        double total = 0;

        for (ElementoServicio elemento : elementos) {
            total += elemento.calcularCosto();
        }

        return total;
    }

    public String getCodigo() {
        return codigo;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<ElementoServicio> getElementos() {
        return Collections.unmodifiableList(elementos);
    }

    public LocalDate getFechaProgramada() {
        return fechaProgramada;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public String getPrioridad() {
        return prioridad;
    }
}
