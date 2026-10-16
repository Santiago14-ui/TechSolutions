package service;

import composite.PaqueteServicios;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PaqueteServiceTest {

    private PaqueteService service;

    @BeforeEach
    void configurar() {
        service = new PaqueteService();
    }

    private PaqueteServicios paquete(String codigo) {
        return new PaqueteServicios(
                codigo,
                "Paquete básico",
                "Paquete de mantenimiento."
        );
    }

    @Test
    void registraYBuscaPaquete() {
        PaqueteServicios paquete = paquete("PAQ-001");

        service.registrarPaquete(paquete);

        assertSame(paquete, service.buscarPorCodigo("PAQ-001"));
        assertEquals(1, service.obtenerPaquetes().size());
    }

    @Test
    void rechazaPaqueteNulo() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.registrarPaquete(null)
        );
    }

    @Test
    void rechazaCodigosDuplicadosSinDistinguirMayusculas() {
        service.registrarPaquete(paquete("PAQ-001"));

        assertThrows(
                IllegalArgumentException.class,
                () -> service.registrarPaquete(paquete("paq-001"))
        );
    }

    @Test
    void devuelveNuloSiNoExisteOCodigoVacio() {
        assertNull(service.buscarPorCodigo("PAQ-999"));
        assertNull(service.buscarPorCodigo(null));
        assertNull(service.buscarPorCodigo(" "));
    }

    @Test
    void listaNoEsModificable() {
        service.registrarPaquete(paquete("PAQ-001"));

        List<PaqueteServicios> lista = service.obtenerPaquetes();

        assertThrows(
                UnsupportedOperationException.class,
                lista::clear
        );
    }
}