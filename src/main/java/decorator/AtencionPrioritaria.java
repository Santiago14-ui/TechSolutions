
package decorator;

import model.ServicioTecnologico;

/**
 * Añade atención prioritaria a un elemento de servicio.
 */
public class AtencionPrioritaria extends Complemento {

    private static final double COSTO_ADICIONAL = 35000;

    @Override
    public String descripcionComplemento() {
        return "Atención prioritaria";
    }

    @Override
    public double costoPropio() {
        return COSTO_ADICIONAL;
    }

    public AtencionPrioritaria(ServicioTecnologico servicio) {
        super(servicio);
    }

    @Override
    public String getNombre() {
        return "Atencion Prioritaria";
    }

    @Override
    public String getDescripcion() {
        return "Costo adicional: " + costoPropio();
    }

    @Override
    public void mostrarElemento(String identacion) {
        System.out.println(identacion + getNombre() + getDescripcion());
    }
}
