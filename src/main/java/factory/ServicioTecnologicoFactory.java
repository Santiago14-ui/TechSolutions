package factory;

import model.ServicioTecnologico;

/**
 * Interfaz para crear los diferentes servicios tecnologicos
 */
public interface ServicioTecnologicoFactory {

    /**
     * Metodo para crear cualquier servicio tecnologico
     * @return un nuevo tipo de servicio tecnologico
     */
    ServicioTecnologico crearServicioTecnologico(String codigo, String descripcion);
}
