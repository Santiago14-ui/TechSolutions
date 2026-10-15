package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solicitud {

    private final String codigo;
    private final LocalDate fechaCreacion;
    private final Cliente cliente;
    private final List<ElementoServicio> elementos;
    private final LocalDate fechaProgramada;
    private final String observaciones;
    private final PrioridadSolicitud prioridad;

    private final boolean atencionPresencial;
    private final String idTecnico;
    private final double costoDesplazamiento;

    // Constructor original, conservado para mantener compatibilidad.
    public Solicitud(
            String codigo,
            LocalDate fechaCreacion,
            Cliente cliente,
            List<ElementoServicio> elementos,
            LocalDate fechaProgramada,
            String observaciones,
            PrioridadSolicitud prioridad) {

        this(
                codigo,
                fechaCreacion,
                cliente,
                elementos,
                fechaProgramada,
                observaciones,
                prioridad,
                false,
                null,
                0
        );
    }

    // Constructor que guarda la modalidad y el costo del desplazamiento.
    public Solicitud(
            String codigo,
            LocalDate fechaCreacion,
            Cliente cliente,
            List<ElementoServicio> elementos,
            LocalDate fechaProgramada,
            String observaciones,
            PrioridadSolicitud prioridad,
            boolean atencionPresencial,
            String idTecnico,
            double costoDesplazamiento) {

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

        if (elementos == null || elementos.isEmpty()) {
            throw new IllegalArgumentException(
                    "La solicitud debe contener al menos un servicio o paquete."
            );
        }

        if (elementos.stream().anyMatch(elemento -> elemento == null)) {
            throw new IllegalArgumentException(
                    "Los elementos de la solicitud no pueden ser nulos."
            );
        }

        if (prioridad == null) {
            throw new IllegalArgumentException(
                    "La prioridad es obligatoria."
            );
        }

        if (!Double.isFinite(costoDesplazamiento)
                || costoDesplazamiento < 0) {
            throw new IllegalArgumentException(
                    "El costo del desplazamiento no es válido."
            );
        }

        if (atencionPresencial
                && (idTecnico == null || idTecnico.isBlank())) {
            throw new IllegalArgumentException(
                    "La atención presencial requiere un técnico."
            );
        }

        if (!atencionPresencial && costoDesplazamiento != 0) {
            throw new IllegalArgumentException(
                    "Una solicitud no presencial no puede tener costo de desplazamiento."
            );
        }

        this.codigo = codigo.trim();
        this.fechaCreacion = fechaCreacion;
        this.cliente = cliente;
        this.elementos = new ArrayList<>(elementos);
        this.fechaProgramada = fechaProgramada;
        this.observaciones = observaciones == null
                ? ""
                : observaciones.trim();
        this.prioridad = prioridad;

        this.atencionPresencial = atencionPresencial;
        this.idTecnico = atencionPresencial ? idTecnico.trim() : null;
        this.costoDesplazamiento = costoDesplazamiento;
    }

    public double calcularCostoTotal() {
        double total = 0;

        for (ElementoServicio elemento : elementos) {
            total += elemento.calcularCosto();
        }

        return total + costoDesplazamiento;
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

    public PrioridadSolicitud getPrioridad() {
        return prioridad;
    }

    public boolean isAtencionPresencial() {
        return atencionPresencial;
    }

    public String getIdTecnico() {
        return idTecnico;
    }

    public double getCostoDesplazamiento() {
        return costoDesplazamiento;
    }
}