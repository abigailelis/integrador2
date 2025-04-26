import com.opencsv.exceptions.CsvValidationException;
import dto.CarreraInscriptosDTO;
import dto.ReporteCarreraDTO;
import repository.CarreraRepository;
import repository.EstudianteCarreraRepository;
import repository.EstudianteRepository;
import java.io.IOException;
import java.util.List;


public class Main {

    public static void main(String[] args) throws CsvValidationException, IOException {

        CarreraRepository repCarrera = new CarreraRepository();
        EstudianteRepository repEstudiante = new EstudianteRepository();
        EstudianteCarreraRepository repEstudianteCarrera = new EstudianteCarreraRepository();

        repCarrera.insertarCarreraCSV("carreras.csv");
        repEstudiante.insertarEstudianteCSV("estudiantes.csv");
        repEstudianteCarrera.matricularEstudiantesCSV("estudianteCarrera.csv");

        //System.out.println(repEstudiante.buscarEstudiantes());


        // Insertar estudiante, Insertar carrera y Matricular estudiante
        /**
        Estudiante newEstudiante = new Estudiante(39111222,"Manuel","Perez",66,"Male","Necochea",99911);
        Carrera newCarrera = new Carrera(50, "Diseñador Gráfico", 3);

        repEstudiante.insertarEstudiante(newEstudiante);
        repCarrera.insertarCarrera(newCarrera);
        repEstudianteCarrera.matricularEstudiante(newEstudiante, newCarrera);

        System.out.println(repEstudiante.buscarEstudiantes());
         */

        //System.out.println(repEstudiante.buscarEstudiantesApellido());
        //System.out.println(repEstudiante.buscarEstudiantesLU(19844));
        //System.out.println(repEstudiante.buscarEstudiantesGenero("Male"));
        //System.out.println(repEstudiante.buscarEstudiantesCarreraCiudad("TUDAI", "Samal"));

        //List<ReporteCarreraDTO> reportes = repCarrera.generarReporteCarreras();
        //for (ReporteCarreraDTO reporte : reportes){
        //    System.out.println(reporte);
        //}

        List<CarreraInscriptosDTO> reportes = repEstudianteCarrera.buscarCarrerasConEstudiantes();
        for (CarreraInscriptosDTO reporte : reportes){
            System.out.println(reporte);
        }

    }

}
