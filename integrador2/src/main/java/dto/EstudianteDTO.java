package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO (Data Transfer Object) que representa un estudiante con su dni, nombre, apellido, edad, genero, ciudad y libreta universitaria.
 * Se utiliza para transportar datos desde la base de datos a la capa de presentación.
 */

@Getter
@AllArgsConstructor
public class EstudianteDTO {
    private int DNI;
    private String nombre;
    private String apellido;
    private int edad;
    private String genero;
    private String ciudad;
    private int LU;

    @Override
    public String toString(){
        return "DNI: " + DNI
                + " | Nombre: " + nombre
                + " | apellido: " + apellido
                + " | edad: " + edad
                + " | genero: " + genero
                + " | ciudad: " + ciudad
                + " | LU: " + LU;
    }
}
