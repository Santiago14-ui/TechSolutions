package factory;

import model.ServicioTecnologico;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MantenimientoPreventivoFactoryTest {

    @Test
    void creaServicioConDatosCorrectos() {
        ServicioTecnologico servicio =
                new MantenimientoPreventivoServicioTecnologicoFactory()
                        .crearServicioTecnologico(
                                "SER-001",
                                "Revisión preventiva de equipos"
                        );

        assertNotNull(servicio);
        assertEquals("SER-001", servicio.getCodigo());
        assertEquals("Mantenimiento preventivo", servicio.getNombre());
        assertEquals(
                "Revisión preventiva de equipos",
                servicio.getDescripcion()
        );
        assertEquals(80000, servicio.calcularCosto(), 0.001);
        assertEquals(
                "Mantenimiento preventivo",
                servicio.getTipoServicioTecnologico()
        );
    }

    @Test
    void creaInstanciasIndependientes() {
        MantenimientoPreventivoServicioTecnologicoFactory factory =
                new MantenimientoPreventivoServicioTecnologicoFactory();

        assertNotSame(
                factory.crearServicioTecnologico("SER-001", "Revisión"),
                factory.crearServicioTecnologico("SER-001", "Revisión")
        );
    }
}