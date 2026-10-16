package factory;

import model.ServicioTecnologico;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MantenimientoCorrectivoFactoryTest {

    @Test
    void creaServicioConDatosCorrectos() {
        ServicioTecnologico servicio =
                new MantenimientoCorrectivoServicioTecnologicoFactory()
                        .crearServicioTecnologico(
                                "SER-002",
                                "Reparación de equipos"
                        );

        assertNotNull(servicio);
        assertEquals("SER-002", servicio.getCodigo());
        assertEquals("Mantenimiento correctivo", servicio.getNombre());
        assertEquals(
                "Reparación de equipos",
                servicio.getDescripcion()
        );
        assertEquals(120000, servicio.calcularCosto(), 0.001);
        assertEquals(
                "Mantenimiento correctivo",
                servicio.getTipoServicioTecnologico()
        );
    }

    @Test
    void creaInstanciasIndependientes() {
        MantenimientoCorrectivoServicioTecnologicoFactory factory =
                new MantenimientoCorrectivoServicioTecnologicoFactory();

        assertNotSame(
                factory.crearServicioTecnologico("SER-002", "Reparación"),
                factory.crearServicioTecnologico("SER-002", "Reparación")
        );
    }
}