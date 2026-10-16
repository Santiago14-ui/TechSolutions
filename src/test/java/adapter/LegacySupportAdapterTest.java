package adapter;

import external.LegacySupport;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LegacySupportAdapterTest {

    @Test
    void consultaTecnicoDeMantenimiento() {
        TecnicoInfo tecnico = new LegacySupportAdapter(
                LegacySupport.getInstance()
        ).consultarTecnico("Mantenimiento");

        assertEquals("TEC-105", tecnico.getId());
        assertTrue(tecnico.isDisponible());
        assertEquals(30000, tecnico.getCostoDesplazamiento(), 0.001);
    }

    @Test
    void consultaTecnicoDeRedes() {
        TecnicoInfo tecnico = new LegacySupportAdapter(
                LegacySupport.getInstance()
        ).consultarTecnico("Instalación de redes");

        assertEquals("TEC-208", tecnico.getId());
        assertEquals(40000, tecnico.getCostoDesplazamiento(), 0.001);
    }

    @Test
    void consultaTecnicoNoDisponible() {
        TecnicoInfo tecnico = new LegacySupportAdapter(
                LegacySupport.getInstance()
        ).consultarTecnico("Recuperación de información");

        assertEquals("TEC-310", tecnico.getId());
        assertFalse(tecnico.isDisponible());
    }

    @Test
    void usaTecnicoPredeterminadoParaEspecialidadDesconocida() {
        TecnicoInfo tecnico = new LegacySupportAdapter(
                LegacySupport.getInstance()
        ).consultarTecnico("otra");

        assertEquals("SIN_TECNICO", tecnico.getId());
        assertFalse(tecnico.isDisponible());
    }

    @Test
    void rechazaPlataformaNula() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new LegacySupportAdapter(null)
        );
    }
}