package DTO;
import lombok.*;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CarreraDTO {
    private int id_carrera;
    private String carrera;
    private int duracion;

    @Override
    public String toString(){
        return "\nCarrera: " + carrera
                + "Duracion: " + duracion;
    }
}
