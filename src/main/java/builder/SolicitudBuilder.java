
package builder;

import model.Cliente;
import model.ElementoServicio;
import model.Solicitud;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Construye solicitudes de servicios técnicos paso a paso.
 */
public class SolicitudBuilder {

    private String codigo;
    private LocalDate fechaCreacion;
    private Cliente cliente;
    private List<ElementoServicio> elementos = new ArrayList<>();
    private LocalDate fechaProgramada;
    private String observaciones;
    private String prioridad;

    public SolicitudBuilder() {
        this.fechaCreacion = LocalDate.now();
    }

    public SolicitudBuilder codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public SolicitudBuilder fechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        return this;
    }

    public SolicitudBuilder cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public SolicitudBuilder agregarElemento(ElementoServicio elemento) {
        if (elemento == null) {
            throw new IllegalArgumentException("El elemento no puede ser nulo.");
        }

        this.elementos.add(elemento);
        return this;
    }

    public SolicitudBuilder fechaProgramada(LocalDate fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
        return this;
    }

    public SolicitudBuilder observaciones(String observaciones) {
        this.observaciones = observaciones;
        return this;
    }

    public SolicitudBuilder prioridad(String prioridad) {
        this.prioridad = prioridad;
        return this;
    }

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
