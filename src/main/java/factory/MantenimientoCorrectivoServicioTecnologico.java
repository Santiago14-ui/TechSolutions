
package factory;

import model.ServicioTecnologico;

/**
 * Representa un servicio de mantenimiento correctivo.
 * Usa Factory Method.
 */
public class MantenimientoCorrectivoServicioTecnologico
        extends ServicioTecnologico implements IServicioTecnologico {

    public MantenimientoCorrectivoServicioTecnologico(
            String codigo,
            String nombre,
            String descripcion,
            double costoBase,
            String tipoServicioTecnologico) {

        super(codigo, nombre, descripcion, costoBase, tipoServicioTecnologico);
    }
}
