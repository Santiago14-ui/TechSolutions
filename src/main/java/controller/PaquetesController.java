
package controller;

import composite.PaqueteServicios;
import service.PaqueteService;

import java.util.List;

public class PaquetesController {

    private final PaqueteService paqueteService;

    public PaquetesController(PaqueteService paqueteService) {
        if (paqueteService == null) {
            throw new IllegalArgumentException(
                    "El servicio de paquetes es obligatorio."
            );
        }

        this.paqueteService = paqueteService;
    }

    public void registrarPaquete(PaqueteServicios paquete) {
        paqueteService.registrarPaquete(paquete);
    }

    public List<PaqueteServicios> obtenerPaquetes() {
        return paqueteService.obtenerPaquetes();
    }

    public PaqueteServicios buscarPorCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException(
                    "El código del paquete es obligatorio."
            );
        }

        return paqueteService.buscarPorCodigo(codigo.trim());
    }
}
