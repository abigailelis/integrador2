package repository;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import dto.EstudianteDTO;
import entities.Estudiante;
import factory.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class EstudianteRepository {

    /**
     * Inserta estudiantes desde un archivo CSV en la base de datos.
     *
     * @param urlFile path del archivo CSV a procesar.
     */
    public void insertarEstudianteCSV(String urlFile) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        String root = "src\\main\\resources\\csv\\" + urlFile;

        try (CSVReader reader = new CSVReader(new FileReader(root))) {
            String[] line;
            reader.readNext();

            transaction.begin();
            boolean success = true;

            while ((line = reader.readNext()) != null) {
                try {
                    Estudiante estudiante = new Estudiante();
                    estudiante.setDNI(Integer.parseInt(line[0]));
                    estudiante.setNombre(line[1]);
                    estudiante.setApellido(line[2]);
                    estudiante.setEdad(Integer.parseInt(line[3]));
                    estudiante.setGenero(line[4]);
                    estudiante.setCiudad(line[5]);
                    estudiante.setLU(Integer.parseInt(line[6]));
                    em.persist(estudiante);
                } catch (Exception e) {
                    success = false;
                    System.err.println("Error al agregar el estudiante con el ID: " + line[0] + " - " + e.getMessage());
                }
            }
            if (success)
                commitTransaction(transaction, "\nEstudiantes agregados exitosamente");
            else
                rollbackTransaction(transaction, "\nError al intentar insertar las carreras, la transacción se ha revertido.");

        } catch (Exception e) {
            System.err.println("\nError al leer el archivo CSV: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    /**
     * Inserta un nuevo estudiante en la base de datos.
     *
     * @param estudiante Estudiante a insertar.
     */
    public void insertarEstudiante(Estudiante estudiante) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(estudiante);
            commitTransaction(transaction, "\nEstudiante dado de alta correctamente - Nombre: " + estudiante.getNombre() +", apellido: " +estudiante.getApellido());
        } catch (Exception e) {
            rollbackTransaction(transaction, "\nError al dar de alta a estudiante con ID: " + estudiante.getDNI() + ": " + e.getMessage());
        } finally {
            em.close();
        }
    }

    /**
     * Filtra estudiantes por género.
     *
     * @param genero Género de los estudiantes a buscar.
     * @return Lista de estudiantes filtrados.
     */
    public List<EstudianteDTO> buscarEstudiantesGenero(String genero) {
        EntityManager em = JPAUtil.getEntityManager();
        List<EstudianteDTO> estudiantesDTO = new ArrayList<>();

        try {
            List<Estudiante> estudiantes = em.createQuery(
                            "SELECT e FROM Estudiante e WHERE e.genero = :genero", Estudiante.class)
                    .setParameter("genero", genero)
                    .getResultList();
            estudiantesDTO = verificarListasVacias(estudiantes, estudiantesDTO);
        } catch (Exception e) {
            System.err.println("\nError al buscar estudiantes por género: " + e.getMessage());
        } finally {
            em.close();
        }

        return estudiantesDTO;
    }

    /**
     * Busca un estudiante por número de libreta universitaria (LU).
     *
     * @param LU Número de libreta universitaria a buscar.
     * @return estudiante encontrado.
     */
    public EstudianteDTO buscarEstudianteLU(int LU) {
        EntityManager em = JPAUtil.getEntityManager();
        EstudianteDTO estudianteDTO = null;

        try {
            Estudiante estudiante = em.createQuery(
                            "SELECT e FROM Estudiante e WHERE e.LU = :LU",
                            Estudiante.class)
                    .setParameter("LU", LU)
                    .getSingleResult();
            estudianteDTO = crearDTO(estudiante);

        } catch (NoResultException e) {
            System.err.println("\nNo se encontró ningún estudiante con LU: " + LU);
        } catch (Exception e) {
            System.err.println("\nError al buscar estudiante con LU " + LU + ": " + e.getMessage());
        } finally {
            em.close();
        }

        return estudianteDTO;
    }

    /**
     * Busca estudiantes ordenados por apellido.
     *
     * @return Lista de estudiantes ordenados.
     */
    public List<EstudianteDTO> buscarEstudiantesApellido() {
        EntityManager em = JPAUtil.getEntityManager();
        List<EstudianteDTO> estudiantesDTO = new ArrayList<>();

        try {
            List<Estudiante> estudiantes = em.createQuery(
                            "SELECT e FROM Estudiante e ORDER BY e.apellido", Estudiante.class)
                    .getResultList();
            estudiantesDTO = verificarListasVacias(estudiantes, estudiantesDTO);
        } catch (Exception e) {
            System.err.println("\nError al buscar estudiantes por apellido: " + e.getMessage());
        } finally {
            em.close();
        }

        return estudiantesDTO;
    }

    /**
     * Busca estudiantes por una carrera determinada y filtrados por ciudad.
     *
     * @param carrera Nombre de la carrera que se desea filtrar.
     * @param ciudad  Nombre de la ciudad que se desea fitrar.
     * @return Lista de estudiantes filtrados.
     */
    public List<EstudianteDTO> buscarEstudiantesCarreraCiudad(String carrera, String ciudad) {
        EntityManager em = JPAUtil.getEntityManager();
        List<EstudianteDTO> estudiantesDTO = new ArrayList<>();

        try {
            List<Estudiante> estudiantes = em.createQuery(
                            "SELECT e FROM Estudiante e JOIN e.carreras ec " +
                                    "WHERE ec.carrera.carrera = :carrera AND e.ciudad = :ciudad", Estudiante.class)
                    .setParameter("carrera", carrera)
                    .setParameter("ciudad", ciudad)
                    .getResultList();
            estudiantesDTO = verificarListasVacias(estudiantes, estudiantesDTO);
        } catch (Exception e) {
            System.err.println("\nError al buscar estudiantes en la carrera: " + carrera + ", y ciudad: " + ciudad + "': " + e.getMessage());
        } finally {
            em.close();
        }

        return estudiantesDTO;
    }

    /**
     * Obtiene la lista completa de estudiantes.
     *
     * @return Lista de estudiantes.
     */
    public List<EstudianteDTO> buscarEstudiantes() {
        EntityManager em = JPAUtil.getEntityManager();
        List<EstudianteDTO> estudiantesDTO = new ArrayList<>();

        try {
            List<Estudiante> estudiantes = em.createQuery("SELECT e FROM Estudiante e", Estudiante.class).getResultList();
            estudiantesDTO = verificarListasVacias(estudiantes, estudiantesDTO);
        } catch (Exception e) {
            System.err.println("\nError al buscar estudiantes: " + e.getMessage());
        } finally {
            em.close();
        }

        return estudiantesDTO;
    }

    /**
     * Convierte una lista de entidades Estudiante en una lista de objetos EstudianteDTO.
     * @param estudiantes Lista de estudiantes.
     * @return Lista de objetos EstudianteDTO.
     */
    public List<EstudianteDTO> crearDtos(List<Estudiante> estudiantes) {
        List<EstudianteDTO> estudiantesDTO = new ArrayList<>();
        for (Estudiante estudiante : estudiantes)
            estudiantesDTO.add(crearDTO(estudiante));
        return estudiantesDTO;
    }

    /**
     * Convierte un objeto Estudiante en uno del tipo EstudianteDTO.
     * @param estudiantes estudiantes.
     * @return objeto de EstudianteDTO.
     */
    public EstudianteDTO crearDTO(Estudiante estudiante) {
        return new EstudianteDTO(
                estudiante.getDNI(),
                estudiante.getNombre(),
                estudiante.getApellido(),
                estudiante.getEdad(),
                estudiante.getGenero(),
                estudiante.getCiudad(),
                estudiante.getLU());
    }

    /**
     * Verifica que la lista de elementos de la base de datos no este vacia
     * @param estudiantes
     * @param estudiantesDTO
     * @return
     */
    public List<EstudianteDTO> verificarListasVacias(List<Estudiante> estudiantes, List<EstudianteDTO> estudiantesDTO) {
        if (estudiantes.isEmpty())
            System.out.println("\nNo se encontraron estudiantes registrados.");
        else
            estudiantesDTO = crearDtos(estudiantes);
        return estudiantesDTO;
    }

    /**
     * Realiza el commit de una transacción y muestra un mensaje de éxito.
     * @param transaction La transacción que se va a confirmar.
     * @param msgExito    Mensaje que se imprimirá al completar la transacción exitosamente.
     */
    public void commitTransaction(EntityTransaction transaction, String msgExito) {
        transaction.commit();
        System.out.println(msgExito);
    }

    /**
     * Revierte la transacción y muestra un mensaje de error.
     * @param transaction La transacción que se va a revertir.
     * @param msgError    Mensaje de error que se imprimirá al revertir la transacción.
     */
    public void rollbackTransaction(EntityTransaction transaction, String msgError) {
        transaction.rollback();
        System.err.println(msgError);
    }


}
