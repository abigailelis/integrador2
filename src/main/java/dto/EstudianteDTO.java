package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Data Transfer Object (edu.isistan.db.DTO) for representing a person.
 * This class contains personal details such as name, age, city, address and partner information.
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
        return "\nDNI: " + DNI
                + " | Nombre: " + nombre
                + " | apellido: " + apellido
                + " | edad: " + edad
                + " | genero: " + genero
                + " | ciudad: " + ciudad
                + " | LU: " + LU;
    }
}
