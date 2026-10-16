package service;

import adapter.LegacySupportAdapter;
import adapter.TecnicoInfo;
import external.LegacySupport;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TecnicoServiceTest {

    @Test
    void consultaTecnicoDeMantenimiento() {
        TecnicoInfo tecnico = new TecnicoService(
                new LegacySupportAdapter(LegacySupport.getInstance())
        ).consultarTecnico("Mantenimiento");

        assertEquals("TEC-105", tecnico.getId());
        assertTrue(tecnico.isDisponible());
        assertEquals(30000, tecnico.getCostoDesplazamiento(), 0.001);
    }

    @Test
    void consultaTecnicoDeRedes() {
        TecnicoInfo tecnico = new TecnicoService(
                new LegacySupportAdapter(LegacySupport.getInstance())
        ).consultarTecnico("Instalación de redes");

        assertEquals("TEC-208", tecnico.getId());
    }

    @Test
    void rechazaAdapterNulo() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new TecnicoService(null)
        );
    }
}