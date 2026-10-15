package builder;

import model.Cliente;
import model.ElementoServicio;
import model.PrioridadSolicitud;
import model.ServicioTecnologico;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase SolicitudBuilder.
 */
class SolicitudServicioTecnologicoTest {

    private Cliente crearCliente() {
        return new Cliente(
                "123456789",
                "Juan Pérez",
                "juan@gmail.com"
        );
    }

    private ServicioTecnologico crearServicio() {
        return new ServicioTecnologico(
                "SER-001",
                "Mantenimiento preventivo",
                "Revisión y mantenimiento preventivo de equipos.",
                80000
        );
    }

    private SolicitudServicio crearBuilderValido() {
        return new SolicitudServicio.Builder(
                "SOL-001",
                LocalDate.of(2026, 10, 15),
                crearCliente(),
                crearServicio()).build();
    }

    @Test
    void debeConstruirSolicitudConDatosCorrectos() {
        model.Solicitud solicitud = crearBuilderValido().construir();

        assertEquals("SOL-001", solicitud.getCodigo());
        assertEquals(
                LocalDate.of(2026, 10, 15),
                solicitud.getFechaCreacion()
        );
        assertEquals("Juan Pérez", solicitud.getCliente().getNombre());
        assertEquals(1, solicitud.getElementos().size());
    }

    @Test
    void debeAsignarPrioridadMediaPorDefecto() {
        model.Solicitud solicitud = crearBuilderValido().construir();

        assertEquals(PrioridadSolicitud.MEDIA, solicitud.getPrioridad());
    }

    @Test
    void debePermitirCambiarLaPrioridad() {
        model.Solicitud solicitud = crearBuilderValido()
                .prioridad(PrioridadSolicitud.ALTA)
                .construir();

        assertEquals(PrioridadSolicitud.ALTA, solicitud.getPrioridad());
    }

    @Test
    void debeAgregarVariosElementos() {
        ServicioTecnologico segundoServicioTecnologico = new ServicioTecnologico(
                "SER-002",
                "Mantenimiento correctivo",
                "Reparación de equipos.",
                120000
        );

        model.Solicitud solicitud = crearBuilderValido()
                .agregarElemento(segundoServicioTecnologico)
                .construir();

        assertEquals(2, solicitud.getElementos().size());
        assertEquals(200000, solicitud.calcularCostoTotal(), 0.001);
    }

    @Test
    void debeGuardarFechaProgramada() {
        LocalDate fecha = LocalDate.of(2026, 11, 1);

        model.Solicitud solicitud = crearBuilderValido()
                .fechaProgramada(fecha)
                .construir();

        assertEquals(fecha, solicitud.getFechaProgramada());
    }

    @Test
    void debeGuardarObservaciones() {
        model.Solicitud solicitud = crearBuilderValido()
                .observaciones("Atender en horario de la mañana")
                .construir();

        assertEquals(
                "Atender en horario de la mañana",
                solicitud.getObservaciones()
        );
    }

    @Test
    void debeRechazarCodigoNulo() {
        SolicitudServicio builder = crearBuilderValido()
                .codigo(null);

        assertThrows(
                IllegalArgumentException.class,
                builder::construir
        );
    }

    @Test
    void debeRechazarCodigoVacio() {
        SolicitudServicio builder = crearBuilderValido()
                .codigo(" ");

        assertThrows(
                IllegalArgumentException.class,
                builder::construir
        );
    }

    @Test
    void debeRechazarFechaDeCreacionNula() {
        SolicitudServicio builder = crearBuilderValido()
                .fechaCreacion(null);

        assertThrows(
                IllegalArgumentException.class,
                builder::construir
        );
    }

    @Test
    void debeRechazarClienteNulo() {
        SolicitudServicio builder = crearBuilderValido()
                .cliente(null);

        assertThrows(
                IllegalArgumentException.class,
                builder::construir
        );
    }

    @Test
    void debeRechazarSolicitudSinElementos() {
        SolicitudServicio builder = new SolicitudServicio()
                .codigo("SOL-002")
                .cliente(crearCliente());

        assertThrows(
                IllegalArgumentException.class,
                builder::construir
        );
    }

    @Test
    void debeRechazarAgregarElementoNulo() {
        SolicitudServicio builder = new SolicitudServicio();

        assertThrows(
                IllegalArgumentException.class,
                () -> builder.agregarElemento((ElementoServicio) null)
        );
    }

    @Test
    void debeRechazarPrioridadNula() {
        SolicitudServicio builder = crearBuilderValido()
                .prioridad(null);

        assertThrows(
                IllegalArgumentException.class,
                builder::construir
        );
    }
}