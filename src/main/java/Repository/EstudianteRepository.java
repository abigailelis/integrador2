package Repository;

import DTO.EstudianteDTO;
import Entities.Estudiante;
import javax.persistence.EntityManager;
import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;
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
        List<Estudiante> estudiantes = em.createQuery(
                        "SELECT new DTO.EstudianteDTO(e.DNI, e.nombre, e.apellido, e.edad, e.genero, e.ciudad, e.LU) " +
                                "FROM Estudiante e " +
                                "WHERE e.genero = :genero",
                        Estudiante.class)
                .setParameter("genero", genero)
                .getResultList();
        return crearDtos(estudiantes);
    }

    public List<EstudianteDTO> buscarEstudiantesLU(int LU){
        List<Estudiante> estudiantes = em.createQuery(
                        "SELECT new DTO.EstudianteDTO(e.DNI, e.nombre, e.apellido, e.edad, e.genero, e.ciudad, e.LU) " +
                                "FROM Estudiante e " +
                                "WHERE e.LU = :LU",
                        Estudiante.class)
                .setParameter("LU", LU)
                .getResultList();
        return crearDtos(estudiantes);
    }

    public List<EstudianteDTO> buscarEstudiantesApellido(){
        List<Estudiante> estudiantes = em.createQuery(
                        "SELECT new DTO.EstudianteDTO(e.DNI, e.nombre, e.apellido, e.edad, e.genero, e.ciudad, e.LU) " +
                                "FROM Estudiante e " +
                                "ORDER BY e.apellido",
                        Estudiante.class)
                .getResultList();
        return crearDtos(estudiantes);
    }

    public List<EstudianteDTO> buscarEstudiantesApellido(String carrera, String ciudad){
        List<Estudiante> estudiantes = em.createQuery(
                        "SELECT new DTO.EstudianteDTO(e.DNI, e.nombre, e.apellido, e.edad, e.genero, e.ciudad, e.LU) " +
                                "FROM Estudiante e " +
                                "JOIN EstudianteCarrera ec " +
                                "WHERE carrera = :carrera AND ciudad = :ciudad",
                        Estudiante.class)
                .setParameter("carrera", carrera)
                .setParameter("ciudad", ciudad)
                .getResultList();

        return crearDtos(estudiantes);
    }

    public List<EstudianteDTO> buscarEstudiantes(){
        List<EstudianteDTO> estudiantes = em.createQuery("SELECT e FROM Estudiante e", EstudianteDTO.class).getResultList();
        return estudiantes;
    }

    public  List<EstudianteDTO> crearDtos(List<Estudiante> estudiantes){
        List<EstudianteDTO> estudiantesDTO = new ArrayList<>();
        for (Estudiante e: estudiantes){
            EstudianteDTO estudianteDTO = new EstudianteDTO(e.DNI, e.nombre, e.apellido, e.edad, e.genero, e.ciudad, e.LU);
            estudiantesDTO.add(estudianteDTO);
        }
        return estudiantesDTO;
    }
}
