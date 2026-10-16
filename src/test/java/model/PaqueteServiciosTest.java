package model;

import composite.ElementoServicio;
import composite.PaqueteServicios;
import factory.MantenimientoPreventivoServicioTecnologicoFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PaqueteServiciosTest {

    private PaqueteServicios paquete() {
        return new PaqueteServicios(
                "PAQ-001",
                "Paquete básico",
                "Paquete de mantenimiento."
        );
    }

    @Test
    void creaPaqueteConDatosCorrectos() {
        PaqueteServicios paquete = paquete();

        assertEquals("PAQ-001", paquete.getCodigo());
        assertTrue(paquete.getElementos().isEmpty());
    }

    @Test
    void agregaServicioYCalculaCosto() {
        PaqueteServicios paquete = paquete();

        paquete.agregarElemento(
                new MantenimientoPreventivoServicioTecnologicoFactory()
                        .crearServicioTecnologico("SER-001", "Revisión")
        );

        assertEquals(80000, paquete.calcularCosto(), 0.001);
    }

    @Test
    void calculaCostoDePaquetesAnidados() {
        PaqueteServicios principal = paquete();

        PaqueteServicios subpaquete = new PaqueteServicios(
                "PAQ-002",
                "Subpaquete",
                "Descripción"
        );

        subpaquete.agregarElemento(
                new MantenimientoPreventivoServicioTecnologicoFactory()
                        .crearServicioTecnologico("SER-001", "Revisión")
        );

        principal.agregarElemento(subpaquete);

        assertEquals(80000, principal.calcularCosto(), 0.001);
    }

    @Test
    void rechazaElementoNulo() {
        assertThrows(
                IllegalArgumentException.class,
                () -> paquete().agregarElemento(null)
        );
    }

    @Test
    void rechazaIncluirseASiMismo() {
        PaqueteServicios paquete = paquete();

        assertThrows(
                IllegalArgumentException.class,
                () -> paquete.agregarElemento(paquete)
        );
    }

    @Test
    void rechazaDependenciaCircular() {
        PaqueteServicios a = paquete();

        PaqueteServicios b = new PaqueteServicios(
                "PAQ-002",
                "B",
                "B"
        );

        a.agregarElemento(b);

        assertThrows(
                IllegalArgumentException.class,
                () -> b.agregarElemento(a)
        );
    }

    @Test
    void listaNoEsModificable() {
        PaqueteServicios paquete = paquete();

        paquete.agregarElemento(
                new MantenimientoPreventivoServicioTecnologicoFactory()
                        .crearServicioTecnologico("SER-001", "Revisión")
        );

        List<ElementoServicio> lista = paquete.getElementos();

        assertThrows(
                UnsupportedOperationException.class,
                lista::clear
        );
    }
}