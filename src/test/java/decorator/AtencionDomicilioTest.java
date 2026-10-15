package decorator;

import model.Servicio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para el decorador AtencionDomicilio.
 */
class AtencionDomicilioTest {

    @Test
    void debeAgregarCostoDeAtencionDomicilio() {
        Servicio servicio = new Servicio(
                "SER-001",
                "Mantenimiento preventivo",
                "Revisión y mantenimiento preventivo de equipos.",
                80000
        );

        AtencionDomicilio servicioADomicilio =
                new AtencionDomicilio(servicio);

        assertEquals(105000, servicioADomicilio.calcularCosto(), 0.001);
    }

    @Test
    void debeAgregarAtencionDomicilioAlNombre() {
        Servicio servicio = new Servicio(
                "SER-001",
                "Mantenimiento preventivo",
                "Revisión y mantenimiento preventivo de equipos.",
                80000
        );

        AtencionDomicilio servicioADomicilio =
                new AtencionDomicilio(servicio);

        assertEquals(
                "Mantenimiento preventivo con atención a domicilio",
                servicioADomicilio.getNombre()
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

        AtencionDomicilio servicioADomicilio =
                new AtencionDomicilio(servicio);

        assertEquals(25000, servicioADomicilio.calcularCosto(), 0.001);
    }

    @Test
    void noDebeModificarElCostoOriginalDelServicio() {
        Servicio servicio = new Servicio(
                "SER-001",
                "Mantenimiento preventivo",
                "Revisión y mantenimiento preventivo de equipos.",
                80000
        );

        AtencionDomicilio servicioADomicilio =
                new AtencionDomicilio(servicio);

        assertEquals(80000, servicio.calcularCosto(), 0.001);
        assertEquals(105000, servicioADomicilio.calcularCosto(), 0.001);
    }
}