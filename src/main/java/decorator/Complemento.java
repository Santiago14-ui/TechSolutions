package decorator;

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
    public String descripcion() {
        return servicio.descripcion()
                + " + "
                + descripcionComplemento();
    }

    @Override
    public double costoAdicional() {
        return servicio.costoAdicional() + costoPropio();
    }

    @Override
    public double calcularCosto() {
        return servicio.calcularCosto() + costoPropio();
    }
}
