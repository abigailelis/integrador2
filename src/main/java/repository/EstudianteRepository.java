package repository;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import dto.EstudianteDTO;
import entities.Estudiante;
import factory.JPAUtil;

import jakarta.persistence.EntityManager;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para gestionar entidades de Estudiante en la base de datos.
 */

public class EstudianteRepository {

    /**
     * Inserta estudiantes desde un archivo CSV en la base de datos.
     * @param urlFile Ruta o URL del archivo CSV a procesar.
     * @throws IOException Si ocurre un error al leer el archivo.
     * @throws CsvValidationException Si el formato del CSV es inválido.
     */
    public void insertarEstudianteCSV(String urlFile) throws IOException, CsvValidationException {
        EntityManager em = JPAUtil.getEntityManager();
        String root = "src\\main\\resources\\csv\\" + urlFile;
        CSVReader reader = new CSVReader(new FileReader(root));
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

    /**
     * Inserta un nuevo estudiante en la base de datos.
     * @param estudiante Estudiante a insertar.
     */
    public void insertarEstudiante(Estudiante estudiante){
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(estudiante);
        em.getTransaction().commit();
        System.out.println("Estudiante agregado correctamente");
        em.close();
    }

    /**
     * Filtra estudiantes por género.
     * @param genero Género de los estudiantes a buscar.
     * @return Lista de estudiantes filtrados.
     */
    public List<EstudianteDTO> buscarEstudiantesGenero(String genero){
        EntityManager em = JPAUtil.getEntityManager();
        List<Estudiante> estudiantes = em.createQuery(
                        "SELECT e FROM Estudiante e WHERE e.genero = :genero",
                        Estudiante.class)
                .setParameter("genero", genero)
                .getResultList();
        return crearDtos(estudiantes);
    }

    /**
     * Busca estudiantes por número de libreta universitaria (LU).
     * @param LU Número de libreta universitaria a buscar.
     * @return Lista de estudiantes filtrados.
     */
    public List<EstudianteDTO> buscarEstudiantesLU(int LU){
        EntityManager em = JPAUtil.getEntityManager();
        List<Estudiante> estudiantes = em.createQuery(
                        "SELECT e FROM Estudiante e WHERE e.LU = :LU",
                        Estudiante.class)
                .setParameter("LU", LU)
                .getResultList();
        return crearDtos(estudiantes);
    }

    /**
     * Busca estudiantes ordenados por apellido.
     * @return Lista de estudiantes ordenados.
     */
    public List<EstudianteDTO> buscarEstudiantesApellido(){
        EntityManager em = JPAUtil.getEntityManager();
        List<Estudiante> estudiantes = em.createQuery(
                        "SELECT e FROM Estudiante e ORDER BY e.apellido",
                        Estudiante.class)
                .getResultList();
        return crearDtos(estudiantes);
    }

    /**
     * Busca estudiantes por carrera y ciudad.
     * @param carrera Nombre de la carrera.
     * @param ciudad Nombre de la ciudad.
     * @return Lista de estudiantes filtrados.
     */
    public List<EstudianteDTO> buscarEstudiantesApellido(String carrera, String ciudad){
        EntityManager em = JPAUtil.getEntityManager();
        List<Estudiante> estudiantes = em.createQuery(
                                "SELECT e FROM Estudiante e " +
                                "JOIN EstudianteCarrera ec " +
                                "WHERE carrera = :carrera AND ciudad = :ciudad",
                        Estudiante.class)
                .setParameter("carrera", carrera)
                .setParameter("ciudad", ciudad)
                .getResultList();

        return crearDtos(estudiantes);
    }

    /**
     * Obtiene la lista completa de estudiantes.
     * @return Lista de estudiantes.
     */
    public List<EstudianteDTO> buscarEstudiantes(){
        EntityManager em = JPAUtil.getEntityManager();
        List<Estudiante> estudiantes = em.createQuery("SELECT e FROM Estudiante e", Estudiante.class).getResultList();
        return crearDtos(estudiantes);
    }

    /**
     * Convierte una lista de entidades Estudiante en una lista de objetos edu.isistan.db.DTO.
     * @param estudiantes Lista de estudiantes.
     * @return Lista de objetos EstudianteDTO.
     */
    public  List<EstudianteDTO> crearDtos(List<Estudiante> estudiantes){
        List<EstudianteDTO> estudiantesDTO = new ArrayList<>();
        for (Estudiante e: estudiantes){
            EstudianteDTO estudianteDTO = new EstudianteDTO(
                    e.getDNI(),
                    e.getNombre(),
                    e.getApellido(),
                    e.getEdad(),
                    e.getGenero(),
                    e.getCiudad(),
                    e.getLU());
            estudiantesDTO.add(estudianteDTO);
        }
        return estudiantesDTO;
    }
}
