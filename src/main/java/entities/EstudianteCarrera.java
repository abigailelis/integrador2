package entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa la relación entre un estudiante y una carrera.
 * Contiene información sobre la inscripción, graduación y antigüedad del estudiante en la carrera.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteCarrera {

    /**
     * Clave compuesta que identifica la relación entre estudiante y carrera.
     */
    @EmbeddedId
    private EstudianteCarreraKey id;

    @Column
    private int inscripcion;

    @Column
    private int graduacion;

    @Column
    private int antiguedad;

    /**
     * Relación con la entidad Estudiante.
     * Se une por el campo 'DNI' sin permitir inserciones o actualizaciones automáticas.
     */
    @ManyToOne
    @JoinColumn(name = "id_estudiante", referencedColumnName = "DNI", insertable = false, updatable = false)
    private Estudiante estudiante;

    /**
     * Relación con la entidad Carrera.
     * Se une por el campo 'id_carrera' sin permitir inserciones o actualizaciones automáticas.
     */
    @ManyToOne
    @JoinColumn(name = "id_carrera", referencedColumnName = "id_carrera", insertable = false, updatable = false)
    private Carrera carrera;
}
