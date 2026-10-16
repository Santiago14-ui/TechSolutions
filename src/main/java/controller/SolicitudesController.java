
package controller;

import model.SolicitudServicio;
import service.SolicitudServicioService;

import java.util.List;

public class SolicitudesController {

    private final SolicitudServicioService solicitudServicioService;

    public SolicitudesController(
            SolicitudServicioService solicitudServicioService) {

        if (solicitudServicioService == null) {
            throw new IllegalArgumentException(
                    "El servicio de solicitudes es obligatorio."
            );
        }

        this.solicitudServicioService = solicitudServicioService;
    }

    public void registrarSolicitud(SolicitudServicio solicitud) {
        solicitudServicioService.registrarSolicitud(solicitud);
    }

    public List<SolicitudServicio> obtenerSolicitudes() {
        return solicitudServicioService.obtenerSolicitudes();
    }

    public SolicitudServicio buscarPorCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException(
                    "El código de la solicitud es obligatorio."
            );
        }

        return solicitudServicioService.buscarPorCodigo(codigo.trim());
    }

    public double calcularCostoTotal(SolicitudServicio solicitud) {
        if (solicitud == null) {
            throw new IllegalArgumentException(
                    "La solicitud es obligatoria."
            );
        }

        return solicitudServicioService.calcularCostoTotal(solicitud);
    }
}
