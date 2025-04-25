package Entities;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class EstudianteCarreraKey implements Serializable {
    private int id_estudiante;
    private int id_carrera;
}
