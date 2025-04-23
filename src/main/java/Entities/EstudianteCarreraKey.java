package Entities;

import jakarta.persistence.*;
import java.io.Serializable;

@Embeddable
public class EstudianteCarreraKey implements Serializable {
    private int id;
    private int id_estudiante;
    private int id_carrera;
}
