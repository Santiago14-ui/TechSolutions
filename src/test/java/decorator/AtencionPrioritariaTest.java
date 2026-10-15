package decorator;

import model.Servicio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para el decorador AtencionPrioritaria.
 */
class AtencionPrioritariaTest {

    @Test
    void debeAgregarCostoDeAtencionPrioritaria() {
        Servicio servicio = new Servicio(
                "SER-001",
                "Mantenimiento preventivo",
                "Revisión y mantenimiento preventivo de equipos.",
                80000
        );

        AtencionPrioritaria servicioPrioritario =
                new AtencionPrioritaria(servicio);

        assertEquals(115000, servicioPrioritario.calcularCosto(), 0.001);
    }

    @Test
    void debeAgregarAtencionPrioritariaAlNombre() {
        Servicio servicio = new Servicio(
                "SER-001",
                "Mantenimiento preventivo",
                "Revisión y mantenimiento preventivo de equipos.",
                80000
        );

        AtencionPrioritaria servicioPrioritario =
                new AtencionPrioritaria(servicio);

        assertEquals(
                "Mantenimiento preventivo con atención prioritaria",
                servicioPrioritario.getNombre()
        );
    }

    @Test
    void debeCalcularCorrectamenteElCostoDeUnServicioGratuito() {
        Servicio servicio = new Servicio(
                "SER-000",
                "Servicio gratuito",
                "Servicio de prueba sin costo base.",
                0
        );

        AtencionPrioritaria servicioPrioritario =
                new AtencionPrioritaria(servicio);

        assertEquals(35000, servicioPrioritario.calcularCosto(), 0.001);
    }

    @Test
    void noDebeModificarElCostoOriginalDelServicio() {
        Servicio servicio = new Servicio(
                "SER-001",
                "Mantenimiento preventivo",
                "Revisión y mantenimiento preventivo de equipos.",
                80000
        );

        AtencionPrioritaria servicioPrioritario =
                new AtencionPrioritaria(servicio);

        assertEquals(80000, servicio.calcularCosto(), 0.001);
        assertEquals(115000, servicioPrioritario.calcularCosto(), 0.001);
    }
}