
package factory;

import model.ServicioTecnologico;

/**
 * Fábrica de mantenimiento preventivo.
 */
public class MantenimientoPreventivoFactory extends ServicioFactory {

    @Override
    public ServicioTecnologico crearServicio() {
        return new ServicioTecnologico(
                "SER-001",
                "Mantenimiento preventivo",
                "Revisión y mantenimiento preventivo de equipos.",
                80000
        );
    }
}
