package Repository;

import Entities.*;
import Factory.JPAUtil;
import com.opencsv.*;
import com.opencsv.exceptions.CsvValidationException;
import javax.persistence.*;
import java.io.*;
import java.util.List;

public class CarreraRepository {
    /**
     * Reads a CSV file and inserts new person records into the database.
     * Each row in the CSV should contain person details, including an address ID.
     *
     * @param urlFile the file path or URL of the CSV file to be processed.
     * @throws IOException if an error occurs while reading the CSV file.
     * @throws CsvValidationException if the CSV format is invalid or data validation fails.
     */
    private EntityManager em;

    public CarreraRepository() {
        this.em = JPAUtil.getEntityManager();
    }

    // Insertar carras desde archivo CSV
    public void insertarCarreraCSV(String urlFile) throws IOException, CsvValidationException {
        EntityManager em = JPAUtil.getEntityManager();
        CSVReader reader = new CSVReader(new FileReader(urlFile));
        String[] line;
        reader.readNext();

        em.getTransaction().begin();

        while((line = reader.readNext()) != null){
            Carrera carrera = new Carrera();

            carrera.setId_carrera(Integer.parseInt(line[0]));
            carrera.setCarrera(line[1]);
            carrera.setDuracion(Integer.parseInt(line[2]));
            em.persist(carrera);
        }

        em.getTransaction().commit();
        System.out.print("\nCarreras agregadas exitosamente");
        em.close();
    }

    // Buscar carrera por ID
    public CarreraDTO buscarCarreraId(int id) {
        return em.find(Carrera.class, id);
    }

    // Buscar todas las carreras
    public List<CarreraDTO> buscarTodasCarreras() {
        TypedQuery<CarreraDTO> query = em.createQuery("SELECT c FROM Carrera c", Carrera.class);
        return query.getResultList();
    }

    // Insertar carrera manualmente
    public void insertarCarrera(Carrera carrera) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(carrera);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }
}