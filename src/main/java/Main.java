import com.opencsv.exceptions.CsvValidationException;
import dto.EstudianteDTO;
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

        //a)
        /*
        System.out.println("\n\n-----Dar de alta a un estudiante-----");
        Estudiante newEstudiante = new Estudiante(39111222,"Manuel","Perez",66,"Male","Necochea",99911);
        repEstudiante.insertarEstudiante(newEstudiante);
        */

        //b)
        /*
        System.out.println("\n\n-----Matricular estudiante en una carrera-----");
        Carrera newCarrera = new Carrera(50, "Diseñador Gráfico", 3);
        repCarrera.insertarCarrera(newCarrera);
        repEstudianteCarrera.matricularEstudiante(newEstudiante, newCarrera);
        */

        //c)
        System.out.println("\n\n-----Recuperar todos los estudiantes con el criterio de ordenamiento : Apellido-----");
        List<EstudianteDTO> estudiantesOrdenados = repEstudiante.buscarEstudiantesApellido();
        for(EstudianteDTO estudiante : estudiantesOrdenados)
            System.out.println(estudiante);

        //d)
        System.out.println("\n\n-----Recuperar un estudiante en base a su LU-----");
        System.out.println(repEstudiante.buscarEstudianteLU(19844));

        //e)
        System.out.println("\n\n-----Recuperar todos los estudiantes en base a su género-----");
        List<EstudianteDTO> estudiantesGenero = repEstudiante.buscarEstudiantesGenero("Male");
        for(EstudianteDTO estudiante : estudiantesGenero)
            System.out.println(estudiante);

        //f)
        System.out.println("\n\n-----Recuperar las carreras con estudiantes inscriptos y ordenar por cantidad de inscriptos-----");
        List<CarreraInscriptosDTO> carreras = repEstudianteCarrera.buscarCarrerasConEstudiantes();
        for (CarreraInscriptosDTO carrera : carreras)
            System.out.println(carrera);

        //g)
        System.out.println("\n\n-----Recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia-----");
        List<EstudianteDTO> estudiantesCarrera = repEstudiante.buscarEstudiantesCarreraCiudad("TUDAI", "Rauch");
        for(EstudianteDTO estudiante : estudiantesCarrera)
            System.out.println(estudiante);

        //3
        System.out.println("\n\n-----Reporte de las carreras con inscriptos y egresados. " +
                "Ordenado alfabeticamente por carrera y cronológicamente por años-----");
        List<ReporteCarreraDTO> reportes = repCarrera.generarReporteCarreras();
        for (ReporteCarreraDTO reporte : reportes)
           System.out.println(reporte);





    }

}
