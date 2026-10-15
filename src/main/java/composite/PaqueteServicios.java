
package composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa un paquete que agrupa elementos de servicio.
 */
public class PaqueteServicios implements ElementoServicio {

    private String codigo;
    private String nombre;
    private String descripcion;
    private List<ElementoServicio> elementosServicios;

    public PaqueteServicios(String codigo, String nombre, String descripcion) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El código es obligatorio.");
        }

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }

        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("La descripción es obligatoria.");
        }

        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.elementosServicios = new ArrayList<>();
    }

    public void agregarElemento(ElementoServicio elemento) {
        if (elemento == null) {
            throw new IllegalArgumentException("El elemento no puede ser nulo.");
        }

        if (elemento == this) {
            throw new IllegalArgumentException(
                    "Un paquete no puede incluirse a sí mismo."
            );
        }

        if (elemento instanceof PaqueteServicios paquete
                && paquete.contienePaquete(this)) {
            throw new IllegalArgumentException(
                    "No se puede crear una dependencia circular entre paquetes."
            );
        }

        elementosServicios.add(elemento);
    }

    private boolean contienePaquete(PaqueteServicios buscado) {
        if (this == buscado) {
            return true;
        }

        for (ElementoServicio elemento : elementosServicios) {
            if (elemento instanceof PaqueteServicios paquete
                    && paquete.contienePaquete(buscado)) {
                return true;
            }
        }

        return false;
    }

    public List<ElementoServicio> getElementos() {
        return Collections.unmodifiableList(elementosServicios);
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

    @Override
    public double calcularCosto() {
        double total = 0;

        for (ElementoServicio elemento : elementosServicios) {
            total += elemento.calcularCosto();
        }

        return total;
    }

    @Override
    public void mostrarElemento(String identacion) {
        System.out.println(this);
        for (ElementoServicio elemento : elementosServicios) {
            elemento.mostrarElemento(identacion + "   ");
        }
    }

    @Override
    public String toString() {
        return "PaqueteServicios{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", elementos=" + elementosServicios +
                '}';
    }
}
