
package external;

/**
 * Simula la plataforma externa LegacySupport.
 */
public class LegacySupport {

    public String consultarTecnico(int codigoEspecialidad) {

        switch (codigoEspecialidad) {
            case 1:
                return "TEC-105|DISPONIBLE|30000";

            case 2:
                return "TEC-208|DISPONIBLE|40000";

            case 3:
                return "TEC-310|NO_DISPONIBLE|0";

            default:
                return "SIN_TECNICO|NO_DISPONIBLE|0";
        }
    }
}
