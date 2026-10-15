
package factory;

import model.Servicio;

/**
 * Fábrica de instalación de redes.
 */
public class InstalacionRedesFactory extends ServicioFactory {

    @Override
    public Servicio crearServicio() {
        return new Servicio(
                "SER-003",
                "Instalación de redes",
                "Instalación y configuración de redes.",
                180000
        );
    }
}
