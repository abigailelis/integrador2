package DTO;

import lombok.*;

/**
 * Data Transfer Object (DTO) for representing a person.
 * This class contains personal details such as name, age, city, address and partner information.
 */

@Getter
@NoArgsConstructor
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
                + "Nombre: " + nombre
                + " | apellido: " + apellido
                + " | edad: " + edad
                + " | genero: " + genero
                + " | ciudad: " + ciudad
                + " | LU: " + LU;
    }
}
