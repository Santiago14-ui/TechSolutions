
package model;

/**
 * Define las operaciones comunes de los elementos de servicio.
 * Los elementos pueden ser paquetes o instancias indiciduales, que pueden usar builder o composite
 */
public interface ElementoServicio {


    String getNombre();

    String getDescripcion();

    /**
     * Metodo para calcular el costo final de cada elemento
     * @return costo total
     */
    double calcularCosto();

    /**
     * Metodo para mostrar en texto la estructura composite
     * @param identacion espacio para mostrar la estructura de arbol
     */
    void mostrarElemento(String identacion);
}
