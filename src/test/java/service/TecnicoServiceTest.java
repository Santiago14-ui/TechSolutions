package service;

import adapter.LegacySupportAdapter;
import external.LegacySupport;
import model.TecnicoInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para TecnicoService.
 */
class TecnicoServiceTest {

    @Test
    void debeConsultarTecnicoDisponible() {
        TecnicoService service = new TecnicoService(
                new LegacySupportAdapter(new LegacySupport())
        );

        TecnicoInfo tecnico = service.consultarTecnico(1);

        assertEquals("TEC-105", tecnico.getId());
        assertTrue(tecnico.isDisponible());
        assertEquals(30000, tecnico.getCostoDesplazamiento(), 0.001);
    }

    @Test
    void debeConsultarOtroTecnicoDisponible() {
        TecnicoService service = new TecnicoService(
                new LegacySupportAdapter(new LegacySupport())
        );

        TecnicoInfo tecnico = service.consultarTecnico(2);

        assertEquals("TEC-208", tecnico.getId());
        assertTrue(tecnico.isDisponible());
        assertEquals(40000, tecnico.getCostoDesplazamiento(), 0.001);
    }

    @Test
    void debeConsultarTecnicoNoDisponible() {
        TecnicoService service = new TecnicoService(
                new LegacySupportAdapter(new LegacySupport())
        );

        TecnicoInfo tecnico = service.consultarTecnico(3);

        assertEquals("TEC-310", tecnico.getId());
        assertFalse(tecnico.isDisponible());
        assertEquals(0, tecnico.getCostoDesplazamiento(), 0.001);
    }

    @Test
    void debeRechazarAdapterNulo() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new TecnicoService(null)
        );
    }
}