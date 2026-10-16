
package factory;

import model.ServicioTecnologico;

/**
 * Crea servicios de recuperación de información.
 * Usa Factory Method.
 */
public class RecuperacionInformacionServicioTecnologicoFactory
        implements ServicioTecnologicoFactory {

    private final double COSTO_BASE = 150000;
    private final String NOMBRE = "Recuperación de información";

    @Override
    public ServicioTecnologico crearServicioTecnologico(
            String codigo, String descripcion) {

        return new RecuperacionInformacionServicioTecnologico(
                codigo,
                NOMBRE,
                descripcion,
                COSTO_BASE,
                NOMBRE);
    }
}
