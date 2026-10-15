package decorator;

import model.ServicioTecnologico;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para el decorador AtencionDomicilio.
 */
class AtencionDomicilioTest {

    @Test
    void debeAgregarCostoDeAtencionDomicilio() {
        ServicioTecnologico servicioTecnologico = new ServicioTecnologico(
                "SER-001",
                "Mantenimiento preventivo",
                "Revisión y mantenimiento preventivo de equipos.",
                80000
        );

        AtencionDomicilio servicioADomicilio =
                new AtencionDomicilio(servicioTecnologico);

        assertEquals(105000, servicioADomicilio.calcularCosto(), 0.001);
    }

    @Test
    void debeAgregarAtencionDomicilioAlNombre() {
        ServicioTecnologico servicioTecnologico = new ServicioTecnologico(
                "SER-001",
                "Mantenimiento preventivo",
                "Revisión y mantenimiento preventivo de equipos.",
                80000
        );

        AtencionDomicilio servicioADomicilio =
                new AtencionDomicilio(servicioTecnologico);

        assertEquals(
                "Mantenimiento preventivo con atención a domicilio",
                servicioADomicilio.getNombre()
        );
    }

    @Test
    void debeCalcularElCostoCuandoElServicioBaseEsGratuito() {
        ServicioTecnologico servicioTecnologico = new ServicioTecnologico(
                "SER-000",
                "Servicio gratuito",
                "Servicio de prueba sin costo base.",
                0
        );

        AtencionDomicilio servicioADomicilio =
                new AtencionDomicilio(servicioTecnologico);

        assertEquals(25000, servicioADomicilio.calcularCosto(), 0.001);
    }

    @Test
    void noDebeModificarElCostoOriginalDelServicio() {
        ServicioTecnologico servicioTecnologico = new ServicioTecnologico(
                "SER-001",
                "Mantenimiento preventivo",
                "Revisión y mantenimiento preventivo de equipos.",
                80000
        );

        AtencionDomicilio servicioADomicilio =
                new AtencionDomicilio(servicioTecnologico);

        assertEquals(80000, servicioTecnologico.calcularCosto(), 0.001);
        assertEquals(105000, servicioADomicilio.calcularCosto(), 0.001);
    }
}