
package service;

import factory.*;
import model.ServicioTecnologico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Gestiona el catálogo inicial de servicios.
 */
public class CatalogoService {

    private final List<ServicioTecnologico> servicioTecnologicos;

    public CatalogoService() {
        servicioTecnologicos = new ArrayList<>();

        cargarServiciosIniciales();
    }

    private void cargarServiciosIniciales() {
        agregarServicio(new MantenimientoPreventivoServicioTecnologicoFactory());
        agregarServicio(new MantenimientoCorrectivoServicioTecnologicoFactory());
        agregarServicio(new InstalacionRedesServicioTecnologicoFactory());
        agregarServicio(new RecuperacionInformacionServicioTecnologicoFactory());
    }

    private void agregarServicio(ServicioTecnologicoFactory factory) {
        servicioTecnologicos.add(factory.crearServicioTecnologico("0001", "servicio tecnologico"));
    }

    public List<ServicioTecnologico> obtenerServicios() {
        return Collections.unmodifiableList(servicioTecnologicos);
    }

    public ServicioTecnologico buscarPorCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            return null;
        }

        for (ServicioTecnologico servicioTecnologico : servicioTecnologicos) {
            if (servicioTecnologico.getCodigo().equalsIgnoreCase(codigo)) {
                return servicioTecnologico;
            }
        }

        return null;
    }
}
