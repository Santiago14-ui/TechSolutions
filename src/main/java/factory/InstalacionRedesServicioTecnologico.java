package factory;

import model.TipoServicioTecnologico;
import model.ServicioTecnologico;

/**
 * Clase para conectar la instancia con su debido tipo de servicio tecnologico
 * Usa factory method
 */
public class InstalacionRedesServicioTecnologico extends ServicioTecnologico implements IServicioTecnologico {

    public InstalacionRedesServicioTecnologico(String codigo, String nombre, String descripcion, double costoBase, TipoServicioTecnologico tipoServicioTecnologico) {
        super(codigo, nombre, descripcion, costoBase, tipoServicioTecnologico);
    }

}
