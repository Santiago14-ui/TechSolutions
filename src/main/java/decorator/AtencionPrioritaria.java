
package decorator;

import model.ElementoServicio;

/**
 * Añade atención prioritaria a un elemento de servicio.
 */
public class AtencionPrioritaria extends ComplementoServicio {

    private static final double COSTO_ADICIONAL = 35000;

    public AtencionPrioritaria(ElementoServicio elemento) {
        super(elemento);
    }

    @Override
    public String getNombre() {
        return elemento.getNombre() + " con atención prioritaria";
    }

    @Override
    public double calcularCosto() {
        return elemento.calcularCosto() + COSTO_ADICIONAL;
    }
}
