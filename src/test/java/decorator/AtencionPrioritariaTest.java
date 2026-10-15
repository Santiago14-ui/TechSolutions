package decorator;

import model.ServicioTecnologico;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para el decorador AtencionPrioritaria.
 */
class AtencionPrioritariaTest {

    @Test
    void debeAgregarCostoDeAtencionPrioritaria() {
        ServicioTecnologico servicioTecnologico = new ServicioTecnologico(
                "SER-001",
                "Mantenimiento preventivo",
                "Revisión y mantenimiento preventivo de equipos.",
                80000
        );

        AtencionPrioritaria servicioPrioritario =
                new AtencionPrioritaria(servicioTecnologico);

        assertEquals(115000, servicioPrioritario.calcularCosto(), 0.001);
    }

    @Test
    void debeAgregarAtencionPrioritariaAlNombre() {
        ServicioTecnologico servicioTecnologico = new ServicioTecnologico(
                "SER-001",
                "Mantenimiento preventivo",
                "Revisión y mantenimiento preventivo de equipos.",
                80000
        );

        AtencionPrioritaria servicioPrioritario =
                new AtencionPrioritaria(servicioTecnologico);

        assertEquals(
                "Mantenimiento preventivo con atención prioritaria",
                servicioPrioritario.getNombre()
        );
    }

    @Test
    void debeCalcularCorrectamenteElCostoDeUnServicioGratuito() {
        ServicioTecnologico servicioTecnologico = new ServicioTecnologico(
                "SER-000",
                "Servicio gratuito",
                "Servicio de prueba sin costo base.",
                0
        );

        AtencionPrioritaria servicioPrioritario =
                new AtencionPrioritaria(servicioTecnologico);

        assertEquals(35000, servicioPrioritario.calcularCosto(), 0.001);
    }

    @Test
    void noDebeModificarElCostoOriginalDelServicio() {
        ServicioTecnologico servicioTecnologico = new ServicioTecnologico(
                "SER-001",
                "Mantenimiento preventivo",
                "Revisión y mantenimiento preventivo de equipos.",
                80000
        );

        AtencionPrioritaria servicioPrioritario =
                new AtencionPrioritaria(servicioTecnologico);

        assertEquals(80000, servicioTecnologico.calcularCosto(), 0.001);
        assertEquals(115000, servicioPrioritario.calcularCosto(), 0.001);
    }
}