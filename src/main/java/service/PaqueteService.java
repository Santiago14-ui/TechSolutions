
package service;

import composite.PaqueteServicios;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Gestiona los paquetes de servicios creados en la aplicación.
 * Permite registrar y consultar los paquetes.
 */
public class PaqueteService {

    private final List<PaqueteServicios> paquetes;

    /**
     * Inicializa la lista de paquetes.
     */
    public PaqueteService() {
        paquetes = new ArrayList<>();
    }

    /**
     * Registra un paquete nuevo.
     *
     * @param paquete paquete que se desea registrar
     */
    public void registrarPaquete(PaqueteServicios paquete) {

        if (paquete == null) {
            throw new IllegalArgumentException(
                    "El paquete es obligatorio."
            );
        }

        for (PaqueteServicios existente : paquetes) {
            if (existente.getCodigo().equalsIgnoreCase(
                    paquete.getCodigo())) {

                throw new IllegalArgumentException(
                        "Ya existe un paquete con ese código."
                );
            }
        }

        paquetes.add(paquete);
    }

    /**
     * Obtiene la lista de paquetes registrados.
     *
     * @return lista de paquetes registrados
     */
    public List<PaqueteServicios> obtenerPaquetes() {
        return Collections.unmodifiableList(paquetes);
    }

    /**
     * Busca un paquete por su código.
     *
     * @param codigo código del paquete que se desea buscar
     * @return paquete encontrado o null si no existe
     */
    public PaqueteServicios buscarPorCodigo(String codigo) {

        if (codigo == null || codigo.isBlank()) {
            return null;
        }

        for (PaqueteServicios paquete : paquetes) {
            if (paquete.getCodigo().equalsIgnoreCase(codigo)) {
                return paquete;
            }
        }

        return null;
    }
}
