package controller;

import composite.ElementoServicio;
import factory.MantenimientoPreventivoServicioTecnologicoFactory;
import model.*;
import org.junit.jupiter.api.Test;
import service.SolicitudServicioService;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ControladorSolicitudesTest {

    @Test
    void rechazaServicioDeSolicitudesNulo() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new SolicitudesController(null)
        );
    }

    @Test
    void registraYConsultaSolicitud() {
        SolicitudServicioService service = new SolicitudServicioService();
        SolicitudesController controller = new SolicitudesController(service);

        ArrayList<ElementoServicio> elementos = new ArrayList<>();
        elementos.add(
                new MantenimientoPreventivoServicioTecnologicoFactory()
                        .crearServicioTecnologico("SER-001", "Revisión")
        );

        SolicitudServicio solicitud = new SolicitudServicio.Builder(
                "SOL-001",
                LocalDate.now(),
                new Cliente("123", "Ana", "ana@mail.com"),
                elementos
        ).prioridad(PrioridadSolicitud.MEDIA).build();

        controller.registrarSolicitud(solicitud);

        assertEquals(1, controller.obtenerSolicitudes().size());
        assertSame(solicitud, controller.buscarPorCodigo(" SOL-001 "));
        assertEquals(80000, controller.calcularCostoTotal(solicitud), 0.001);
    }

    @Test
    void rechazaBusquedaConCodigoVacio() {
        SolicitudesController controller = new SolicitudesController(
                new SolicitudServicioService()
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> controller.buscarPorCodigo(" ")
        );
    }

    @Test
    void rechazaCalcularSolicitudNula() {
        SolicitudesController controller = new SolicitudesController(
                new SolicitudServicioService()
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> controller.calcularCostoTotal(null)
        );
    }
}