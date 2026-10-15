package decorator;

import model.Servicio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para el decorador Soporte30Dias.
 */
class Soporte30DiasTest {

    @Test
    void debeAgregarCostoDeSoporteTecnico() {
        Servicio servicio = new Servicio(
                "SER-001",
                "Mantenimiento preventivo",
                "Revisión y mantenimiento preventivo de equipos.",
                80000
        );

        Soporte30Dias servicioConSoporte = new Soporte30Dias(servicio);

        assertEquals(130000, servicioConSoporte.calcularCosto(), 0.001);
    }

    @Test
    void debeAgregarSoporteTecnicoAlNombre() {
        Servicio servicio = new Servicio(
                "SER-001",
                "Mantenimiento preventivo",
                "Revisión y mantenimiento preventivo de equipos.",
                80000
        );

        Soporte30Dias servicioConSoporte = new Soporte30Dias(servicio);

        assertEquals(
                "Mantenimiento preventivo con soporte técnico por 30 días",
                servicioConSoporte.getNombre()
        );
    }

    @Test
    void debeCalcularElCostoCuandoElServicioBaseEsGratuito() {
        Servicio servicio = new Servicio(
                "SER-000",
                "Servicio gratuito",
                "Servicio de prueba sin costo base.",
                0
        );

        Soporte30Dias servicioConSoporte = new Soporte30Dias(servicio);

        assertEquals(50000, servicioConSoporte.calcularCosto(), 0.001);
    }

    @Test
    void noDebeModificarElCostoOriginalDelServicio() {
        Servicio servicio = new Servicio(
                "SER-001",
                "Mantenimiento preventivo",
                "Revisión y mantenimiento preventivo de equipos.",
                80000
        );

        Soporte30Dias servicioConSoporte = new Soporte30Dias(servicio);

        assertEquals(80000, servicio.calcularCosto(), 0.001);
        assertEquals(130000, servicioConSoporte.calcularCosto(), 0.001);
    }
}