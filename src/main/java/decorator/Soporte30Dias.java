
package decorator;

import composite.ElementoServicio;
import model.ServicioTecnologico;

/**
 * Añade soporte técnico durante 30 días.
 */
public class Soporte30Dias extends Complemento {

    private static final double COSTO_ADICIONAL = 50000;

    public Soporte30Dias(ComplementoServicioTecnologico servicio) {
        super(servicio);
    }

    @Override
    protected String descripcionComplemento() {
        return "Soporte por 30 días";
    }

    @Override
    protected double costoPropio() {
        return COSTO_ADICIONAL;
    }
}
