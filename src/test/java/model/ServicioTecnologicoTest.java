package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase Servicio.
 */
class ServicioTecnologicoTest {

    @Test
    void debeCrearServicioConDatosCorrectos() {
        ServicioTecnologico servicioTecnologico = new ServicioTecnologico(
                "SER-001",
                "Mantenimiento preventivo",
                "Revisión y mantenimiento preventivo de equipos.",
                80000
        );

        assertEquals("SER-001", servicioTecnologico.getCodigo());
        assertEquals("Mantenimiento preventivo", servicioTecnologico.getNombre());
        assertEquals(
                "Revisión y mantenimiento preventivo de equipos.",
                servicioTecnologico.getDescripcion()
        );
        assertEquals(80000, servicioTecnologico.getCostoBase(), 0.001);
    }

    @Test
    void debeCalcularCostoBaseCorrectamente() {
        ServicioTecnologico servicioTecnologico = new ServicioTecnologico(
                "SER-001",
                "Mantenimiento preventivo",
                "Revisión de equipos.",
                80000
        );

        assertEquals(80000, servicioTecnologico.calcularCosto(), 0.001);
    }

    @Test
    void debePermitirCostoCero() {
        ServicioTecnologico servicioTecnologico = new ServicioTecnologico(
                "SER-000",
                "Diagnóstico",
                "Evaluación inicial del equipo.",
                0
        );

        assertEquals(0, servicioTecnologico.calcularCosto(), 0.001);
    }

    @Test
    void debeRechazarCodigoNulo() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new ServicioTecnologico(
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
                () -> new ServicioTecnologico(
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
                () -> new ServicioTecnologico(
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
                () -> new ServicioTecnologico(
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
                () -> new ServicioTecnologico(
                        "SER-001",
                        "Mantenimiento preventivo",
                        "Revisión de equipos.",
                        -1000
                )
        );
    }
}