package decorator;

import factory.MantenimientoPreventivoServicioTecnologicoFactory;
import model.ServicioTecnologico;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Soporte30DiasTest {

    @Test
    void agregaCostoDelComplemento() {
        ServicioTecnologico servicio =
                new MantenimientoPreventivoServicioTecnologicoFactory()
                        .crearServicioTecnologico("SER-001", "Revisión preventiva");

        Soporte30Dias decorado = new Soporte30Dias(servicio);

        assertEquals(130000, decorado.calcularCosto(), 0.001);
        assertTrue(decorado.getNombre().contains("Soporte por 30 días"));
        assertEquals(80000, servicio.calcularCosto(), 0.001);
    }
}