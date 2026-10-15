package service;

import model.ServicioTecnologico;
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

        List<ServicioTecnologico> servicioTecnologicos = catalogo.obtenerServicios();

        assertEquals(4, servicioTecnologicos.size());
    }

    @Test
    void debeEncontrarMantenimientoPreventivoPorCodigo() {
        CatalogoService catalogo = new CatalogoService();

        ServicioTecnologico servicioTecnologico = catalogo.buscarPorCodigo("SER-001");

        assertNotNull(servicioTecnologico);
        assertEquals("Mantenimiento preventivo", servicioTecnologico.getNombre());
        assertEquals(80000, servicioTecnologico.calcularCosto(), 0.001);
    }

    @Test
    void debeEncontrarMantenimientoCorrectivoPorCodigo() {
        CatalogoService catalogo = new CatalogoService();

        ServicioTecnologico servicioTecnologico = catalogo.buscarPorCodigo("SER-002");

        assertNotNull(servicioTecnologico);
        assertEquals("Mantenimiento correctivo", servicioTecnologico.getNombre());
        assertEquals(120000, servicioTecnologico.calcularCosto(), 0.001);
    }

    @Test
    void debeIgnorarMayusculasYMinusculasEnLaBusqueda() {
        CatalogoService catalogo = new CatalogoService();

        ServicioTecnologico servicioTecnologico = catalogo.buscarPorCodigo("ser-001");

        assertNotNull(servicioTecnologico);
        assertEquals("SER-001", servicioTecnologico.getCodigo());
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

        List<ServicioTecnologico> servicioTecnologicos = catalogo.obtenerServicios();

        assertThrows(
                UnsupportedOperationException.class,
                servicioTecnologicos::clear
        );

        assertEquals(4, catalogo.obtenerServicios().size());
    }
}