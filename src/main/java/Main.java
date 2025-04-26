import com.opencsv.exceptions.CsvValidationException;
import entities.Carrera;
import entities.Estudiante;
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

        // Dar de alta a un estudiante
        //Estudiante newEstudiante = new Estudiante(39111222,"Manuel","Perez",66,"Male","Necochea",99911);
        //repEstudiante.insertarEstudiante(newEstudiante);

        //b)Matricular estudiante en una carrera
        //Carrera newCarrera = new Carrera(50, "Diseñador Gráfico", 3);
        //repCarrera.insertarCarrera(newCarrera);
        //repEstudianteCarrera.matricularEstudiante(newEstudiante, newCarrera);

        //c)Recuperar todos los estudiantes con el criterio de ordenamiento : Apellido;
        // System.out.println(repEstudiante.buscarEstudiantesApellido());

        //d)Recuperar un estudiante en base a su LU
        // System.out.println(repEstudiante.buscarEstudiantesLU(19844));

        //e)Recuperar todos los estudiantes en base a su género
        //System.out.println(repEstudiante.buscarEstudiantesGenero("Male"));

        //f)Recuperar las carreras con estudiantes inscriptos y ordenar por cantidad de inscriptos
        //List<CarreraInscriptosDTO> reportes = repEstudianteCarrera.buscarCarrerasConEstudiantes();
        //for (CarreraInscriptosDTO reporte : reportes){
        //   System.out.println(reporte);
        //}

        //g)Recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia
        //System.out.println(repEstudiante.buscarEstudiantesCarreraCiudad("TUDAI", "Rauch"));

        //3- Reporte de las carreras con inscriptos y egresados.
        //Ordenado alfabeticamente por carrera y cronológicamente por años
        //List<ReporteCarreraDTO> reportes = repCarrera.generarReporteCarreras();
        //for (ReporteCarreraDTO reporte : reportes){
        //    System.out.println(reporte);
        //}

        //Imprimir lista completa de estudiantes
        System.out.println(repEstudiante.buscarEstudiantes());
    }

}
