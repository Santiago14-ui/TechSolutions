package builder;

import model.Cliente;
import model.ElementoServicio;
import model.PrioridadSolicitud;
import model.Servicio;
import model.Solicitud;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase SolicitudBuilder.
 */
class SolicitudBuilderTest {

    private Cliente crearCliente() {
        return new Cliente(
                "123456789",
                "Juan Pérez",
                "juan@gmail.com"
        );
    }

    private Servicio crearServicio() {
        return new Servicio(
                "SER-001",
                "Mantenimiento preventivo",
                "Revisión y mantenimiento preventivo de equipos.",
                80000
        );
    }

    private SolicitudBuilder crearBuilderValido() {
        return new SolicitudBuilder()
                .codigo("SOL-001")
                .fechaCreacion(LocalDate.of(2026, 10, 15))
                .cliente(crearCliente())
                .agregarElemento(crearServicio());
    }

    @Test
    void debeConstruirSolicitudConDatosCorrectos() {
        Solicitud solicitud = crearBuilderValido().construir();

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
        Solicitud solicitud = crearBuilderValido().construir();

        assertEquals(PrioridadSolicitud.MEDIA, solicitud.getPrioridad());
    }

    @Test
    void debePermitirCambiarLaPrioridad() {
        Solicitud solicitud = crearBuilderValido()
                .prioridad(PrioridadSolicitud.ALTA)
                .construir();

        assertEquals(PrioridadSolicitud.ALTA, solicitud.getPrioridad());
    }

    @Test
    void debeAgregarVariosElementos() {
        Servicio segundoServicio = new Servicio(
                "SER-002",
                "Mantenimiento correctivo",
                "Reparación de equipos.",
                120000
        );

        Solicitud solicitud = crearBuilderValido()
                .agregarElemento(segundoServicio)
                .construir();

        assertEquals(2, solicitud.getElementos().size());
        assertEquals(200000, solicitud.calcularCostoTotal(), 0.001);
    }

    @Test
    void debeGuardarFechaProgramada() {
        LocalDate fecha = LocalDate.of(2026, 11, 1);

        Solicitud solicitud = crearBuilderValido()
                .fechaProgramada(fecha)
                .construir();

        assertEquals(fecha, solicitud.getFechaProgramada());
    }

    @Test
    void debeGuardarObservaciones() {
        Solicitud solicitud = crearBuilderValido()
                .observaciones("Atender en horario de la mañana")
                .construir();

        assertEquals(
                "Atender en horario de la mañana",
                solicitud.getObservaciones()
        );
    }

    @Test
    void debeRechazarCodigoNulo() {
        SolicitudBuilder builder = crearBuilderValido()
                .codigo(null);

        assertThrows(
                IllegalArgumentException.class,
                builder::construir
        );
    }

    @Test
    void debeRechazarCodigoVacio() {
        SolicitudBuilder builder = crearBuilderValido()
                .codigo(" ");

        assertThrows(
                IllegalArgumentException.class,
                builder::construir
        );
    }

    @Test
    void debeRechazarFechaDeCreacionNula() {
        SolicitudBuilder builder = crearBuilderValido()
                .fechaCreacion(null);

        assertThrows(
                IllegalArgumentException.class,
                builder::construir
        );
    }

    @Test
    void debeRechazarClienteNulo() {
        SolicitudBuilder builder = crearBuilderValido()
                .cliente(null);

        assertThrows(
                IllegalArgumentException.class,
                builder::construir
        );
    }

    @Test
    void debeRechazarSolicitudSinElementos() {
        SolicitudBuilder builder = new SolicitudBuilder()
                .codigo("SOL-002")
                .cliente(crearCliente());

        assertThrows(
                IllegalArgumentException.class,
                builder::construir
        );
    }

    @Test
    void debeRechazarAgregarElementoNulo() {
        SolicitudBuilder builder = new SolicitudBuilder();

        assertThrows(
                IllegalArgumentException.class,
                () -> builder.agregarElemento((ElementoServicio) null)
        );
    }

    @Test
    void debeRechazarPrioridadNula() {
        SolicitudBuilder builder = crearBuilderValido()
                .prioridad(null);

        assertThrows(
                IllegalArgumentException.class,
                builder::construir
        );
    }
}