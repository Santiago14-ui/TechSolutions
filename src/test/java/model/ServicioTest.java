package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase Servicio.
 */
class ServicioTest {

    @Test
    void debeCrearServicioConDatosCorrectos() {
        Servicio servicio = new Servicio(
                "SER-001",
                "Mantenimiento preventivo",
                "Revisión y mantenimiento preventivo de equipos.",
                80000
        );

        assertEquals("SER-001", servicio.getCodigo());
        assertEquals("Mantenimiento preventivo", servicio.getNombre());
        assertEquals(
                "Revisión y mantenimiento preventivo de equipos.",
                servicio.getDescripcion()
        );
        assertEquals(80000, servicio.getCostoBase(), 0.001);
    }

    @Test
    void debeCalcularCostoBaseCorrectamente() {
        Servicio servicio = new Servicio(
                "SER-001",
                "Mantenimiento preventivo",
                "Revisión de equipos.",
                80000
        );

        assertEquals(80000, servicio.calcularCosto(), 0.001);
    }

    @Test
    void debePermitirCostoCero() {
        Servicio servicio = new Servicio(
                "SER-000",
                "Diagnóstico",
                "Evaluación inicial del equipo.",
                0
        );

        assertEquals(0, servicio.calcularCosto(), 0.001);
    }

    @Test
    void debeRechazarCodigoNulo() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Servicio(
                        null,
                        "Mantenimiento preventivo",
                        "Revisión de equipos.",
                        80000
                )
        );
    }

    @Test
    void debeRechazarCodigoVacio() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Servicio(
                        " ",
                        "Mantenimiento preventivo",
                        "Revisión de equipos.",
                        80000
                )
        );
    }

    @Test
    void debeRechazarNombreNulo() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Servicio(
                        "SER-001",
                        null,
                        "Revisión de equipos.",
                        80000
                )
        );
    }

    @Test
    void debeRechazarDescripcionVacia() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Servicio(
                        "SER-001",
                        "Mantenimiento preventivo",
                        "",
                        80000
                )
        );
    }

    @Test
    void debeRechazarCostoNegativo() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Servicio(
                        "SER-001",
                        "Mantenimiento preventivo",
                        "Revisión de equipos.",
                        -1000
                )
        );
    }
}