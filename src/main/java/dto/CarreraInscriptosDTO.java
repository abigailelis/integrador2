package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO (Data Transfer Object) que representa una carrera con su duración y cantidad de inscriptos.
 * Se utiliza para transportar datos desde la base de datos a la capa de presentación.
 */
@Getter
@AllArgsConstructor

public class CarreraInscriptosDTO {
    private String carrera;
    private int duracion;
    private long inscriptos;

    @Override
    public String toString() {
        return "Carrera: " + carrera
                + " | duración: " + duracion
                + " | inscriptos: " + inscriptos;
    }
}
