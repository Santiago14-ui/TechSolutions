
package model;

import composite.ElementoServicio;
import decorator.ComplementoServicioTecnologico;

/**
 * Representa un servicio técnico de TechSolutions.
 * Utiliza composite, decorator y factory method
 */
public abstract class ServicioTecnologico implements ElementoServicio, ComplementoServicioTecnologico {

    /**
     * Atributos
     */
    //Datos obligatorios
    private String codigo;
    private String nombre;
    private String descripcion;
    private double costoBase;
    private TipoServicioTecnologico tipoServicioTecnologico;

    public ServicioTecnologico(String codigo, String nombre, String descripcion, double costoBase,  TipoServicioTecnologico tipoServicioTecnologico) {

        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costoBase = costoBase;
        this.tipoServicioTecnologico = tipoServicioTecnologico;
    }

    public String getCodigo() {
        return codigo;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }

    public double getCostoBase() {
        return costoBase;
    }

    @Override
    public void mostrarElemento(String identacion) {
        System.out.println(identacion + this);
    }

    @Override
    public String toString() {
        return "ServicioTecnologico{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", tipo=" + tipoServicioTecnologico + '\'' +
                ", costoBase=" + costoBase +
                '}';
    }

    @Override
    public double calcularCosto() {
        return costoBase + costoAdicional();
    }

    @Override
    public String descripcion() {
        return descripcion;
    }

    @Override
    public double costoAdicional() {
        return 0;
    }
}
