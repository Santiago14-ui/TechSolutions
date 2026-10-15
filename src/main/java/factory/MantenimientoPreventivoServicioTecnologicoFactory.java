package factory;

import model.ServicioTecnologico;
import model.TipoServicioTecnologico;

/**
 * Clase para crear un servicio tecnologico de tipo mantenimiento preventivo
 * Usa factory method
 */
public class MantenimientoPreventivoServicioTecnologicoFactory implements ServicioTecnologicoFactory {

    private final double COSTO_BASE = 80000;
    private final String NOMBRE = "Mantenimiento preventivo";

    @Override
    public ServicioTecnologico crearServicioTecnologico(String codigo, String descripcion) {
        return new MantenimientoPreventivoServicioTecnologico(
                codigo,
                NOMBRE,
                descripcion,
                COSTO_BASE,
                TipoServicioTecnologico.MANTENIMIENTO_PREVENTIVO);
    }
}
