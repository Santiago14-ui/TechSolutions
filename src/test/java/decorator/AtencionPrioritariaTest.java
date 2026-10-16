package decorator;

import factory.MantenimientoPreventivoServicioTecnologicoFactory;
import model.ServicioTecnologico;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AtencionPrioritariaTest {

    @Test
    void agregaCostoDelComplemento() {
        ServicioTecnologico servicio =
                new MantenimientoPreventivoServicioTecnologicoFactory()
                        .crearServicioTecnologico("SER-001", "Revisión preventiva");

        AtencionPrioritaria decorado = new AtencionPrioritaria(servicio);

        assertEquals(115000, decorado.calcularCosto(), 0.001);
        assertTrue(decorado.getNombre().contains("Atención prioritaria"));
        assertEquals(80000, servicio.calcularCosto(), 0.001);
    }
}