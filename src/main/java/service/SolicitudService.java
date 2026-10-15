
package service;

import model.Solicitud;
import model.TecnicoInfo;

/**
 * Gestiona las solicitudes y el cálculo de sus costos.
 */
public class SolicitudService {

    public double calcularCostoTotal(
            Solicitud solicitud,
            boolean atencionPresencial,
            TecnicoInfo tecnico) {

        if (solicitud == null) {
            throw new IllegalArgumentException(
                    "La solicitud es obligatoria."
            );
        }

        double total = solicitud.calcularCostoTotal();

        if (atencionPresencial) {
            if (tecnico == null || !tecnico.isDisponible()) {
                throw new IllegalStateException(
                        "No se puede confirmar la atención presencial "
                                + "sin un técnico disponible."
                );
            }

            total += tecnico.getCostoDesplazamiento();
        }

        return total;
    }
}
