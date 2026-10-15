
package service;

import factory.InstalacionRedesFactory;
import factory.MantenimientoCorrectivoFactory;
import factory.MantenimientoPreventivoFactory;
import factory.RecuperacionInformacionFactory;
import factory.ServicioFactory;
import model.Servicio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Gestiona el catálogo inicial de servicios.
 */
public class CatalogoService {

    private final List<Servicio> servicios;

    public CatalogoService() {
        servicios = new ArrayList<>();

        cargarServiciosIniciales();
    }

    private void cargarServiciosIniciales() {
        agregarServicio(new MantenimientoPreventivoFactory());
        agregarServicio(new MantenimientoCorrectivoFactory());
        agregarServicio(new InstalacionRedesFactory());
        agregarServicio(new RecuperacionInformacionFactory());
    }

    private void agregarServicio(ServicioFactory factory) {
        servicios.add(factory.crearServicio());
    }

    public List<Servicio> obtenerServicios() {
        return Collections.unmodifiableList(servicios);
    }

    public Servicio buscarPorCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            return null;
        }

        for (Servicio servicio : servicios) {
            if (servicio.getCodigo().equalsIgnoreCase(codigo)) {
                return servicio;
            }
        }

        return null;
    }
}
