
package adapter;

import external.LegacySupport;
import model.TecnicoInfo;

/**
 * Adapta la respuesta de LegacySupport al modelo de TechSolutions.
 */
public class LegacySupportAdapter {

    private final LegacySupport legacySupport;

    public LegacySupportAdapter(LegacySupport legacySupport) {
        if (legacySupport == null) {
            throw new IllegalArgumentException(
                    "La plataforma externa es obligatoria."
            );
        }

        this.legacySupport = legacySupport;
    }

    public TecnicoInfo consultarTecnico(int codigoEspecialidad) {

        String respuesta =
                legacySupport.consultarTecnico(codigoEspecialidad);

        String[] datos = respuesta.split("\\|");

        if (datos.length != 3) {
            throw new IllegalStateException(
                    "La respuesta de LegacySupport no tiene el formato esperado."
            );
        }

        String id = datos[0];
        boolean disponible = "DISPONIBLE".equals(datos[1]);
        double costoDesplazamiento;

        try {
            costoDesplazamiento = Double.parseDouble(datos[2]);
        } catch (NumberFormatException e) {
            throw new IllegalStateException(
                    "El costo de desplazamiento no es válido."
            );
        }

        return new TecnicoInfo(
                id,
                disponible,
                costoDesplazamiento
        );
    }
}
