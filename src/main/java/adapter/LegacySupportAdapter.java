package adapter;

import external.LegacySupport;

/**
 * Adapta la respuesta de LegacySupport al modelo de TechSolutions.
 */
public class LegacySupportAdapter {

    private final LegacySupport legacySupport;

    /**
     * Inicializa el adaptador con la plataforma externa.
     *
     * @param legacySupport plataforma externa de soporte
     */
    public LegacySupportAdapter(LegacySupport legacySupport) {
        if (legacySupport == null) {
            throw new IllegalArgumentException(
                    "La plataforma externa es obligatoria."
            );
        }

        this.legacySupport = legacySupport;
    }

    /**
     * Consulta un técnico y transforma la respuesta externa
     * en un objeto TecnicoInfo.
     *
     * @param especialidad código de especialidad
     * @return información del técnico consultado
     */
    public TecnicoInfo consultarTecnico(String especialidad) {

        String respuesta =
                legacySupport.consultarTecnico(especialidad);

        if (respuesta == null || respuesta.isBlank()) {
            throw new IllegalStateException(
                    "LegacySupport devolvió una respuesta vacía."
            );
        }

        String[] datos = respuesta.split("\\|", -1);

        if (datos.length != 3) {
            throw new IllegalStateException(
                    "La respuesta de LegacySupport no tiene el formato esperado."
            );
        }

        String id = datos[0].trim();
        String estado = datos[1].trim();
        double costoDesplazamiento;

        if (id.isBlank()) {
            throw new IllegalStateException(
                    "El identificador del técnico es obligatorio."
            );
        }

        boolean disponible;

        if ("DISPONIBLE".equals(estado)) {
            disponible = true;
        } else if ("NO_DISPONIBLE".equals(estado)) {
            disponible = false;
        } else {
            throw new IllegalStateException(
                    "El estado del técnico no es válido: " + estado
            );
        }

        try {
            costoDesplazamiento = Double.parseDouble(datos[2].trim());
        } catch (NumberFormatException e) {
            throw new IllegalStateException(
                    "El costo de desplazamiento no es válido."
            );
        }

        if (!Double.isFinite(costoDesplazamiento)
                || costoDesplazamiento < 0) {
            throw new IllegalStateException(
                    "El costo de desplazamiento debe ser válido y no negativo."
            );
        }

        return new TecnicoInfo(
                id,
                disponible,
                costoDesplazamiento
        );
    }
}