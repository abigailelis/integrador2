package entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Estudiante {
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

    @OneToMany
    private List<EstudianteCarrera> carreras;


    public Estudiante(int DNI,String nombre, String apellido,int edad, String genero, String ciudad, int LU) {
        this.DNI = DNI;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.genero = genero;
        this.ciudad = ciudad;
        this.LU = LU;
    }
}
