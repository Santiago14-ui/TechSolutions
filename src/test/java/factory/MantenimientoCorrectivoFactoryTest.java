package factory;

import model.ServicioTecnologico;
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

        ServicioTecnologico servicioTecnologico = factory.crearServicio();

        assertNotNull(servicioTecnologico);
        assertEquals("SER-002", servicioTecnologico.getCodigo());
        assertEquals("Mantenimiento correctivo", servicioTecnologico.getNombre());
        assertEquals(
                "Diagnóstico y reparación de equipos.",
                servicioTecnologico.getDescripcion()
        );
        assertEquals(120000, servicioTecnologico.calcularCosto(), 0.001);
    }

    @Test
    void debeCrearUnServicioConCostoCorrecto() {
        MantenimientoCorrectivoFactory factory =
                new MantenimientoCorrectivoFactory();

        ServicioTecnologico servicioTecnologico = factory.crearServicio();

        assertEquals(120000, servicioTecnologico.getCostoBase(), 0.001);
    }

    @Test
    void debeCrearInstanciasIndependientes() {
        MantenimientoCorrectivoFactory factory =
                new MantenimientoCorrectivoFactory();

        ServicioTecnologico servicioTecnologico1 = factory.crearServicio();
        ServicioTecnologico servicioTecnologico2 = factory.crearServicio();

        assertNotSame(servicioTecnologico1, servicioTecnologico2);
        assertEquals(servicioTecnologico1.getCodigo(), servicioTecnologico2.getCodigo());
        assertEquals(
                servicioTecnologico1.calcularCosto(),
                servicioTecnologico2.calcularCosto(),
                0.001
        );
    }
}