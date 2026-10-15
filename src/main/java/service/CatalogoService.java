
package service;

import factory.InstalacionRedesFactory;
import factory.MantenimientoCorrectivoFactory;
import factory.MantenimientoPreventivoFactory;
import factory.RecuperacionInformacionFactory;
import factory.ServicioFactory;
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
        agregarServicio(new MantenimientoPreventivoFactory());
        agregarServicio(new MantenimientoCorrectivoFactory());
        agregarServicio(new InstalacionRedesFactory());
        agregarServicio(new RecuperacionInformacionFactory());
    }

    private void agregarServicio(ServicioFactory factory) {
        servicioTecnologicos.add(factory.crearServicio());
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
