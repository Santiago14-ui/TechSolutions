package factory;

import model.Servicio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para MantenimientoPreventivoFactory.
 */
class MantenimientoPreventivoFactoryTest {

    @Test
    void debeCrearServicioDeMantenimientoPreventivo() {
        MantenimientoPreventivoFactory factory =
                new MantenimientoPreventivoFactory();

        Servicio servicio = factory.crearServicio();

        assertNotNull(servicio);
        assertEquals("SER-001", servicio.getCodigo());
        assertEquals("Mantenimiento preventivo", servicio.getNombre());
        assertEquals(
                "Revisión y mantenimiento preventivo de equipos.",
                servicio.getDescripcion()
        );
        assertEquals(80000, servicio.calcularCosto(), 0.001);
    }

    @Test
    void debeCrearUnServicioConCostoCorrecto() {
        MantenimientoPreventivoFactory factory =
                new MantenimientoPreventivoFactory();

        Servicio servicio = factory.crearServicio();

        assertEquals(80000, servicio.getCostoBase(), 0.001);
    }

    @Test
    void debeCrearInstanciasIndependientes() {
        MantenimientoPreventivoFactory factory =
                new MantenimientoPreventivoFactory();

        Servicio servicio1 = factory.crearServicio();
        Servicio servicio2 = factory.crearServicio();

        assertNotSame(servicio1, servicio2);
        assertEquals(servicio1.getCodigo(), servicio2.getCodigo());
        assertEquals(servicio1.calcularCosto(), servicio2.calcularCosto(), 0.001);
    }
}