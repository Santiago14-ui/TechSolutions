
package decorator;

import model.ServicioTecnologico;

/**
 * Añade soporte técnico durante 30 días.
 */
public class Soporte30Dias extends Complemento {

    private static final double COSTO_ADICIONAL = 50000;

    public Soporte30Dias(ServicioTecnologico servicio) {
        super(servicio);
    }

    @Override
    public String descripcionComplemento() {
        return "Soporte por 30 dias";
    }

    @Override
    public double costoPropio() {
        return COSTO_ADICIONAL;
    }
}
