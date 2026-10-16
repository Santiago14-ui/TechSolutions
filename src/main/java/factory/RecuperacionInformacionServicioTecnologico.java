
package factory;

import model.ServicioTecnologico;

/**
 * Representa un servicio de recuperación de información.
 * Usa Factory Method.
 */
public class RecuperacionInformacionServicioTecnologico
        extends ServicioTecnologico implements IServicioTecnologico {

    public RecuperacionInformacionServicioTecnologico(
            String codigo,
            String nombre,
            String descripcion,
            double costoBase,
            String tipoServicioTecnologico) {

        super(codigo, nombre, descripcion, costoBase, tipoServicioTecnologico);
    }
}
