package factory;

import model.ServicioTecnologico;
import model.TipoServicioTecnologico;

/**
 * Clase para crear un servicio tecnologico de tipo mantenimiento correctivo
 * Usa factory method
 */
public class MantenimientoCorrectivoServicioTecnologicoFactory implements ServicioTecnologicoFactory{
    private final double COSTO_BASE = 120000;
    private final String NOMBRE = "Mantenimiento correctivo";

    @Override
    public ServicioTecnologico crearServicioTecnologico(String codigo, String descripcion) {
        return new MantenimientoCorrectivoServicioTecnologico(
                codigo,
                NOMBRE,
                descripcion,
                COSTO_BASE,
                TipoServicioTecnologico.MANTENIMIENTO_CORRECTIVO);
    }
}
