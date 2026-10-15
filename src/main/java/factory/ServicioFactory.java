
package factory;

import model.Servicio;

/**
 * Fábrica base para crear servicios.
 */
public abstract class ServicioFactory {

    public abstract Servicio crearServicio();
}
