package service;

import model.SolicitudServicio;
import composite.ElementoServicio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Gestiona el registro, la consulta y los costos de las solicitudes.
 */
public class SolicitudServicioService {

    private final List<SolicitudServicio> solicitudesServicio;

    /**
     * Inicializa las colecciones de solicitudes.
     */
    public SolicitudServicioService() {
        solicitudesServicio = new ArrayList<>();
    }

    /**
     * Registra una solicitud si su código no está repetido.
     *
     * @param solicitud solicitud que se desea registrar
     */
    public void registrarSolicitud(SolicitudServicio solicitud) {
        if (solicitud == null) {
            throw new IllegalArgumentException(
                    "La solicitud es obligatoria."
            );
        }

        String codigo = solicitud.getCodigoUnico();

        for (SolicitudServicio servicio : solicitudesServicio) {
            if (servicio.getCodigoUnico().equals(codigo)) {
                throw new IllegalArgumentException("Ya existe una solicitud con ese código.");
            }
        }
        solicitudesServicio.add(solicitud);
    }

    /**
     * Obtiene las solicitudes registradas sin permitir modificar la lista.
     *
     * @return lista de solicitudes
     */
    public List<SolicitudServicio> obtenerSolicitudes() {
        return Collections.unmodifiableList(solicitudesServicio);
    }

    /**
     * Busca una solicitud por su código.
     *
     * @param codigo código de la solicitud
     * @return solicitud encontrada o null si no existe
     */
    public SolicitudServicio buscarPorCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            return null;
        }

        String codigoBuscado = codigo.trim();

        for (SolicitudServicio solicitud : solicitudesServicio) {
            if (solicitud.getCodigoUnico().equalsIgnoreCase(codigoBuscado)) {
                return solicitud;
            }
        }

        return null;
    }

    /**
     * Calcula el costo de una solicitud y, si corresponde,
     * suma el costo de desplazamiento del técnico.
     *
     * @param solicitudServicio solicitud cuyo costo se calcula
     * @return costo total de la solicitud
     */
    public double calcularCostoTotal(SolicitudServicio solicitudServicio) {

        if (solicitudServicio == null) {
            throw new IllegalArgumentException(
                    "La solicitud es obligatoria."
            );
        }

        double total = 0.0;

        for (ElementoServicio elementoServicio : solicitudServicio.getElementosContratados()) {
            total += elementoServicio.calcularCosto();
        }

        return total;
    }


}