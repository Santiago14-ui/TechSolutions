package service;

import model.Servicio;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para CatalogoService.
 */
class CatalogoServiceTest {

    @Test
    void debeCargarLosCuatroServiciosIniciales() {
        CatalogoService catalogo = new CatalogoService();

        List<Servicio> servicios = catalogo.obtenerServicios();

        assertEquals(4, servicios.size());
    }

    @Test
    void debeEncontrarMantenimientoPreventivoPorCodigo() {
        CatalogoService catalogo = new CatalogoService();

        Servicio servicio = catalogo.buscarPorCodigo("SER-001");

        assertNotNull(servicio);
        assertEquals("Mantenimiento preventivo", servicio.getNombre());
        assertEquals(80000, servicio.calcularCosto(), 0.001);
    }

    @Test
    void debeEncontrarMantenimientoCorrectivoPorCodigo() {
        CatalogoService catalogo = new CatalogoService();

        Servicio servicio = catalogo.buscarPorCodigo("SER-002");

        assertNotNull(servicio);
        assertEquals("Mantenimiento correctivo", servicio.getNombre());
        assertEquals(120000, servicio.calcularCosto(), 0.001);
    }

    @Test
    void debeIgnorarMayusculasYMinusculasEnLaBusqueda() {
        CatalogoService catalogo = new CatalogoService();

        Servicio servicio = catalogo.buscarPorCodigo("ser-001");

        assertNotNull(servicio);
        assertEquals("SER-001", servicio.getCodigo());
    }

    @Test
    void debeRetornarNuloCuandoElCodigoNoExiste() {
        CatalogoService catalogo = new CatalogoService();

        assertNull(catalogo.buscarPorCodigo("SER-999"));
    }

    @Test
    void debeRetornarNuloCuandoElCodigoEsNulo() {
        CatalogoService catalogo = new CatalogoService();

        assertNull(catalogo.buscarPorCodigo(null));
    }

    @Test
    void debeRetornarNuloCuandoElCodigoEstaVacio() {
        CatalogoService catalogo = new CatalogoService();

        assertNull(catalogo.buscarPorCodigo(" "));
    }

    @Test
    void noDebePermitirModificarLaListaDelCatalogo() {
        CatalogoService catalogo = new CatalogoService();

        List<Servicio> servicios = catalogo.obtenerServicios();

        assertThrows(
                UnsupportedOperationException.class,
                servicios::clear
        );

        assertEquals(4, catalogo.obtenerServicios().size());
    }
}