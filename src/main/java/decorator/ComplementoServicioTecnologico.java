package decorator;

/**
 * Interfaz que conecta el objeto con los complementos adicionales
 */
public interface ComplementoServicioTecnologico {

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

    /**
     * Precio final del complemento mas el objeto inicial
     * @return
     */
    double calcularCosto();
}
