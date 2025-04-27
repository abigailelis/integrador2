package repository;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import dto.CarreraInscriptosDTO;
import dto.EstudianteDTO;
import entities.*;
import factory.JPAUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EstudianteCarreraRepository {

    /**
     * Matricula a los estudiantes en las carreras desde archivo CSV y los persiste en la base de datos
     * @param urlFile path del archivo CSV
     */
    public void matricularEstudiantesCSV(String urlFile) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        String root = "src\\main\\resources\\csv\\" + urlFile;

        try (CSVReader reader = new CSVReader(new FileReader(root))) {
            String[] line;
            reader.readNext();

            em.getTransaction().begin();
            boolean success = true;

            while ((line = reader.readNext()) != null) {
                try {
                    int id_estudiante = Integer.parseInt(line[0]);
                    int id_carrera = Integer.parseInt(line[1]);

                    EstudianteCarreraKey estudianteCarreraKey = crearKey(id_estudiante, id_carrera);

                    if (verificarMatriculacion(id_estudiante, id_carrera, estudianteCarreraKey, em)) {
                        int inscripcion = Integer.parseInt(line[2]);
                        int graduacion = Integer.parseInt(line[3]);
                        int antiguedad = Integer.parseInt(line[4]);
                        persistEstudiantecarrera(estudianteCarreraKey, inscripcion, graduacion, antiguedad, em);

                    }
                } catch (Exception e) {
                    success = false;
                    System.err.println("\nError al intentar matricular: " + e.getMessage());
                }
            }
            if (success)
                commitTransaction(transaction, "\nEstudiantes maticulados exitosamente.");
            else
                rollbackTransaction(transaction, "\nError al intentar matricular a los estudiantes, la transacción se ha revertido.");

        } catch (Exception e) {
            System.err.println("\nError al leer el archivo CSV: " + e.getMessage());
        } finally {
            em.close();
        }
    }


    /**
     * Matricula a un estudiante en la carrera seleccionada
     * Requisitos: Debe existir el estudiante y la carrera en la base de datos y no debe estar matriculado en dicha carrera
     * @param newEstudiante
     * @param newCarrera
     */
    public void matricularEstudiante(Estudiante newEstudiante, Carrera newCarrera) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            EstudianteCarreraKey estudianteCarreraKey = crearKey(newEstudiante.getDNI(), newCarrera.getId_carrera());

            if (verificarMatriculacion(newEstudiante.getDNI(), newCarrera.getId_carrera(), estudianteCarreraKey, em)) {  //Si existe el estudiante y la carrera en la bd
                int anioActual = Calendar.getInstance().get(Calendar.YEAR);
                int graduacion = anioActual + newCarrera.getDuracion();
                int antiguedad = 0;
                persistEstudiantecarrera(estudianteCarreraKey, anioActual, graduacion, antiguedad, em);
                commitTransaction(transaction, "\nEstudiante matriculado correctamente - Nombre: " + newEstudiante.getNombre() + ", apellido: " + newEstudiante.getApellido() + ", carrera: " + newCarrera.getCarrera());
            }
        } catch (Exception e) {
            rollbackTransaction(transaction, "\nError al matricular al estudiante con ID: " + newEstudiante.getDNI() + ", en la carrera " + newCarrera.getCarrera()+ " : " + e.getMessage());
        } finally {
            em.close();
        }
    }

    /**
     * Genera una lista de carreras ordenada por cantidad de estudiantes inscriptos
     * @return lista de carreras ordenada por cantidad de estudiantes inscriptos
     */
    public List<CarreraInscriptosDTO> buscarCarrerasConEstudiantes() {
        EntityManager em = JPAUtil.getEntityManager();
        List<CarreraInscriptosDTO> reportes = new ArrayList<>();

        try{
            reportes = em.createQuery("SELECT new dto.CarreraInscriptosDTO(" +
                    "c.carrera, c.duracion, " +
                    "COUNT(CASE WHEN ec.inscripcion IS NOT NULL AND ec.inscripcion > 0 THEN 1 ELSE 0 END)" +
                    ")" +
                    "FROM EstudianteCarrera ec " +
                    "JOIN ec.carrera c " +
                    "GROUP BY c.carrera, c.duracion " +
                    "ORDER BY SUM(CASE WHEN ec.inscripcion IS NOT NULL AND ec.inscripcion > 0 THEN 1 ELSE 0 END) DESC",
                    CarreraInscriptosDTO.class)
                    .getResultList();
        } catch (Exception e) {
            System.out.println("\nError al buscar las carreras con estudiantes inscriptos: " + e.getMessage());
        } finally {
            em.close();
        }

        return reportes;
    }

    /**
     * Verifica si existe el estudiante y la carrera en la base de datos y que no esté matriculado en dicha carrera
     * @param id_estudiante id_estudiante a matricular
     * @param id_carrera id_carrera donde se quiere matricular
     * @param id_estudianteCarreraKey id compuesto por id_estudiante y id_carrera
     * @param em EntityManager
     * @return boolean
     */
    public boolean verificarMatriculacion(int id_estudiante, int id_carrera, EstudianteCarreraKey id_estudianteCarreraKey, EntityManager em) {
        EstudianteCarrera matriculado = em.find(EstudianteCarrera.class, id_estudianteCarreraKey);
        Estudiante estudiante = em.find(Estudiante.class, id_estudiante);
        Carrera carrera = em.find(Carrera.class, id_carrera);

        return matriculado == null && estudiante != null && carrera != null;
    }


    /**
     * Persiste al estudiante en la carrera seleccionada en la base de datos
     * @param estudianteCarreraKey id compuesto por id_estudiante y id_carrera
     * @param inscripcion año de inscripción
     * @param graduacion año de graduación
     * @param duracion cantidad de años que dura la carrera
     * @param em EntityManager
     */
    public void persistEstudiantecarrera(EstudianteCarreraKey estudianteCarreraKey, int inscripcion, int graduacion, int duracion, EntityManager em) {
        EstudianteCarrera estudianteCarrera = new EstudianteCarrera();
        estudianteCarrera.setId(estudianteCarreraKey);
        estudianteCarrera.setInscripcion(inscripcion);
        estudianteCarrera.setGraduacion(graduacion);
        estudianteCarrera.setAntiguedad(duracion);
        em.persist(estudianteCarrera);
    }

    /**
     * Genera la clave primaria compuesta
     * @param id_estudiante id_estudiante que se desea matricular
     * @param id_carrera id_carrera en la cual se desea matricular al estudiante
     * @return clave compuesta
     */
    public EstudianteCarreraKey crearKey(int id_estudiante, int id_carrera) {
        EstudianteCarreraKey estudianteCarreraKey = new EstudianteCarreraKey();
        estudianteCarreraKey.setId_estudiante(id_estudiante);
        estudianteCarreraKey.setId_carrera(id_carrera);

        return estudianteCarreraKey;
    }

    /**
     * Realiza el commit de una transacción y muestra un mensaje de éxito.
     * @param transaction La transacción que se va a confirmar
     * @param msgExito Mensaje que se imprimirá al completar la transacción exitosamente
     */
    public void commitTransaction(EntityTransaction transaction, String msgExito) {
        transaction.commit();
        System.out.println(msgExito);
    }

    /**
     * Revierte la transacción y muestra un mensaje de error.
     * @param transaction La transacción que se va a revertir.
     * @param msgError Mensaje de error que se imprimirá al revertir la transacción.
     */
    public void rollbackTransaction(EntityTransaction transaction, String msgError) {
        transaction.rollback();
        System.err.println(msgError);
    }

}
