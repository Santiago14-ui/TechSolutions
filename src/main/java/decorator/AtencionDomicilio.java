
package decorator;

import model.ServicioTecnologico;

/**
 * Añade atención a domicilio a un elemento de servicio.
 */
public class AtencionDomicilio extends Complemento {

    private static final double COSTO_ADICIONAL = 25000;

    public AtencionDomicilio(ComplementoServicioTecnologico servicio) {
        super(servicio);
    }

    @Override
    protected String descripcionComplemento() {
        return "Atención a domicilio";
    }

    @Override
    protected double costoPropio() {
        return COSTO_ADICIONAL;
    }
}
