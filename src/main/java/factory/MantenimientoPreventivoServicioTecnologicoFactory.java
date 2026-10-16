
package factory;

import model.ServicioTecnologico;

/**
 * Crea servicios de mantenimiento preventivo.
 * Usa Factory Method.
 */
public class MantenimientoPreventivoServicioTecnologicoFactory
        implements ServicioTecnologicoFactory {

    private final double COSTO_BASE = 80000;
    private final String NOMBRE = "Mantenimiento preventivo";

    @Override
    public ServicioTecnologico crearServicioTecnologico(
            String codigo, String descripcion) {

        return new MantenimientoPreventivoServicioTecnologico(
                codigo,
                NOMBRE,
                descripcion,
                COSTO_BASE,
                NOMBRE);
    }
}
