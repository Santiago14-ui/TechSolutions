package service;

import builder.SolicitudServicio;
import model.ElementoServicio;
import model.Solicitud;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Gestiona el registro, la consulta y los costos de las solicitudes.
 */
public class SolicitudServicioService {

    private final List<Solicitud> solicitudes;
    private final Set<String> codigosRegistrados;

    /**
     * Inicializa las colecciones de solicitudes.
     */
    public SolicitudServicioService() {
        solicitudes = new ArrayList<>();
        codigosRegistrados = new HashSet<>();
    }

    /**
     * Registra una solicitud si su código no está repetido.
     *
     * @param solicitud solicitud que se desea registrar
     */
    public void registrarSolicitud(Solicitud solicitud) {
        if (solicitud == null) {
            throw new IllegalArgumentException(
                    "La solicitud es obligatoria."
            );
        }

        String codigo = solicitud.getCodigo().trim().toUpperCase();

        if (codigosRegistrados.contains(codigo)) {
            throw new IllegalArgumentException(
                    "Ya existe una solicitud con ese código."
            );
        }

        solicitudes.add(solicitud);
        codigosRegistrados.add(codigo);
    }

    /**
     * Obtiene las solicitudes registradas sin permitir modificar la lista.
     *
     * @return lista de solicitudes
     */
    public List<Solicitud> obtenerSolicitudes() {
        return Collections.unmodifiableList(solicitudes);
    }

    /**
     * Busca una solicitud por su código.
     *
     * @param codigo código de la solicitud
     * @return solicitud encontrada o null si no existe
     */
    public Solicitud buscarPorCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            return null;
        }

        String codigoBuscado = codigo.trim();

        for (Solicitud solicitud : solicitudes) {
            if (solicitud.getCodigo().equalsIgnoreCase(codigoBuscado)) {
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