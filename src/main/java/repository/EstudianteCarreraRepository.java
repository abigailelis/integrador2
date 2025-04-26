package repository;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import entities.*;
import factory.JPAUtil;

import jakarta.persistence.EntityManager;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;

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
            EstudianteCarreraKey estudianteCarreraKey = new EstudianteCarreraKey();
            estudianteCarreraKey.setId_estudiante(id_estudiante);
            estudianteCarreraKey.setId_carrera(id_carrera);

            EstudianteCarrera matriculado = em.find(EstudianteCarrera.class, estudianteCarreraKey);
            Estudiante estudiante = em.find(Estudiante.class, id_estudiante);
            Carrera carrera = em.find(Carrera.class, id_carrera);

            if (matriculado==null && estudiante!=null && carrera!=null) { //Si existe el estudiante y la carrera pero no está matriculado
                EstudianteCarrera estudianteCarrera = new EstudianteCarrera();
                estudianteCarrera.setId(estudianteCarreraKey);
                estudianteCarrera.setInscripcion(Integer.parseInt(line[2]));
                estudianteCarrera.setGraduacion(Integer.parseInt(line[3]));
                estudianteCarrera.setAntiguedad(Integer.parseInt(line[4]));

                em.persist(estudianteCarrera);
            }
        }

        em.getTransaction().commit();
        System.out.print("\nEstudiantes matriculados exitosamente");
        em.close();
    }

    public void matricularEstudiante(Estudiante newEstudiante, Carrera newCarrera){
        EntityManager em = JPAUtil.getEntityManager();

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
