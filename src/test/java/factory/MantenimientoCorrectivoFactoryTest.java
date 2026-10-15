package factory;

import model.Servicio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para MantenimientoCorrectivoFactory.
 */
class MantenimientoCorrectivoFactoryTest {

    @Test
    void debeCrearServicioDeMantenimientoCorrectivo() {
        MantenimientoCorrectivoFactory factory =
                new MantenimientoCorrectivoFactory();

        Servicio servicio = factory.crearServicio();

        assertNotNull(servicio);
        assertEquals("SER-002", servicio.getCodigo());
        assertEquals("Mantenimiento correctivo", servicio.getNombre());
        assertEquals(
                "Diagnóstico y reparación de equipos.",
                servicio.getDescripcion()
        );
        assertEquals(120000, servicio.calcularCosto(), 0.001);
    }

    @Test
    void debeCrearUnServicioConCostoCorrecto() {
        MantenimientoCorrectivoFactory factory =
                new MantenimientoCorrectivoFactory();

        Servicio servicio = factory.crearServicio();

        assertEquals(120000, servicio.getCostoBase(), 0.001);
    }

    @Test
    void debeCrearInstanciasIndependientes() {
        MantenimientoCorrectivoFactory factory =
                new MantenimientoCorrectivoFactory();

        Servicio servicio1 = factory.crearServicio();
        Servicio servicio2 = factory.crearServicio();

        assertNotSame(servicio1, servicio2);
        assertEquals(servicio1.getCodigo(), servicio2.getCodigo());
        assertEquals(
                servicio1.calcularCosto(),
                servicio2.calcularCosto(),
                0.001
        );
    }
}