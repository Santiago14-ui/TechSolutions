package model;

import factory.MantenimientoPreventivoServicioTecnologicoFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServicioTecnologicoTest {

    @Test
    void creaServicioConDatosYTipoString() {
        ServicioTecnologico servicio =
                new MantenimientoPreventivoServicioTecnologicoFactory()
                        .crearServicioTecnologico("SER-001", "Revisión preventiva");

        assertEquals("SER-001", servicio.getCodigo());
        assertEquals("Mantenimiento preventivo", servicio.getNombre());
        assertEquals(80000, servicio.getCostoBase(), 0.001);
        assertEquals(
                "Mantenimiento preventivo",
                servicio.getTipoServicioTecnologico()
        );
    }

    @Test
    void calculaCostoBase() {
        ServicioTecnologico servicio =
                new MantenimientoPreventivoServicioTecnologicoFactory()
                        .crearServicioTecnologico("SER-001", "Revisión");

        assertEquals(80000, servicio.calcularCosto(), 0.001);
    }

    @Test
    void representaTipoEnToString() {
        ServicioTecnologico servicio =
                new MantenimientoPreventivoServicioTecnologicoFactory()
                        .crearServicioTecnologico("SER-001", "Revisión");

        assertTrue(
                servicio.toString().contains(
                        "tipoServicioTecnologico='Mantenimiento preventivo'"
                )
        );
    }
}