package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO (Data Transfer Object) que representa un reporte de una carrera universitaria,
 * con información sobre el año, cantidad de inscriptos y graduados.
 * Se utiliza para transportar datos desde la base de datos a la capa de presentación.
 */

@Getter
@AllArgsConstructor
public class ReporteCarreraDTO {
    private String carrera;
    private int anio;
    private long inscriptos;
    private long graduados;

    @Override
    public String toString(){
        return "Carrera: " + carrera
                + " | año: " + anio
                + " | inscriptos: " + inscriptos
                + " | graduados: " + graduados;
    }
}
