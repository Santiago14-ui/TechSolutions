
package factory;

import model.ServicioTecnologico;

/**
 * Fábrica de recuperación de información.
 */
public class RecuperacionInformacionFactory extends ServicioFactory {

    @Override
    public ServicioTecnologico crearServicio() {
        return new ServicioTecnologico(
                "SER-004",
                "Recuperación de información",
                "Recuperación de datos y archivos.",
                150000
        );
    }
}
