package builder;

import model.Cliente;
import model.ElementoServicio;
import model.PrioridadSolicitud;
import model.Solicitud;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SolicitudBuilder {

    private String codigo;
    private LocalDate fechaCreacion = LocalDate.now();
    private Cliente cliente;
    private final List<ElementoServicio> elementos = new ArrayList<>();
    private LocalDate fechaProgramada;
    private String observaciones = "";
    private PrioridadSolicitud prioridad = PrioridadSolicitud.MEDIA;

    private boolean atencionPresencial;
    private String idTecnico;
    private double costoDesplazamiento;

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
            throw new IllegalArgumentException(
                    "El elemento no puede ser nulo."
            );
        }

        elementos.add(elemento);
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

    public SolicitudBuilder prioridad(PrioridadSolicitud prioridad) {
        this.prioridad = prioridad;
        return this;
    }

    public SolicitudBuilder atencionPresencial(
            String idTecnico,
            double costoDesplazamiento) {

        this.atencionPresencial = true;
        this.idTecnico = idTecnico;
        this.costoDesplazamiento = costoDesplazamiento;

        return this;
    }

    public SolicitudBuilder atencionNoPresencial() {
        this.atencionPresencial = false;
        this.idTecnico = null;
        this.costoDesplazamiento = 0;

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
                prioridad,
                atencionPresencial,
                idTecnico,
                costoDesplazamiento
        );
    }
}