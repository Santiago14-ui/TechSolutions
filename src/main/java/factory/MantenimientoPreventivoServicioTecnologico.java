package factory;

import model.ServicioTecnologico;
import model.TipoServicioTecnologico;

/**
 * Clase para conectar la instancia con su debido tipo de servicio tecnologico
 * Usa factory method
 */
public class MantenimientoPreventivoServicioTecnologico extends ServicioTecnologico implements IServicioTecnologico {

    public MantenimientoPreventivoServicioTecnologico(String codigo, String nombre, String descripcion, double costoBase, TipoServicioTecnologico tipoServicioTecnologico) {
        super(codigo, nombre, descripcion, costoBase, tipoServicioTecnologico);
    }
}
