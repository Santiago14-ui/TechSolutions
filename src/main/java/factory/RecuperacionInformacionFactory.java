
package factory;

import model.Servicio;

/**
 * Fábrica de recuperación de información.
 */
public class RecuperacionInformacionFactory extends ServicioFactory {

    @Override
    public Servicio crearServicio() {
        return new Servicio(
                "SER-004",
                "Recuperación de información",
                "Recuperación de datos y archivos.",
                150000
        );
    }
}
