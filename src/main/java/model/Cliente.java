
package model;

/**
 * Representa un cliente de TechSolutions.
 */
public class Cliente {

    private String documento;
    private String nombre;
    private String correo;

    public Cliente(String documento, String nombre, String correo) {
        if (documento == null || documento.isBlank()) {
            throw new IllegalArgumentException("El documento es obligatorio.");
        }

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }

        if (correo == null || correo.isBlank()) {
            throw new IllegalArgumentException("El correo es obligatorio.");
        }

        this.documento = documento;
        this.nombre = nombre;
        this.correo = correo;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }
}
