
package decorator;

import model.ElementoServicio;

/**
 * Añade soporte técnico durante 30 días.
 */
public class Soporte30Dias extends ComplementoServicio {

    private static final double COSTO_ADICIONAL = 50000;

    public Soporte30Dias(ElementoServicio elemento) {
        super(elemento);
    }

    @Override
    public String getNombre() {
        return elemento.getNombre() + " con soporte técnico por 30 días";
    }

    @Override
    public double calcularCosto() {
        return elemento.calcularCosto() + COSTO_ADICIONAL;
    }
}
