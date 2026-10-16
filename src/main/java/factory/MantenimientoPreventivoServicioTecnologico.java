
package factory;

import model.ServicioTecnologico;

/**
 * Representa un servicio de mantenimiento preventivo.
 * Usa Factory Method.
 */
public class MantenimientoPreventivoServicioTecnologico
        extends ServicioTecnologico implements IServicioTecnologico {

    public MantenimientoPreventivoServicioTecnologico(
            String codigo,
            String nombre,
            String descripcion,
            double costoBase,
            String tipoServicioTecnologico) {

        super(codigo, nombre, descripcion, costoBase, tipoServicioTecnologico);
    }
}
