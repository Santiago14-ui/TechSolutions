
package decorator;

import model.ServicioTecnologico;

/**
 * Añade atención a domicilio a un elemento de servicio.
 */
public class AtencionDomicilio extends Complemento {

    private static final double COSTO_ADICIONAL = 25000;

    public AtencionDomicilio(ServicioTecnologico servicio) {
        super(servicio);
    }

    @Override
    public String descripcionComplemento() {
        return "Atención a domicilio";
    }

    @Override
    public double costoPropio() {
        return COSTO_ADICIONAL;
    }

    @Override
    public String getNombre() {
        return "";
    }

    @Override
    public String getDescripcion() {
        return "";
    }

    @Override
    public void mostrarElemento(String identacion) {

    }
}
