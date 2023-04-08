package es.joseluisgs.dam.Avanzados.DTO;

/**
 * Los DTO son un tipo de objetos que sirven únicamente para transportar datos.
 * EL DTO contiene las propiedades del objeto. Datos que pueden tener su origen en
 * una o más entidades de información.
 * Es decir, pueden fusionar datos de varias entidades en una sola.
 * o simplemente acoplar los datos que necesitamos para trasportarlos de o hacia un punto a otro.
 * Podemos necesitar mapeadores para armar el DTO o desarmarlo.
 */
public class DTOTest {
    public void test() {
        StudentBO studentBusinessObject = new StudentBO();

        //print all students
        for (StudentVO student : studentBusinessObject.getAllStudents()) {
            System.out.println("Student: [RollNo : " + student.getRollNo() + ", Name : " + student.getName() + " ]");
        }

        //update student
        StudentVO student = studentBusinessObject.getAllStudents().get(0);
        student.setName("Michael");
        studentBusinessObject.updateStudent(student);

        //get the student
        student = studentBusinessObject.getStudent(0);
        System.out.println("Student: [RollNo : " + student.getRollNo() + ", Name : " + student.getName() + " ]");
    }
}
