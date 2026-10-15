package adapter;

import external.LegacySupport;
import model.TecnicoInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para LegacySupportAdapter.
 */
class LegacySupportAdapterTest {

    @Test
    void debeConsultarTecnicoDisponibleDeEspecialidadUno() {
        LegacySupportAdapter adapter =
                new LegacySupportAdapter(new LegacySupport());

        TecnicoInfo tecnico = adapter.consultarTecnico(1);

        assertEquals("TEC-105", tecnico.getId());
        assertTrue(tecnico.isDisponible());
        assertEquals(30000, tecnico.getCostoDesplazamiento(), 0.001);
    }

    @Test
    void debeConsultarTecnicoDisponibleDeEspecialidadDos() {
        LegacySupportAdapter adapter =
                new LegacySupportAdapter(new LegacySupport());

        TecnicoInfo tecnico = adapter.consultarTecnico(2);

        assertEquals("TEC-208", tecnico.getId());
        assertTrue(tecnico.isDisponible());
        assertEquals(40000, tecnico.getCostoDesplazamiento(), 0.001);
    }

    @Test
    void debeConsultarTecnicoNoDisponibleDeEspecialidadTres() {
        LegacySupportAdapter adapter =
                new LegacySupportAdapter(new LegacySupport());

        TecnicoInfo tecnico = adapter.consultarTecnico(3);

        assertEquals("TEC-310", tecnico.getId());
        assertFalse(tecnico.isDisponible());
        assertEquals(0, tecnico.getCostoDesplazamiento(), 0.001);
    }

    @Test
    void debeDevolverTecnicoPredeterminadoParaEspecialidadDesconocida() {
        LegacySupportAdapter adapter =
                new LegacySupportAdapter(new LegacySupport());

        TecnicoInfo tecnico = adapter.consultarTecnico(99);

        assertEquals("SIN_TECNICO", tecnico.getId());
        assertFalse(tecnico.isDisponible());
        assertEquals(0, tecnico.getCostoDesplazamiento(), 0.001);
    }

    @Test
    void debeRechazarPlataformaExternaNula() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new LegacySupportAdapter(null)
        );
    }
}