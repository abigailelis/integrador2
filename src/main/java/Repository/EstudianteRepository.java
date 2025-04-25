package Repository;

import Entities.Estudiante;
import javax.persistence.EntityManager;
import java.io.IOException;
import org.apache.commons.csv.*;

public class EstudianteRepository {

    /**
     * Reads a CSV file and inserts new student records into the database.
     * Each row in the CSV should contain student details
     *
     * @param urlFile the file path or URL of the CSV file to be processed.
     * @throws IOException if an error occurs while reading the CSV file.
     * @throws CsvValidationException if the CSV format is invalid or data validation fails.
     */
    public void insertarEstudianteCSV(String urlFile) throws IOException, CsvValidationException {
        EntityManager em = JPAUtil.getEntityManager();
        CSVReader reader = new CSVReader(new FileReader(urlFile));
        String[] line;
        reader.readNext();

        em.getTransaction().begin();

        while((line = reader.readNext()) != null){
            Estudiante estudiante = new Estudiante();

            estudiante.setDNI(Integer.parseInt(line[0]));

            em.persist(estudiante);
        }

        em.getTransaction().commit();
        System.out.print("\nEstudiante agregado exitosamente");
        em.close();
    }
}
