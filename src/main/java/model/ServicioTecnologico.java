
package model;

import composite.ElementoServicio;
import decorator.ComplementoServicioTecnologico;

/**
 * Representa un servicio técnico de TechSolutions.
 * Utiliza composite y decorator
 */
public class ServicioTecnologico implements ElementoServicio, ComplementoServicioTecnologico {

    /**
     * Atributos
     */
    //Datos obligatorios
    private String codigo;
    private String nombre;
    private String descripcion;
    private double costoBase;

    public ServicioTecnologico(String codigo, String nombre, String descripcion, double costoBase) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El código es obligatorio.");
        }

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }

        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("La descripción es obligatoria.");
        }

        if (costoBase < 0) {
            throw new IllegalArgumentException("El costo no puede ser negativo.");
        }

        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costoBase = costoBase;
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
