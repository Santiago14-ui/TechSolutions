
package factory;

import model.ServicioTecnologico;

/**
 * Crea servicios de mantenimiento correctivo.
 * Usa Factory Method.
 */
public class MantenimientoCorrectivoServicioTecnologicoFactory
        implements ServicioTecnologicoFactory {

    private final double COSTO_BASE = 120000;
    private final String NOMBRE = "Mantenimiento correctivo";

    @Override
    public ServicioTecnologico crearServicioTecnologico(
            String codigo, String descripcion) {

        return new MantenimientoCorrectivoServicioTecnologico(
                codigo,
                NOMBRE,
                descripcion,
                COSTO_BASE,
                NOMBRE);
    }
}
