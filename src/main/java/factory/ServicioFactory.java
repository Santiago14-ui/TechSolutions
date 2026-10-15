
package factory;

import model.ServicioTecnologico;

/**
 * Fábrica base para crear servicios.
 */
public abstract class ServicioFactory {

    public abstract ServicioTecnologico crearServicio();
}
