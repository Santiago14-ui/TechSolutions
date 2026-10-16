
package decorator;

import model.ServicioTecnologico;

/**
 * Añade atención prioritaria a un elemento de servicio.
 */
public class AtencionPrioritaria extends Complemento {

    private static final double COSTO_ADICIONAL = 35000;

    public AtencionPrioritaria(ComplementoServicioTecnologico servicio) {
        super(servicio);
    }

    @Override
    protected String descripcionComplemento() {
        return "Atención prioritaria";
    }

    @Override
    protected double costoPropio() {
        return COSTO_ADICIONAL;
    }
}
