
package controller;

import composite.ElementoServicio;
import composite.PaqueteServicios;
import service.PaqueteService;

import java.util.List;

/**
 * Controla las operaciones relacionadas con los paquetes de servicios.
 * Permite crearlos, registrar paquetes, agregar elementos y calcular costos.
 */
public class ControladorPaquetes {

    private final PaqueteService paqueteService;

    /**
     * Crea un controlador con un servicio de paquetes nuevo.
     */
    public ControladorPaquetes() {
        this(new PaqueteService());
    }

    /**
     * Crea un controlador utilizando un servicio de paquetes existente.
     *
     * @param paqueteService servicio que gestionará los paquetes
     */
    public ControladorPaquetes(PaqueteService paqueteService) {
        if (paqueteService == null) {
            throw new IllegalArgumentException(
                    "El servicio de paquetes es obligatorio."
            );
        }

        this.paqueteService = paqueteService;
    }

    /**
     * Crea un paquete después de validar sus datos.
     *
     * @param codigo código del paquete
     * @param nombre nombre del paquete
     * @param descripcion descripción del paquete
     * @return paquete creado
     */
    public PaqueteServicios crearPaquete(
            String codigo,
            String nombre,
            String descripcion) {

        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException(
                    "El código del paquete es obligatorio."
            );
        }

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre del paquete es obligatorio."
            );
        }

        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException(
                    "La descripción del paquete es obligatoria."
            );
        }

        return new PaqueteServicios(
                codigo,
                nombre,
                descripcion
        );
    }

    /**
     * Registra un paquete en el servicio.
     *
     * @param paquete paquete que se desea registrar
     */
    public void registrarPaquete(PaqueteServicios paquete) {
        paqueteService.registrarPaquete(paquete);
    }

    /**
     * Agrega un servicio o paquete a otro paquete.
     *
     * @param paquete paquete que recibirá el elemento
     * @param elemento servicio o paquete que se agregará
     */
    public void agregarElemento(
            PaqueteServicios paquete,
            ElementoServicio elemento) {

        if (paquete == null) {
            throw new IllegalArgumentException(
                    "El paquete es obligatorio."
            );
        }

        if (elemento == null) {
            throw new IllegalArgumentException(
                    "El elemento que se agregará es obligatorio."
            );
        }

        paquete.agregarElemento(elemento);
    }

    /**
     * Calcula el costo total de un paquete.
     *
     * @param paquete paquete cuyo costo se calculará
     * @return costo total del paquete
     */
    public double calcularCosto(PaqueteServicios paquete) {

        if (paquete == null) {
            throw new IllegalArgumentException(
                    "El paquete es obligatorio."
            );
        }

        return paquete.calcularCosto();
    }

    /**
     * Obtiene los paquetes registrados.
     *
     * @return lista de paquetes registrados
     */
    public List<PaqueteServicios> obtenerPaquetes() {
        return paqueteService.obtenerPaquetes();
    }

    /**
     * Busca un paquete por su código.
     *
     * @param codigo código del paquete que se desea buscar
     * @return paquete encontrado o null si no existe
     */
    public PaqueteServicios buscarPorCodigo(String codigo) {
        return paqueteService.buscarPorCodigo(codigo);
    }
}
