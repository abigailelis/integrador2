package entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

/**
 * Representa una carrera universitaria con información sobre el nombre, duración y los estudiantes inscritos.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Carrera {
    @Id
    private int id_carrera;

    @Column
    private String carrera;

    @Column
    private int duracion;

    /**
     * Lista de estudiantes inscritos en la carrera.
     */
    @OneToMany (mappedBy = "carrera")
    private List<EstudianteCarrera> estudiantes;

    public Carrera(int id_carrera, String carrera, int duracion) {
        this.id_carrera = id_carrera;
        this.carrera = carrera;
        this.duracion = duracion;
    }
}
