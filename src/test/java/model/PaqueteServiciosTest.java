package model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase PaqueteServicios.
 */
class PaqueteServiciosTest {

    @Test
    void debeCrearPaqueteConDatosCorrectos() {
        PaqueteServicios paquete = new PaqueteServicios(
                "PAQ-001",
                "Paquete básico",
                "Paquete de mantenimiento básico."
        );

        assertEquals("PAQ-001", paquete.getCodigo());
        assertEquals("Paquete básico", paquete.getNombre());
        assertEquals(
                "Paquete de mantenimiento básico.",
                paquete.getDescripcion()
        );
        assertTrue(paquete.getElementos().isEmpty());
    }

    @Test
    void debeAgregarServicioAlPaquete() {
        PaqueteServicios paquete = crearPaquete();
        Servicio servicio = crearServicio("SER-001", 80000);

        paquete.agregarElemento(servicio);

        assertEquals(1, paquete.getElementos().size());
        assertTrue(paquete.getElementos().contains(servicio));
    }

    @Test
    void debeCalcularCostoDeVariosServicios() {
        PaqueteServicios paquete = crearPaquete();

        paquete.agregarElemento(crearServicio("SER-001", 80000));
        paquete.agregarElemento(crearServicio("SER-002", 120000));

        assertEquals(200000, paquete.calcularCosto(), 0.001);
    }

    @Test
    void debeCalcularCostoDePaquetesAnidados() {
        PaqueteServicios paquetePrincipal = crearPaquete();
        PaqueteServicios subpaquete = new PaqueteServicios(
                "PAQ-002",
                "Subpaquete",
                "Paquete secundario."
        );

        subpaquete.agregarElemento(crearServicio("SER-001", 80000));
        subpaquete.agregarElemento(crearServicio("SER-002", 120000));

        paquetePrincipal.agregarElemento(subpaquete);
        paquetePrincipal.agregarElemento(
                crearServicio("SER-003", 180000)
        );

        assertEquals(380000, paquetePrincipal.calcularCosto(), 0.001);
    }

    @Test
    void debeRechazarElementoNulo() {
        PaqueteServicios paquete = crearPaquete();

        assertThrows(
                IllegalArgumentException.class,
                () -> paquete.agregarElemento(null)
        );
    }

    @Test
    void debeRechazarIncluirseASiMismo() {
        PaqueteServicios paquete = crearPaquete();

        assertThrows(
                IllegalArgumentException.class,
                () -> paquete.agregarElemento(paquete)
        );
    }

    @Test
    void debeRechazarDependenciaCircularEntrePaquetes() {
        PaqueteServicios paqueteA = crearPaquete();

        PaqueteServicios paqueteB = new PaqueteServicios(
                "PAQ-002",
                "Paquete B",
                "Segundo paquete."
        );

        paqueteA.agregarElemento(paqueteB);

        assertThrows(
                IllegalArgumentException.class,
                () -> paqueteB.agregarElemento(paqueteA)
        );
    }

    @Test
    void debeImpedirModificarLaListaDeElementos() {
        PaqueteServicios paquete = crearPaquete();
        paquete.agregarElemento(crearServicio("SER-001", 80000));

        List<ElementoServicio> elementos = paquete.getElementos();

        assertThrows(
                UnsupportedOperationException.class,
                () -> elementos.clear()
        );

        assertEquals(1, paquete.getElementos().size());
    }

    @Test
    void debeRechazarCodigoVacio() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new PaqueteServicios(
                        " ",
                        "Paquete básico",
                        "Descripción del paquete."
                )
        );
    }

    private PaqueteServicios crearPaquete() {
        return new PaqueteServicios(
                "PAQ-001",
                "Paquete básico",
                "Paquete de mantenimiento básico."
        );
    }

    private Servicio crearServicio(String codigo, double costo) {
        return new Servicio(
                codigo,
                "Servicio de prueba",
                "Descripción del servicio de prueba.",
                costo
        );
    }
}