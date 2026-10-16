package service;

import model.ServicioTecnologico;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CatalogoServiceTest {

    @Test
    void cargaCuatroServicios() {
        assertEquals(4, new CatalogoService().obtenerServicios().size());
    }

    @Test
    void encuentraServicioPorCodigo() {
        CatalogoService catalogo = new CatalogoService();

        ServicioTecnologico servicio =
                catalogo.buscarPorCodigo("ST-001");

        assertNotNull(servicio);
        assertEquals("Mantenimiento preventivo", servicio.getNombre());
        assertEquals(80000, servicio.calcularCosto(), 0.001);
    }

    @Test
    void ignoraMayusculasYEspaciosEnCodigo() {
        ServicioTecnologico servicio =
                new CatalogoService().buscarPorCodigo(" st-001 ");

        assertEquals("ST-001", servicio.getCodigo());
    }

    @Test
    void devuelveNuloParaCodigoNoEncontradoOVacio() {
        CatalogoService catalogo = new CatalogoService();

        assertNull(catalogo.buscarPorCodigo("ST-999"));
        assertNull(catalogo.buscarPorCodigo(null));
        assertNull(catalogo.buscarPorCodigo(" "));
    }

    @Test
    void creaServicioConTipoTexto() {
        CatalogoService catalogo = new CatalogoService();

        ServicioTecnologico servicio = catalogo.crearServicio(
                "ST-005",
                "Diagnóstico de equipos",
                "Mantenimiento preventivo"
        );

        assertEquals(
                "Mantenimiento preventivo",
                servicio.getTipoServicioTecnologico()
        );
    }

    @Test
    void rechazaTipoInvalido() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new CatalogoService().crearServicio(
                        "ST-005",
                        "Descripción",
                        "Desconocido"
                )
        );
    }

    @Test
    void listaNoEsModificable() {
        List<ServicioTecnologico> lista =
                new CatalogoService().obtenerServicios();

        assertThrows(
                UnsupportedOperationException.class,
                lista::clear
        );
    }
}