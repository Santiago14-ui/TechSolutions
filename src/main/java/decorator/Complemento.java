package decorator;

import composite.ElementoServicio;

/**
 * Clase padre que recibe el objeto a modificar
 */
public abstract class Complemento implements ComplementoServicioTecnologico {

    protected final ComplementoServicioTecnologico servicio;

    //Constructor
    public Complemento(ComplementoServicioTecnologico servicio) {
        this.servicio = servicio;
    }

    protected abstract String descripcionComplemento();

    protected abstract double costoPropio();

    @Override
    public String getNombre() {return servicio.getNombre() + " + " + descripcionComplemento();}

    @Override
    public String getDescripcion() {return servicio.getDescripcion() + " | " + descripcionComplemento();}

    @Override
    public String descripcion() {return servicio.descripcion() + " + " + descripcionComplemento();}

    @Override
    public double costoAdicional() {return servicio.costoAdicional() + costoPropio();}

    @Override
    public double calcularCosto() {return servicio.calcularCosto() + costoPropio();}

    @Override
    public void mostrarElemento(String identacion) {
        servicio.mostrarElemento(identacion);
        System.out.println(identacion
                        + "  + "
                        + descripcionComplemento()
                        + " ($"
                        + costoPropio()
                        + ")");}
}
