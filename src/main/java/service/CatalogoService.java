
package service;

import factory.*;
import model.ServicioTecnologico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CatalogoService {

    private final List<ServicioTecnologico> servicios = new ArrayList<>();

    public CatalogoService() {
        cargarServiciosIniciales();
    }

    private void cargarServiciosIniciales() {
        registrarServicioInterno(new MantenimientoPreventivoServicioTecnologicoFactory().crearServicioTecnologico("ST-001", "Revisión preventiva de equipos"));

        registrarServicioInterno(new MantenimientoCorrectivoServicioTecnologicoFactory().crearServicioTecnologico("ST-002", "Reparación de equipos"));

        registrarServicioInterno(new InstalacionRedesServicioTecnologicoFactory().crearServicioTecnologico("ST-003", "Instalación y configuración de redes"));

        registrarServicioInterno(new RecuperacionInformacionServicioTecnologicoFactory().crearServicioTecnologico("ST-004", "Recuperación de archivos y datos"));
    }

    public List<ServicioTecnologico> obtenerServicios() {
        return Collections.unmodifiableList(servicios);
    }

    public ServicioTecnologico buscarPorCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            return null;
        }

        for (ServicioTecnologico servicio : servicios) {
            if (servicio.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return servicio;
            }
        }

        return null;
    }

    public ServicioTecnologico crearServicio(
            String codigo,
            String descripcion,
            String tipo) {

        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException(
                    "El código es obligatorio."
            );
        }

        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException(
                    "La descripción es obligatoria."
            );
        }

        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException(
                    "Debes seleccionar un tipo de servicio."
            );
        }

        if (buscarPorCodigo(codigo.trim()) != null) {
            throw new IllegalArgumentException(
                    "Ya existe un servicio con ese código."
            );
        }

        String codigoLimpio = codigo.trim();
        String descripcionLimpia = descripcion.trim();

        ServicioTecnologico nuevo = switch (tipo) {
            case "Mantenimiento preventivo" -> new MantenimientoPreventivoServicioTecnologicoFactory().crearServicioTecnologico(codigoLimpio, descripcionLimpia);

            case "Mantenimiento correctivo" -> new MantenimientoCorrectivoServicioTecnologicoFactory().crearServicioTecnologico(codigoLimpio, descripcionLimpia);

            case "Instalación de redes" -> new InstalacionRedesServicioTecnologicoFactory().crearServicioTecnologico(codigoLimpio, descripcionLimpia);

            case "Recuperación de información" -> new RecuperacionInformacionServicioTecnologicoFactory().crearServicioTecnologico(codigoLimpio, descripcionLimpia);

            //Aquí seguiría la cadena de los factory, por el momento se deja asi para demostrarlo en la interfaz grafica
            default -> throw new IllegalArgumentException(
                    "Mostrar Factory method"
            );
        };

        registrarServicioInterno(nuevo);
        return nuevo;
    }

    private void registrarServicioInterno(ServicioTecnologico servicio) {
        servicios.add(servicio);
    }
}
