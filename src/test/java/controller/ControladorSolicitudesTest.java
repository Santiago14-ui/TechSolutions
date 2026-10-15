package controller;

import adapter.LegacySupportAdapter;
import external.LegacySupport;
import model.Cliente;
import model.PrioridadSolicitud;
import model.ServicioTecnologico;
import model.Solicitud;
import adapter.TecnicoInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.CatalogoService;
import service.ClienteService;
import service.PaqueteService;
import service.SolicitudServicioService;
import service.TecnicoService;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para ControladorSolicitudes.
 */
class ControladorSolicitudesTest {

    private ControladorSolicitudes controlador;

    @BeforeEach
    void configurar() {
        controlador = new ControladorSolicitudes(
                new CatalogoService(),
                new ClienteService(),
                new PaqueteService(),
                new SolicitudServicioService(),
                new TecnicoService(
                        new LegacySupportAdapter(LegacySupport.getInstance())
                )
        );
    }

    @Test
    void debeRechazarServiciosNulosEnElConstructor() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new ControladorSolicitudes(
                        null,
                        new ClienteService(),
                        new PaqueteService(),
                        new SolicitudServicioService(),
                        new TecnicoService(
                                new LegacySupportAdapter(LegacySupport.getInstance())
                        )
                )
        );
    }

    @Test
    void debeObtenerLosClientesIniciales() {
        List<Cliente> clientes = controlador.obtenerClientes();

        assertEquals(4, clientes.size());
        assertEquals("1094900001", clientes.getFirst().getDocumento());
    }

    @Test
    void debeObtenerLosServiciosDelCatalogo() {
        List<ServicioTecnologico> servicioTecnologicos = controlador.obtenerServicios();

        assertEquals(4, servicioTecnologicos.size());
        assertNotNull(servicioTecnologicos);
    }

    @Test
    void debeAgregarElementoSeleccionado() {
        ServicioTecnologico servicioTecnologico = crearServicio();

        controlador.agregarElemento(servicioTecnologico);

        assertEquals(1, controlador.obtenerElementosSeleccionados().size());
        assertSame(servicioTecnologico, controlador.obtenerElementosSeleccionados().getFirst());
    }

    @Test
    void debeRechazarAgregarElementoNulo() {
        assertThrows(
                IllegalArgumentException.class,
                () -> controlador.agregarElemento(null)
        );
    }

    @Test
    void debeCalcularCostoDeElementosSeleccionados() {
        controlador.agregarElemento(crearServicio());

        assertEquals(80000, controlador.calcularCostoElementos(), 0.001);
    }

    @Test
    void debeCalcularCostoConTodosLosComplementos() {
        controlador.agregarElemento(crearServicio());

        double total = controlador.calcularCostoConComplementos(
                true,
                true,
                true
        );

        assertEquals(190000, total, 0.001);
    }

    @Test
    void debeCalcularCostoSinComplementos() {
        controlador.agregarElemento(crearServicio());

        double total = controlador.calcularCostoConComplementos(
                false,
                false,
                false
        );

        assertEquals(80000, total, 0.001);
    }

    @Test
    void debeLimpiarLosElementosSeleccionados() {
        controlador.agregarElemento(crearServicio());

        controlador.limpiarElementosSeleccionados();

        assertTrue(controlador.obtenerElementosSeleccionados().isEmpty());
        assertEquals(0, controlador.calcularCostoElementos(), 0.001);
    }

    @Test
    void debeRegistrarSolicitudCorrectamente() {
        controlador.agregarElemento(crearServicio());

        Solicitud solicitud = controlador.registrarSolicitud(
                "SOL-001",
                "1094900001",
                LocalDate.of(2026, 11, 1),
                "Revisar equipo",
                PrioridadSolicitud.ALTA
        );

        assertNotNull(solicitud);
        assertEquals("SOL-001", solicitud.getCodigo());
        assertEquals("1094900001", solicitud.getCliente().getDocumento());
        assertEquals(80000, solicitud.calcularCostoTotal(), 0.001);
        assertEquals(1, controlador.obtenerSolicitudes().size());
        assertTrue(controlador.obtenerElementosSeleccionados().isEmpty());
    }

    @Test
    void debeRechazarRegistrarSolicitudSinElementos() {
        assertThrows(
                IllegalArgumentException.class,
                () -> controlador.registrarSolicitud(
                        "SOL-002",
                        "1094900001",
                        null,
                        "",
                        PrioridadSolicitud.MEDIA
                )
        );
    }

    @Test
    void debeRechazarRegistrarSolicitudConClienteInexistente() {
        controlador.agregarElemento(crearServicio());

        assertThrows(
                IllegalArgumentException.class,
                () -> controlador.registrarSolicitud(
                        "SOL-003",
                        "0000000000",
                        null,
                        "",
                        PrioridadSolicitud.MEDIA
                )
        );
    }

    @Test
    void debeConsultarTecnicoMedianteElAdapter() {
        TecnicoInfo tecnico = controlador.consultarTecnico(1);

        assertEquals("TEC-105", tecnico.getId());
        assertTrue(tecnico.isDisponible());
        assertEquals(30000, tecnico.getCostoDesplazamiento(), 0.001);
    }

    @Test
    void debeCalcularCostoTotalConDesplazamientoPresencial() {
        controlador.agregarElemento(crearServicio());

        Solicitud solicitud = controlador.registrarSolicitud(
                "SOL-004",
                "1094900001",
                null,
                "",
                PrioridadSolicitud.MEDIA
        );

        TecnicoInfo tecnico = controlador.consultarTecnico(1);

        assertEquals(
                110000,
                controlador.calcularCostoTotal(solicitud, true, tecnico),
                0.001
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
}