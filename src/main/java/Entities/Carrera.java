package Entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Carrera {

    @Id
    private int id_carrera;

    @Column
    private String carrera;

    @Column
    private int duracion;

    @OneToMany(mappedBy = "estudiante")
    private List<EstudianteCarrera> carreras;
}
