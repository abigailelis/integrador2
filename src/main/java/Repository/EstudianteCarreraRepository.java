package Repository;

import Entities.Carrera;
import Entities.Estudiante;
import Entities.EstudianteCarrera;
import Entities.EstudianteCarreraKey;
import Factory.JPAUtil;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import javax.persistence.EntityManager;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;

public class EstudianteCarreraRepository {

    private EntityManager em;

    public EstudianteCarreraRepository(){
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
    public void matricularEstudiantesCSV(String urlFile) throws IOException, CsvValidationException {
        CSVReader reader = new CSVReader(new FileReader(urlFile));
        String[] line;
        reader.readNext();

        em.getTransaction().begin();

        while((line = reader.readNext()) != null){
            EstudianteCarrera estudianteCarrera = new EstudianteCarrera();
            EstudianteCarreraKey estudianteCarreraKey = new EstudianteCarreraKey();

            estudianteCarreraKey.setId_estudiante(Integer.parseInt(line[0]));
            estudianteCarreraKey.setId_carrera(Integer.parseInt(line[1]));
            estudianteCarrera.setId(estudianteCarreraKey);
            estudianteCarrera.setInscripcion(Integer.parseInt(line[2]));
            estudianteCarrera.setGraduacion(Integer.parseInt(line[3]));
            estudianteCarrera.setAntiguedad(Integer.parseInt(line[4]));

            em.persist(estudianteCarrera);
        }

        em.getTransaction().commit();
        System.out.print("\nEstudiantes matriculados exitosamente");
        em.close();
    }

    public void matricularEstudiante(Estudiante newEstudiante, Carrera newCarrera){
        Estudiante estudiante = em.find(Estudiante.class, newEstudiante.getDNI());
        Carrera carrera = em.find(Carrera.class, newCarrera.getId_carrera());
        EstudianteCarrera estudianteCarrera = new EstudianteCarrera();
        EstudianteCarreraKey estudianteCarreraKey = new EstudianteCarreraKey();
        int anioActual = Calendar.getInstance().get(Calendar.YEAR);
        
        if(estudiante != null && carrera != null){  //Si existe el estudiante y la carrera en la bd
            estudianteCarreraKey.setId_estudiante(estudiante.getDNI());
            estudianteCarreraKey.setId_carrera(carrera.getId_carrera());
            estudianteCarrera.setId(estudianteCarreraKey);
            estudianteCarrera.setInscripcion(anioActual);
            estudianteCarrera.setGraduacion(anioActual + carrera.getDuracion());
            estudianteCarrera.setAntiguedad(anioActual - estudianteCarrera.getInscripcion());

            em.persist(estudianteCarrera);
        }

        em.getTransaction().commit();
        System.out.print("\nEstudiante matriculado exitosamente");
        em.close();
    }


    
}
