package adapter;

import external.LegacySupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para LegacySupportAdapter.
 */
class LegacySupportAdapterTest {
    private final static java.util.logging.Logger LOGGER = Logger.getLogger(LegacySupportAdapterTest.class.getName());
    private final LegacySupport legacySupport = LegacySupport.getInstance();
    private LegacySupportAdapter adapter1, adapter2, adapter3, adapter4;
    TecnicoInfo tecnico1, tecnico2, tecnico3, tecnico4;

    @BeforeEach
    void setUp() {
        adapter1 =  new LegacySupportAdapter(legacySupport);
        adapter2 = new LegacySupportAdapter(legacySupport);
        adapter3 = new LegacySupportAdapter(legacySupport);
        adapter4 = new LegacySupportAdapter(legacySupport);
    }

    @Test
    void debeConsultarTecnicoDisponibleDeEspecialidadUno() {
        LOGGER.info("Iniciando debeConsultarTecnicoDisponibleDeEspecialidadUno");
        tecnico1 = adapter1.consultarTecnico(1);

        assertEquals("TEC-105", tecnico1.getId());
        assertTrue(tecnico1.isDisponible());
        assertEquals(30000, tecnico1.getCostoDesplazamiento(), 0.001);
        LOGGER.info("finalizando debeConsultarTecnicoDisponibleUno");
    }

    @Test
    void debeConsultarTecnicoDisponibleDeEspecialidadDos() {
        LOGGER.info("Iniciando debeConsultarTecnicoDisponibleDos");
        tecnico2 = adapter2.consultarTecnico(2);

        assertEquals("TEC-208", tecnico2.getId());
        assertTrue(tecnico2.isDisponible());
        assertEquals(40000, tecnico2.getCostoDesplazamiento(), 0.001);
        LOGGER.info("finalizando debeConsultarTecnicoDisponibleDos");
    }

    @Test
    void debeConsultarTecnicoNoDisponibleDeEspecialidadTres() {
        LOGGER.info("Iniciando debeConsultarTecnicoNoDisponibleTres");
        tecnico3 = adapter3.consultarTecnico(3);

        assertEquals("TEC-310", tecnico3.getId());
        assertFalse(tecnico3.isDisponible());
        assertEquals(0, tecnico3.getCostoDesplazamiento(), 0.001);
        LOGGER.info("Finalizando debeConsultarTecnicoNoDisponibleTres");
    }

    @Test
    void debeDevolverTecnicoPredeterminadoParaEspecialidadDesconocida() {
        LOGGER.info("Iniciando debeDevolverTecnicoDesconocida");
        tecnico4 = adapter4.consultarTecnico(99);

        assertEquals("SIN_TECNICO", tecnico4.getId());
        assertFalse(tecnico4.isDisponible());
        assertEquals(0, tecnico4.getCostoDesplazamiento(), 0.001);
        LOGGER.info("Finalizando debeDevolverTecnicoDesconocida");
    }

    @Test
    void debeRechazarPlataformaExternaNula() {
        LOGGER.info("Iniciando debeRechazarPlataformaExterna");
        assertThrows(
                IllegalArgumentException.class,
                () -> new LegacySupportAdapter(null)
        );
        LOGGER.info("Finalizando debeRechazarPlataformaExterna");
    }
}