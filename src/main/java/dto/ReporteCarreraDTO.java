package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
                + " | anio: " + anio
                + " | inscriptos: " + inscriptos
                + " | graduados: " + graduados;
    }
}
