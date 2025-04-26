package entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class EstudianteCarrera {

    @EmbeddedId
    private EstudianteCarreraKey id;

    @ManyToOne
    @JoinColumn(name = "estudiante")
    private Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name = "carrera")
    private Carrera carrera;

    @Column
    private int inscripcion;

    @Column
    private int graduacion;

    @Column
    private int antiguedad;
}
