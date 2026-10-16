
package model;

import adapter.TecnicoInfo;
import composite.ElementoServicio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa el servicio final contratado por un cliente.
 * Relaciona los elementos contratados, el técnico asignado
 * y el costo final.
 */
public class Servicio {

    private final Cliente cliente;
    private final List<ElementoServicio> elementosContratados;
    private final TecnicoInfo tecnico;
    private final double costoFinal;

    public Servicio(
            Cliente cliente,
            List<ElementoServicio> elementosContratados,
            TecnicoInfo tecnico) {

        if (cliente == null) {
            throw new IllegalArgumentException(
                    "El cliente es obligatorio."
            );
        }

        if (elementosContratados == null
                || elementosContratados.isEmpty()) {
            throw new IllegalArgumentException(
                    "Debe contratarse al menos un elemento."
            );
        }

        if (tecnico == null) {
            throw new IllegalArgumentException(
                    "El técnico es obligatorio."
            );
        }

        if (!tecnico.isDisponible()) {
            throw new IllegalArgumentException(
                    "El técnico seleccionado no está disponible."
            );
        }

        this.cliente = cliente;
        this.elementosContratados =
                Collections.unmodifiableList(
                        new ArrayList<>(elementosContratados)
                );
        this.tecnico = tecnico;
        this.costoFinal = calcularCostoFinal();
    }

    /**
     * Suma el costo de los elementos contratados
     * y el desplazamiento del técnico una sola vez.
     */
    private double calcularCostoFinal() {
        double total = 0;

        for (ElementoServicio elemento : elementosContratados) {
            total += elemento.calcularCosto();
        }

        total += tecnico.getCostoDesplazamiento();

        return total;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<ElementoServicio> getElementosContratados() {
        return elementosContratados;
    }

    public TecnicoInfo getTecnico() {
        return tecnico;
    }

    public double getCostoFinal() {
        return costoFinal;
    }

    /**
     * Muestra un resumen del servicio contratado.
     */
    public void mostrarResumen() {
        System.out.println("===== SERVICIO CONTRATADO =====");
        System.out.println("Cliente: " + cliente.getNombre());
        System.out.println("Documento: " + cliente.getDocumento());

        System.out.println("\nElementos contratados:");

        for (ElementoServicio elemento : elementosContratados) {
            elemento.mostrarElemento("  ");
        }

        System.out.println("\nTécnico: " + tecnico.getId());
        System.out.println(
                "Desplazamiento: $"
                        + tecnico.getCostoDesplazamiento()
        );
        System.out.println("Costo final: $" + costoFinal);
    }
}
