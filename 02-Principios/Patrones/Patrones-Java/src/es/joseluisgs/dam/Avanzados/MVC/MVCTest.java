package es.joseluisgs.dam.Avanzados.MVC;

/**
 * Separa la responsabilidad de la apicación de la lógica de negocio.
 * Model: Representa la información de la aplicación.
 * View: Representa la interfaz de usuario.
 * Controller: Representa la lógica de negocio.
 */
public class MVCTest {
    public void test () {
        Student model  = retriveStudentFromDatabase();
        StudentView view = new StudentView();
        StudentController controller = new StudentController(model, view);
        controller.updateView();
        controller.setStudentName("John");
        controller.updateView();
    }

    private Student retriveStudentFromDatabase(){
        Student student = new Student();
        student.setName("Robert");
        student.setRollNo("10");
        return student;
    }
}
