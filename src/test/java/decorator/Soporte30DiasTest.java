package decorator;

import model.ServicioTecnologico;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para el decorador Soporte30Dias.
 */
class Soporte30DiasTest {

    @Test
    void debeAgregarCostoDeSoporteTecnico() {
        ServicioTecnologico servicioTecnologico = new ServicioTecnologico(
                "SER-001",
                "Mantenimiento preventivo",
                "Revisión y mantenimiento preventivo de equipos.",
                80000
        );

        Soporte30Dias servicioConSoporte = new Soporte30Dias(servicioTecnologico);

        assertEquals(130000, servicioConSoporte.calcularCosto(), 0.001);
    }

    @Test
    void debeAgregarSoporteTecnicoAlNombre() {
        ServicioTecnologico servicioTecnologico = new ServicioTecnologico(
                "SER-001",
                "Mantenimiento preventivo",
                "Revisión y mantenimiento preventivo de equipos.",
                80000
        );

        Soporte30Dias servicioConSoporte = new Soporte30Dias(servicioTecnologico);

        assertEquals(
                "Mantenimiento preventivo con soporte técnico por 30 días",
                servicioConSoporte.getNombre()
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

        Soporte30Dias servicioConSoporte = new Soporte30Dias(servicioTecnologico);

        assertEquals(50000, servicioConSoporte.calcularCosto(), 0.001);
    }

    @Test
    void noDebeModificarElCostoOriginalDelServicio() {
        ServicioTecnologico servicioTecnologico = new ServicioTecnologico(
                "SER-001",
                "Mantenimiento preventivo",
                "Revisión y mantenimiento preventivo de equipos.",
                80000
        );

        Soporte30Dias servicioConSoporte = new Soporte30Dias(servicioTecnologico);

        assertEquals(80000, servicioTecnologico.calcularCosto(), 0.001);
        assertEquals(130000, servicioConSoporte.calcularCosto(), 0.001);
    }
}