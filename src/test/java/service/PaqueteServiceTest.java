package service;

import model.PaqueteServicios;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para PaqueteService.
 */
class PaqueteServiceTest {

    private PaqueteService service;

    @BeforeEach
    void configurar() {
        service = new PaqueteService();
    }

    private PaqueteServicios crearPaquete(String codigo) {
        return new PaqueteServicios(
                codigo,
                "Paquete básico",
                "Paquete de mantenimiento básico."
        );
    }

    @Test
    void debeRegistrarUnPaquete() {
        PaqueteServicios paquete = crearPaquete("PAQ-001");

        service.registrarPaquete(paquete);

        assertEquals(1, service.obtenerPaquetes().size());
        assertSame(paquete, service.buscarPorCodigo("PAQ-001"));
    }

    @Test
    void debeRechazarRegistrarPaqueteNulo() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.registrarPaquete(null)
        );
    }

    @Test
    void debeRechazarCodigosDuplicados() {
        service.registrarPaquete(crearPaquete("PAQ-001"));

        assertThrows(
                IllegalArgumentException.class,
                () -> service.registrarPaquete(crearPaquete("PAQ-001"))
        );
    }

    @Test
    void debeRechazarCodigosDuplicadosSinImportarMayusculas() {
        service.registrarPaquete(crearPaquete("PAQ-001"));

        assertThrows(
                IllegalArgumentException.class,
                () -> service.registrarPaquete(crearPaquete("paq-001"))
        );
    }

    @Test
    void debeBuscarPaqueteIgnorandoMayusculas() {
        PaqueteServicios paquete = crearPaquete("PAQ-001");
        service.registrarPaquete(paquete);

        assertSame(paquete, service.buscarPorCodigo("paq-001"));
    }

    @Test
    void debeRetornarNuloSiElPaqueteNoExiste() {
        assertNull(service.buscarPorCodigo("PAQ-999"));
    }

    @Test
    void debeRetornarNuloSiElCodigoEsNuloOVacio() {
        assertNull(service.buscarPorCodigo(null));
        assertNull(service.buscarPorCodigo(" "));
    }

    @Test
    void noDebePermitirModificarLaListaDePaquetes() {
        service.registrarPaquete(crearPaquete("PAQ-001"));

        List<PaqueteServicios> paquetes = service.obtenerPaquetes();

        assertThrows(
                UnsupportedOperationException.class,
                paquetes::clear
        );

        assertEquals(1, service.obtenerPaquetes().size());
    }
}