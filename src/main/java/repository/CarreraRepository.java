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

    // Insertar carras desde archivo CSV
    public void insertarCarreraCSV(String urlFile) throws IOException, CsvValidationException {
        EntityManager em = JPAUtil.getEntityManager();
        String root = "src\\main\\resources\\csv\\" + urlFile;
        CSVReader reader = new CSVReader(new FileReader(root));
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

    // Buscar todas las carreras
    public List<CarreraDTO> buscarTodasCarreras() {
        EntityManager em = JPAUtil.getEntityManager();
        TypedQuery<Carrera> query = em.createQuery("SELECT c FROM Carrera c", Carrera.class);
        List<Carrera> resultados = query.getResultList();
        List<CarreraDTO> carrerasDTO = new ArrayList<>();

        for (Carrera c : resultados) {
            CarreraDTO carreraDTO = new CarreraDTO(c.getId_carrera(), c.getCarrera(), c.getDuracion());
            carrerasDTO.add(carreraDTO);
        }

        return carrerasDTO;
    }

    // Insertar carrera manualmente
    public void insertarCarrera(Carrera carrera) {
        EntityManager em = JPAUtil.getEntityManager();
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