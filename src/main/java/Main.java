import com.opencsv.exceptions.CsvValidationException;
import repository.CarreraRepository;
import repository.EstudianteCarreraRepository;
import repository.EstudianteRepository;
import java.io.IOException;


public class Main {

    public static void main(String[] args) throws CsvValidationException, IOException {

        CarreraRepository repCarrera = new CarreraRepository();
        EstudianteRepository repEstudiante = new EstudianteRepository();
        EstudianteCarreraRepository repEstudianteCarrera = new EstudianteCarreraRepository();

        repCarrera.insertarCarreraCSV("carreras.csv");
        repEstudiante.insertarEstudianteCSV("estudiantes.csv");
        repEstudianteCarrera.matricularEstudiantesCSV("estudianteCarrera.csv");

        System.out.println(repEstudiante.buscarEstudiantes());
        //dejar comentado el metodo de insertar estudiante y matricular estudiante

    }

}
