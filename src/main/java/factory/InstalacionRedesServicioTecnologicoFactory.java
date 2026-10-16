
package factory;

import model.ServicioTecnologico;

/**
 * Crea servicios de instalación de redes.
 * Usa Factory Method.
 */
public class InstalacionRedesServicioTecnologicoFactory
        implements ServicioTecnologicoFactory {

    private final double COSTO_BASE = 180000;
    private final String NOMBRE = "Instalación de redes";

    @Override
    public ServicioTecnologico crearServicioTecnologico(
            String codigo, String descripcion) {

        return new InstalacionRedesServicioTecnologico(
                codigo,
                NOMBRE,
                descripcion,
                COSTO_BASE,
                NOMBRE);
    }
}
