
package controller;

import adapter.TecnicoInfo;
import service.TecnicoService;

public class TecnicosController {

    private final TecnicoService tecnicoService;

    public TecnicosController(TecnicoService tecnicoService) {
        if (tecnicoService == null) {
            throw new IllegalArgumentException(
                    "El servicio de técnicos es obligatorio."
            );
        }

        this.tecnicoService = tecnicoService;
    }

    public TecnicoInfo consultarTecnico(String codigoEspecialidad) {
        if (codigoEspecialidad == null
                || codigoEspecialidad.isBlank()) {
            throw new IllegalArgumentException(
                    "La especialidad es obligatoria."
            );
        }

        return tecnicoService.consultarTecnico(
                codigoEspecialidad.trim()
        );
    }
}
