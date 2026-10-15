
package service;

import adapter.LegacySupportAdapter;
import model.TecnicoInfo;

/**
 * Gestiona las consultas de técnicos.
 */
public class TecnicoService {

    private final LegacySupportAdapter adapter;

    public TecnicoService(LegacySupportAdapter adapter) {
        if (adapter == null) {
            throw new IllegalArgumentException("El adapter es obligatorio.");
        }

        this.adapter = adapter;
    }

    public TecnicoInfo consultarTecnico(int codigoEspecialidad) {
        return adapter.consultarTecnico(codigoEspecialidad);
    }
}
