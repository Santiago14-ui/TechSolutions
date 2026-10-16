
package controller;

import adapter.TecnicoInfo;
import composite.ElementoServicio;
import model.Cliente;
import model.Servicio;

import java.util.List;

public class ServicioController {

    public Servicio crearServicio(
            Cliente cliente,
            List<ElementoServicio> elementosContratados,
            TecnicoInfo tecnico) {

        return new Servicio(
                cliente,
                elementosContratados,
                tecnico
        );
    }

    public double calcularCostoFinal(Servicio servicio) {
        if (servicio == null) {
            throw new IllegalArgumentException(
                    "El servicio es obligatorio."
            );
        }

        return servicio.getCostoFinal();
    }

    public void obtenerResumen(Servicio servicio) {
        if (servicio == null) {
            throw new IllegalArgumentException(
                    "El servicio es obligatorio."
            );
        }
        servicio.mostrarResumen();
    }
}
