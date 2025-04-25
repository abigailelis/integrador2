package Repository;

import DTO.EstudianteDTO;
import Entities.Estudiante;
import javax.persistence.EntityManager;
import java.io.IOException;
import java.io.FileReader;
import java.util.List;
import Factory.JPAUtil;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class EstudianteRepository {

    private EntityManager em;

    public EstudianteRepository(){
        this.em = JPAUtil.getEntityManager();
    }
    /**
     * Reads a CSV file and inserts new student records into the database.
     * Each row in the CSV should contain student details
     *
     * @param urlFile the file path or URL of the CSV file to be processed.
     * @throws IOException if an error occurs while reading the CSV file.
     * @throws CsvValidationException if the CSV format is invalid or data validation fails.
     */
    public void insertarEstudianteCSV(String urlFile) throws IOException, CsvValidationException {
        CSVReader reader = new CSVReader(new FileReader(urlFile));
        String[] line;
        reader.readNext();

        em.getTransaction().begin();

        while((line = reader.readNext()) != null){
            Estudiante estudiante = new Estudiante();

            estudiante.setDNI(Integer.parseInt(line[0]));
            estudiante.setNombre(line[1]);
            estudiante.setApellido(line[2]);
            estudiante.setEdad(Integer.parseInt(line[3]));
            estudiante.setGenero(line[4]);
            estudiante.setCiudad(line[5]);
            estudiante.setLU(Integer.parseInt(line[6]));

            em.persist(estudiante);
        }

        em.getTransaction().commit();
        System.out.print("\nEstudiante agregado exitosamente");
        em.close();
    }

    public void insertarEstudiante(Estudiante estudiante){
        em.getTransaction().begin();
        em.persist(estudiante);
        em.getTransaction().commit();
        System.out.println("Estudiante agregado correctamente");
        em.close();
    }

    public List<EstudianteDTO> buscarEstudiantesGenero(String genero){
        List<EstudianteDTO> estudiantes = em.createQuery(
                        "SELECT new DTO.EstudianteDTO(e.DNI, e.nombre, e.apellido, e.edad, e.genero, e.ciudad, e.LU) " +
                                "FROM Estudiante e " +
                                "WHERE e.genero = :genero",
                        EstudianteDTO.class)
                .setParameter("genero", genero)
                .getResultList();
        return estudiantes;
    }

    public List<EstudianteDTO> buscarEstudiantesLU(int LU){
        List<EstudianteDTO> estudiantes = em.createQuery(
                        "SELECT new DTO.EstudianteDTO(e.DNI, e.nombre, e.apellido, e.edad, e.genero, e.ciudad, e.LU) " +
                                "FROM Estudiante e " +
                                "WHERE e.LU = :LU",
                        EstudianteDTO.class)
                .setParameter("LU", LU)
                .getResultList();
        return estudiantes;
    }

    public List<EstudianteDTO> buscarEstudiantesApellido(String apellido){
        List<EstudianteDTO> estudiantes = em.createQuery(
                        "SELECT new DTO.EstudianteDTO(e.DNI, e.nombre, e.apellido, e.edad, e.genero, e.ciudad, e.LU) " +
                                "FROM Estudiante e " +
                                "WHERE e.apellido = :apellido",
                        EstudianteDTO.class)
                .setParameter("apellido", apellido)
                .getResultList();
        return estudiantes;
    }

    public List<EstudianteDTO> buscarEstudiantes(){
        List<EstudianteDTO> estudiantes = em.createQuery("SELECT e FROM Estudiante e", EstudianteDTO.class).getResultList();

        return estudiantes;
    }

}
