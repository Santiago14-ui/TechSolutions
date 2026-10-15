
package factory;

import model.Servicio;

/**
 * Fábrica de mantenimiento correctivo.
 */
public class MantenimientoCorrectivoFactory extends ServicioFactory {

    @Override
    public Servicio crearServicio() {
        return new Servicio(
                "SER-002",
                "Mantenimiento correctivo",
                "Diagnóstico y reparación de equipos.",
                120000
        );
    }
}
