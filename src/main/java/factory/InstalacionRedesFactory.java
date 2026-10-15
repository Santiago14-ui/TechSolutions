
package factory;

import model.ServicioTecnologico;

/**
 * Fábrica de instalación de redes.
 */
public class InstalacionRedesFactory extends ServicioFactory {

    @Override
    public ServicioTecnologico crearServicio() {
        return new ServicioTecnologico(
                "SER-003",
                "Instalación de redes",
                "Instalación y configuración de redes.",
                180000
        );
    }
}
