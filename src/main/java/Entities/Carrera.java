package Entities;

import jakarta.persistence.*;
import lombok.*;

import javax.management.ConstructorParameters;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Carrera {
    @Id
    private int DNI;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column
    private int edad;

    @Column
    private String genero;

    @Column
    private String ciudad;

    @Column
    private int LU;

    @OneToMany(mappedBy = "carrera")
    private List<EstudianteCarrera> estudiantes;

}
