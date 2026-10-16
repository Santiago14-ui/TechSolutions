
package controller;

import model.ServicioTecnologico;
import service.CatalogoService;

import java.util.List;

public class ServiciosController {

    private final CatalogoService catalogoService;

    public ServiciosController(CatalogoService catalogoService) {
        if (catalogoService == null) {
            throw new IllegalArgumentException(
                    "El servicio de catálogo es obligatorio."
            );
        }

        this.catalogoService = catalogoService;
    }

    public List<ServicioTecnologico> obtenerServicios() {
        return catalogoService.obtenerServicios();
    }

    public ServicioTecnologico buscarPorCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException(
                    "El código del servicio es obligatorio."
            );
        }

        return catalogoService.buscarPorCodigo(codigo.trim());
    }
}
