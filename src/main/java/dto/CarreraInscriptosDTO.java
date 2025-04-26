package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class CarreraInscriptosDTO {
    private String carrera;
    private int duracion;
    private long inscriptos;

    @Override
    public String toString() {
        return "Carrera: " + carrera
                + " | duracion: " + duracion
                + " | inscriptos: " + inscriptos;
    }
}
