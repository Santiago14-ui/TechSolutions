
package decorator;

import model.ElementoServicio;

/**
 * Clase base para los complementos de un servicio.
 */
public abstract class ComplementoServicio implements ElementoServicio {

    protected ElementoServicio elemento;

    public ComplementoServicio(ElementoServicio elemento) {
        if (elemento == null) {
            throw new IllegalArgumentException(
                    "El elemento de servicio es obligatorio."
            );
        }

        this.elemento = elemento;
    }

    @Override
    public String getNombre() {
        return elemento.getNombre();
    }

    @Override
    public String getDescripcion() {
        return elemento.getDescripcion();
    }

    @Override
    public double calcularCosto() {
        return elemento.calcularCosto();
    }
}
