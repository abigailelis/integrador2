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

    @Column
    private int inscripcion;

    @Column
    private int graduacion;

    @Column
    private int antiguedad;

    @ManyToOne
    @JoinColumn(name = "id_estudiante", referencedColumnName = "DNI", insertable = false, updatable = false)
    private Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name = "id_carrera", referencedColumnName = "id_carrera", insertable = false, updatable = false)
    private Carrera carrera;
}
