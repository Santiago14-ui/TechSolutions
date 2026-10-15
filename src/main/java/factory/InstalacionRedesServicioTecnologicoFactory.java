package factory;

import model.ServicioTecnologico;
import model.TipoServicioTecnologico;

/**
 * Clase para crear un servicio tecnologico de tipo instalacion de redes
 * Usa factory method
 */
public class InstalacionRedesServicioTecnologicoFactory implements ServicioTecnologicoFactory {
    private final double COSTO_BASE = 180000;
    private final String NOMBRE = "Instalación de redes";

    @Override
    public ServicioTecnologico crearServicioTecnologico(String codigo, String descripcion) {
        return new InstalacionRedesServicioTecnologico(
                codigo,
                NOMBRE,
                descripcion,
                COSTO_BASE,
                TipoServicioTecnologico.INSTALACION_REDES);
    }
}
