
package model;

/**
 * Representa la información de un técnico consultado.
 */
public class TecnicoInfo {

    private String id;
    private boolean disponible;
    private double costoDesplazamiento;

    public TecnicoInfo(String id, boolean disponible, double costoDesplazamiento) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El ID del técnico es obligatorio.");
        }

        if (costoDesplazamiento < 0) {
            throw new IllegalArgumentException("El costo de desplazamiento no puede ser negativo.");
        }

        this.id = id;
        this.disponible = disponible;
        this.costoDesplazamiento = costoDesplazamiento;
    }

    public String getId() {
        return id;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public double getCostoDesplazamiento() {
        return costoDesplazamiento;
    }
}
