package factory;

import model.ServicioTecnologico;
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

        ServicioTecnologico servicioTecnologico = factory.crearServicio();

        assertNotNull(servicioTecnologico);
        assertEquals("SER-001", servicioTecnologico.getCodigo());
        assertEquals("Mantenimiento preventivo", servicioTecnologico.getNombre());
        assertEquals(
                "Revisión y mantenimiento preventivo de equipos.",
                servicioTecnologico.getDescripcion()
        );
        assertEquals(80000, servicioTecnologico.calcularCosto(), 0.001);
    }

    @Test
    void debeCrearUnServicioConCostoCorrecto() {
        MantenimientoPreventivoFactory factory =
                new MantenimientoPreventivoFactory();

        ServicioTecnologico servicioTecnologico = factory.crearServicio();

        assertEquals(80000, servicioTecnologico.getCostoBase(), 0.001);
    }

    @Test
    void debeCrearInstanciasIndependientes() {
        MantenimientoPreventivoFactory factory =
                new MantenimientoPreventivoFactory();

        ServicioTecnologico servicioTecnologico1 = factory.crearServicio();
        ServicioTecnologico servicioTecnologico2 = factory.crearServicio();

        assertNotSame(servicioTecnologico1, servicioTecnologico2);
        assertEquals(servicioTecnologico1.getCodigo(), servicioTecnologico2.getCodigo());
        assertEquals(servicioTecnologico1.calcularCosto(), servicioTecnologico2.calcularCosto(), 0.001);
    }
}