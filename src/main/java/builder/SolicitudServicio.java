package builder;

import model.Cliente;
import model.ElementoServicio;
import model.PrioridadSolicitud;
import service.SolicitudServicioService;

import java.time.LocalDate;
import java.util.List;

/**
 * Clase para crear solicitudes
 * Tiene que ser flexible, por lo que se aplica el patron builder
 */
public class SolicitudServicio {
    //Clase con las funcionalidades extra
    private final SolicitudServicioService solicitudServicioService = new SolicitudServicioService();

    /**
     * Atributos
     */
    //Datos obligatorios
    private String codigoUnico;
    private LocalDate fechaCreacion;
    private Cliente cliente;
    private List<ElementoServicio> elementosContratados;
    private double costoTotal;

    //Datos opcionales
    private String observacionesTecnicas;
    private PrioridadSolicitud prioridad;
    private LocalDate fechaProgramada;

    /**
     * Constructor privado para aplicar el patron builder
     * @param builder una clase que contiene los datos especificos para crear una instancia
     */
    private SolicitudServicio(Builder builder) {
        this.codigoUnico = builder.codigoUnico;
        this.fechaCreacion = builder.fechaCreacion;
        this.cliente = builder.cliente;
        this.elementosContratados = builder.elementosContratados;
        this.costoTotal = solicitudServicioService.calcularCostoTotal(this);
        this.observacionesTecnicas = builder.observacionesTecnicas;
        this.prioridad = builder.prioridad;
        this.fechaProgramada = builder.fechaProgramada;
    }


    /**
     * Clase encargada de recoger los datos necesarios para crear una instancia
     */
    public static class Builder {

        //Datos obligatorios
        private String codigoUnico;
        private LocalDate fechaCreacion;
        private Cliente cliente;
        private List<ElementoServicio> elementosContratados;

        //Datos opcionales
        private String observacionesTecnicas;
        private PrioridadSolicitud prioridad;
        private LocalDate fechaProgramada;

        /**
         * Constructor de los datos obligatorios de la clase
         * @param codigoUnico codigo del servicio
         * @param fechaCreacion fecha de creacion
         * @param cliente cliente asociado
         * @param elementosContratados servicios contratados
         */
        public Builder(String  codigoUnico, LocalDate fechaCreacion, Cliente cliente, List<ElementoServicio> elementosContratados) {
            this.codigoUnico = codigoUnico;
            this.fechaCreacion = fechaCreacion;
            this.cliente = cliente;
            this.elementosContratados = elementosContratados;
        }

        //Constructores de datos opcionales
        public Builder observacionesTecnicas(String observacionesTecnicas) {
            this.observacionesTecnicas = observacionesTecnicas;
            return this;
        }

        public Builder prioridad(PrioridadSolicitud prioridad) {
            this.prioridad = prioridad;
            return this;
        }

        public Builder fechaProgramada(LocalDate fechaProgramada) {
            this.fechaProgramada = fechaProgramada;
            return this;
        }

        /**
         * Constructor de la instancia final
         * @return
         */
        public SolicitudServicio build() {
            return new SolicitudServicio(this);
        }
    }

    //Getters y setters
    public String getCodigoUnico() {
        return codigoUnico;
    }

    public void setCodigoUnico(String codigoUnico) {
        this.codigoUnico = codigoUnico;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ElementoServicio> getElementosContratados() {
        return elementosContratados;
    }

    public void setElementosContratados(List<ElementoServicio> elementosContratados) {
        this.elementosContratados = elementosContratados;
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(double costoTotal) {
        this.costoTotal = costoTotal;
    }

    public String getObservacionesTecnicas() {
        return observacionesTecnicas;
    }

    public void setObservacionesTecnicas(String observacionesTecnicas) {
        this.observacionesTecnicas = observacionesTecnicas;
    }

    public PrioridadSolicitud getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(PrioridadSolicitud prioridad) {
        this.prioridad = prioridad;
    }

    public LocalDate getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(LocalDate fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }
}