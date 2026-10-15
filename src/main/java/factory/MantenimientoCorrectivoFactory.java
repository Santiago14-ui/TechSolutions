
package factory;

import model.ServicioTecnologico;

/**
 * Fábrica de mantenimiento correctivo.
 */
public class MantenimientoCorrectivoFactory extends ServicioFactory {

    @Override
    public ServicioTecnologico crearServicio() {
        return new ServicioTecnologico(
                "SER-002",
                "Mantenimiento correctivo",
                "Diagnóstico y reparación de equipos.",
                120000
        );
    }
}
