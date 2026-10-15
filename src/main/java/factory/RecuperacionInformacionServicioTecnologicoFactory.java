package factory;

import model.ServicioTecnologico;
import model.TipoServicioTecnologico;

/**
 * Clase para crear un servicio tecnologico de tipo recuperar informacion
 * Usa factory method
 */
public class RecuperacionInformacionServicioTecnologicoFactory implements ServicioTecnologicoFactory {
    private final double COSTO_BASE = 150000;
    private final String NOMBRE = "Recuperación de información";

    @Override
    public ServicioTecnologico crearServicioTecnologico(String codigo, String descripcion) {
        return new RecuperacionInformacionServicioTecnologico(
                codigo,
                NOMBRE,
                descripcion,
                COSTO_BASE,
                TipoServicioTecnologico.RECUPERACION_INFORMACION);
    }
}
