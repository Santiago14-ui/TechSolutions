package service;

import adapter.LegacySupportAdapter;
import external.LegacySupport;
import adapter.TecnicoInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para TecnicoService.
 */
class TecnicoServiceTest {
    private final static java.util.logging.Logger LOGGER = Logger.getLogger(TecnicoServiceTest.class.getName());
    private final LegacySupport legacySupport = LegacySupport.getInstance();

    @BeforeEach
    void setUp() {
    }


    @Test
    void debeConsultarTecnicoDisponible() {
        LOGGER.info("Iniciando debeConsultarTecnico");
        TecnicoService service = new TecnicoService(
                new LegacySupportAdapter(legacySupport)
        );

        TecnicoInfo tecnico = service.consultarTecnico(1);

        assertEquals("TEC-105", tecnico.getId());
        assertTrue(tecnico.isDisponible());
        assertEquals(30000, tecnico.getCostoDesplazamiento(), 0.001);
        LOGGER.info("Finalizando debeConsultarTecnico");
    }

    @Test
    void debeConsultarOtroTecnicoDisponible() {
        LOGGER.info("Iniciando debeConsultarOtroTecnico");
        TecnicoService service = new TecnicoService(
                new LegacySupportAdapter(legacySupport)
        );

        TecnicoInfo tecnico = service.consultarTecnico(2);

        assertEquals("TEC-208", tecnico.getId());
        assertTrue(tecnico.isDisponible());
        assertEquals(40000, tecnico.getCostoDesplazamiento(), 0.001);
        LOGGER.info("Finalizando debeConsultarOtroTecnico");
    }

    @Test
    void debeConsultarTecnicoNoDisponible() {
        LOGGER.info("Iniciando debeConsultarTecnico");
        TecnicoService service = new TecnicoService(new LegacySupportAdapter(legacySupport));

        TecnicoInfo tecnico = service.consultarTecnico(3);

        assertEquals("TEC-310", tecnico.getId());
        assertFalse(tecnico.isDisponible());
        assertEquals(0, tecnico.getCostoDesplazamiento(), 0.001);
        LOGGER.info("Finalizando debeConsultarTecnico");
    }

    @Test
    void debeRechazarAdapterNulo() {
        LOGGER.info("Iniciando debeRechazarAdapterNulo");
        assertThrows(
                IllegalArgumentException.class,
                () -> new TecnicoService(null)
        );
        LOGGER.info("Finalizando debeRechazarAdapterNulo");
    }
}