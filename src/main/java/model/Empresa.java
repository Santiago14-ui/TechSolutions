package model;

import composite.ElementoServicio;

import java.util.ArrayList;

/**
 * Clase principal de la empresa, la cual contiene toda la informacion
 * Como solo es una empresa, se usa singleton
 */
public class Empresa {
    //Instancia unica de la clase
    private static Empresa instancia;

    private String nombreComercial;
    private String nit;
    private String correoElectronico;
    private ArrayList<Cliente> listClientes;
    private ArrayList<ElementoServicio> listServiciosTecnologicos;
    private ArrayList<Servicio>  listServicios;


    //Constructor
    private Empresa(String nombreComercial, String nit, String correoElectronico) {
        this.nombreComercial = nombreComercial;
        this.nit = nit;
        this.correoElectronico = correoElectronico;
        listClientes = new ArrayList<>();
        listServiciosTecnologicos = new ArrayList<>();
        listServicios = new ArrayList<>();
    }

    /**
     * Metodo para crear una unica instancia de la clase
     * @return instancia unica de la clase
     */
    public static Empresa getInstancia() {
        if (instancia == null) {
            instancia = new Empresa("TechSolutions", "123456", "techSolutions@yahoo.com");
        }
        return instancia;
    }


    //Getters y setters
    public static void setInstancia(Empresa instancia) {
        Empresa.instancia = instancia;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public ArrayList<Cliente> getListClientes() {
        return listClientes;
    }

    public void setListClientes(ArrayList<Cliente> listClientes) {
        this.listClientes = listClientes;
    }

    public ArrayList<ElementoServicio> getListServiciosTecnologicos() {
        return listServiciosTecnologicos;
    }

    public void setListServiciosTecnologicos(ArrayList<ElementoServicio> listServiciosTecnologicos) {
        this.listServiciosTecnologicos = listServiciosTecnologicos;
    }

    public ArrayList<Servicio> getListServicios() {
        return listServicios;
    }

    public void setListServicios(ArrayList<Servicio> listServicios) {
        this.listServicios = listServicios;
    }
}
