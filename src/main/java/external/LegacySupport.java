
package external;


/**
 * Simula la plataforma externa LegacySupport.
 * Solo deberia haber una instancia, por lo que se aplica singleton
 */
public class LegacySupport {
    //Instancia unica
    private static LegacySupport instancia;

    /**
     * Constructor vacio de la clase. Es privado para respetar singleton
     */
    private LegacySupport(){}

    /**
     * Metodo para crear una instancia de la clase. Valida que solo se pueda crear una unica instancia
     * @return unica instancia de la clase
     */
    public static LegacySupport getInstance(){
        if(instancia == null){
            instancia = new LegacySupport();
        }
        return instancia;
    }


    /**
     * Metodo para simular el funcionamiento de la implementacion externa de LegacySupport
     * @param codigoEspecialidad numero correspondiente a la especialidad necesitada. 1=redes, 2=mantenimiento, 3=Informacion.
     * @return
     */
    public String consultarTecnico(int codigoEspecialidad) {

        return switch (codigoEspecialidad) {
            case 1 -> "TEC-105|DISPONIBLE|30000";
            case 2 -> "TEC-208|DISPONIBLE|40000";
            case 3 -> "TEC-310|NO_DISPONIBLE|0";
            default -> "SIN_TECNICO|NO_DISPONIBLE|0";
        };
    }
}
