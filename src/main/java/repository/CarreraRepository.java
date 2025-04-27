package repository;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import dto.*;
import entities.*;
import factory.JPAUtil;
import jakarta.persistence.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CarreraRepository {

    /**
     * Inserta todas las carreras desde un archivo csv.
     * En caso de haber errores en la transacción se muestran los mensajes correspondientes
     * @param urlFile path del archivo csv correspondiente
     */
    public void insertarCarreraCSV(String urlFile) {
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
                    Carrera carrera = new Carrera();
                    carrera.setId_carrera(Integer.parseInt(line[0]));
                    carrera.setCarrera(line[1]);
                    carrera.setDuracion(Integer.parseInt(line[2]));
                    em.persist(carrera);
                } catch (Exception e) {
                    success = false;
                    System.err.println("\nError al agregar la carrera con el ID: " + line[0] + " - " + e.getMessage());
                }
            }

            if (success)
                commitTransaction(transaction, "\nCarreras agregadas exitosamente.");
            else
                rollbackTransaction(transaction, "\nError al intentar insertar las carreras, la transacción se ha revertido.");

        } catch (Exception e) {
            System.err.println("\nError al leer el archivo CSV: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    /**
     * Inserta una nueva carrera en la base de datos
     * @param carrera Carrera nueva que se desea agregar a la base de datos
     */
    public void insertarCarrera(Carrera carrera) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(carrera);
            commitTransaction(transaction, "\nCarrera agregada exitosamente: " + carrera.getCarrera());
        } catch (Exception e) {
            rollbackTransaction(transaction, "\nError al insertar carrera con ID " + carrera.getId_carrera() + ": " + e.getMessage());
        } finally {
            em.close();
        }
    }

    /**
     * Genera un reporte de carreras ordenada alfabeticamente y cronologicamente,
     * mostrando los inscriptos y graduados por carrera
     * @return lista de carreras con sus cantidades de inscriptos y graduados por año
     */
    public List<ReporteCarreraDTO> generarReporteCarreras() {
        EntityManager em = JPAUtil.getEntityManager();
        List<ReporteCarreraDTO> reportes = new ArrayList<>();

        try {
            reportes = em.createQuery(
                    "SELECT new dto.ReporteCarreraDTO(" +
                            "c.carrera, COALESCE(ec.inscripcion, ec.graduacion), " +
                            "SUM(CASE WHEN ec.inscripcion IS NOT NULL THEN 1 ELSE 0 END), " +
                            "SUM(CASE WHEN ec.graduacion IS NOT NULL AND ec.graduacion > 0 THEN 1 ELSE 0 END) " +
                            ")" +
                            "FROM EstudianteCarrera ec " +
                            "JOIN ec.carrera c " +
                            "GROUP BY c.carrera, COALESCE(ec.inscripcion, ec.graduacion) " +
                            "ORDER BY c.carrera, COALESCE(ec.inscripcion, ec.graduacion)",
                    ReporteCarreraDTO.class
            ).getResultList();

            if (reportes.isEmpty())
                System.out.println("No se encontraron registros para el reporte de carreras.");

        } catch (Exception e) {
            System.err.println("Error al generar el reporte de carreras: " + e.getMessage());
        } finally {
            em.close();
        }

        return reportes;
    }

    /**
     * Realiza el commit de una transacción y muestra un mensaje de éxito.
     * @param transaction La transacción que se va a confirmar.
     * @param msgExito Mensaje que se imprimirá al completar la transacción exitosamente.
     */
    public void commitTransaction(EntityTransaction transaction, String msgExito){
        transaction.commit();
        System.out.println(msgExito);
    }

    /**
     * Revierte la transacción y muestra un mensaje de error.
     * @param transaction La transacción que se va a revertir.
     * @param msgExito Mensaje de error que se imprimirá al revertir la transacción.
     */
    public void rollbackTransaction(EntityTransaction transaction, String msgError){
        transaction.rollback();
        System.err.println(msgError);
    }
}