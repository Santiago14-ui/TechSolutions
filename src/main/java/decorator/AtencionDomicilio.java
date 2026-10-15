
package decorator;

import model.ElementoServicio;

/**
 * Añade atención a domicilio a un elemento de servicio.
 */
public class AtencionDomicilio extends ComplementoServicio {

    private static final double COSTO_ADICIONAL = 25000;

    public AtencionDomicilio(ElementoServicio elemento) {
        super(elemento);
    }

    @Override
    public String getNombre() {
        return elemento.getNombre() + " con atención a domicilio";
    }

    @Override
    public double calcularCosto() {
        return elemento.calcularCosto() + COSTO_ADICIONAL;
    }
}
