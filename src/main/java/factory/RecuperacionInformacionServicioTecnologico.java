package factory;

import model.ServicioTecnologico;
import model.TipoServicioTecnologico;

/**
 * Clase para conectar la instancia con su debido tipo de servicio tecnologico
 * Usa factory method
 */
public class RecuperacionInformacionServicioTecnologico extends ServicioTecnologico implements IServicioTecnologico {

    public RecuperacionInformacionServicioTecnologico(String codigo, String nombre, String descripcion, double costoBase, TipoServicioTecnologico tipoServicioTecnologico) {
        super(codigo, nombre, descripcion, costoBase, tipoServicioTecnologico);
    }
}
