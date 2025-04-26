package entities;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class EstudianteCarreraKey implements Serializable {
    private int id_estudiante;
    private int id_carrera;
}
