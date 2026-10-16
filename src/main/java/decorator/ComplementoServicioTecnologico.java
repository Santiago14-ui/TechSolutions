package decorator;

import composite.ElementoServicio;

/**
 * Interfaz que conecta el objeto con los complementos adicionales
 */
public interface ComplementoServicioTecnologico extends ElementoServicio {

    /**
     * Descripcion del complemento a aplicar
     * @return
     */
    String descripcion();

    /**
     * Precio del complemento adicional
     * @return
     */
    double costoAdicional();
}
