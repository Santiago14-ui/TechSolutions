
package factory;

import model.Servicio;

/**
 * Fábrica de mantenimiento preventivo.
 */
public class MantenimientoPreventivoFactory extends ServicioFactory {

    @Override
    public Servicio crearServicio() {
        return new Servicio(
                "SER-001",
                "Mantenimiento preventivo",
                "Revisión y mantenimiento preventivo de equipos.",
                80000
        );
    }
}
