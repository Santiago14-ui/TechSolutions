
package decorator;

import composite.ElementoServicio;
import model.ServicioTecnologico;

/**
 * Añade soporte técnico durante 30 días.
 */
public class Soporte30Dias extends Complemento {

    private static final double COSTO_ADICIONAL = 50000;

    public Soporte30Dias(ElementoServicio servicio) {
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

    @Override
    public String getNombre() {
        return "Soporte por 30 dias";
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
