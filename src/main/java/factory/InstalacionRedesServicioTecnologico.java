
package factory;

import model.ServicioTecnologico;

/**
 * Representa un servicio de instalación de redes.
 * Usa Factory Method.
 */
public class InstalacionRedesServicioTecnologico
        extends ServicioTecnologico implements IServicioTecnologico {

    public InstalacionRedesServicioTecnologico(
            String codigo,
            String nombre,
            String descripcion,
            double costoBase,
            String tipoServicioTecnologico) {

        super(codigo, nombre, descripcion, costoBase, tipoServicioTecnologico);
    }
}
