package repository;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import dto.CarreraInscriptosDTO;
import entities.*;
import factory.JPAUtil;

import jakarta.persistence.EntityManager;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EstudianteCarreraRepository {

    public void matricularEstudiantesCSV(String urlFile) throws IOException, CsvValidationException {
        EntityManager em = JPAUtil.getEntityManager();
        String root = "src\\main\\resources\\csv\\" + urlFile;
        CSVReader reader = new CSVReader(new FileReader(root));
        String[] line;
        reader.readNext();

        em.getTransaction().begin();

        while((line = reader.readNext()) != null){
            int id_estudiante = Integer.parseInt(line[0]);
            int id_carrera = Integer.parseInt(line[1]);

            EstudianteCarreraKey estudianteCarreraKey = crearKey(id_estudiante, id_carrera);

            if (verificarEstudiante(id_estudiante, id_carrera, estudianteCarreraKey, em)) {
                int inscripcion = Integer.parseInt(line[2]);
                int graduacion = Integer.parseInt(line[3]);
                int antiguedad = Integer.parseInt(line[4]);
                persistEstudiantecarrera(estudianteCarreraKey, inscripcion, graduacion, antiguedad, em);

            }
        }

        em.getTransaction().commit();
        System.out.print("\nEstudiantes matriculados exitosamente");
        em.close();
    }



    public void matricularEstudiante(Estudiante newEstudiante, Carrera newCarrera){
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();

        EstudianteCarreraKey estudianteCarreraKey = crearKey(newEstudiante.getDNI(), newCarrera.getId_carrera());

        if(verificarEstudiante(newEstudiante.getDNI(), newCarrera.getId_carrera(),estudianteCarreraKey, em)){  //Si existe el estudiante y la carrera en la bd
            int anioActual = Calendar.getInstance().get(Calendar.YEAR);
            int graduacion = anioActual + newCarrera.getDuracion();
            int antiguedad = 0;
            persistEstudiantecarrera(estudianteCarreraKey, anioActual, graduacion, antiguedad, em);
        }

        em.getTransaction().commit();
        System.out.print("\nEstudiante matriculado exitosamente");
        em.close();
    }

    /**
     * Verifica si existe el estudiante y la carrera pero que no esté matriculado
     * @param id_estudiante
     * @param id_carrera
     * @param id_estudianteCarreraKey
     * @param em
     * @return boolean
     */
    public boolean verificarEstudiante(int id_estudiante, int id_carrera, EstudianteCarreraKey id_estudianteCarreraKey, EntityManager em){
        EstudianteCarrera matriculado = em.find(EstudianteCarrera.class, id_estudianteCarreraKey);
        Estudiante estudiante = em.find(Estudiante.class, id_estudiante);
        Carrera carrera = em.find(Carrera.class, id_carrera);

        return matriculado==null && estudiante!=null && carrera!=null;
    }


    /**
     * Persiste al estudiante en la carrera seleccionada
     * @param estudianteCarreraKey
     * @param inscripcion
     * @param graduacion
     * @param duracion
     * @param em
     */
    public void persistEstudiantecarrera(EstudianteCarreraKey estudianteCarreraKey, int inscripcion, int graduacion, int duracion, EntityManager em){
        EstudianteCarrera estudianteCarrera = new EstudianteCarrera();
        estudianteCarrera.setId(estudianteCarreraKey);
        estudianteCarrera.setInscripcion(inscripcion);
        estudianteCarrera.setGraduacion(graduacion);
        estudianteCarrera.setAntiguedad(duracion);

        em.persist(estudianteCarrera);
    }

    /**
     * Genera la clave primaria compuesta
     * @param id_estudiante El estudiante que se desea matricular
     * @param id_carrera La carrera en la cual se desea matricular al estudiante
     * @return clave compuesta
     */
    public EstudianteCarreraKey crearKey(int id_estudiante, int id_carrera){
        EstudianteCarreraKey estudianteCarreraKey = new EstudianteCarreraKey();
        estudianteCarreraKey.setId_estudiante(id_estudiante);
        estudianteCarreraKey.setId_carrera(id_carrera);

        return estudianteCarreraKey;
    }

    /**
     * esta funcion genera una lista de carreras con estudiantes inscriptos y ordenada por su cantidad
     * @return lista de carreras con sus cantidades de inscriptos y ordenada por ellos
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
                    "ORDER BY SUM(CASE WHEN ec.inscripcion IS NOT NULL AND ec.inscripcion > 0 THEN 1 ELSE 0 END) DESC", CarreraInscriptosDTO.class).getResultList();
        } catch (Exception e){
            System.out.println(e.getMessage());
        } finally {
            em.close();
        }
        return reportes;
    }
    
}
