package decorator;

import factory.MantenimientoPreventivoServicioTecnologicoFactory;
import model.ServicioTecnologico;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AtencionDomicilioTest {

    @Test
    void agregaCostoDelComplemento() {
        ServicioTecnologico servicio =
                new MantenimientoPreventivoServicioTecnologicoFactory()
                        .crearServicioTecnologico("SER-001", "Revisión preventiva");

        AtencionDomicilio decorado = new AtencionDomicilio(servicio);

        assertEquals(105000, decorado.calcularCosto(), 0.001);
        assertTrue(decorado.getNombre().contains("Atención a domicilio"));
        assertEquals(80000, servicio.calcularCosto(), 0.001);
    }
}