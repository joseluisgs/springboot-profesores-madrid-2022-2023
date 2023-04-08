package es.joseluisgs.dam.Avanzados.DAOVsRepository;

/**
 * El patrón Data Access Object (DAO) pretende principalmente independizar
 * la aplicación de la forma de acceder a la base de datos, o cualquier otro
 * tipo de repositorio de datos. Para ello se centraliza el código relativo al
 * acceso al repositorio de datos en las clases llamadas DAO
 * También se suele llamar Repository si se trata de una clase que encapsula
 * una conexión a un repositorio de datos.
 * Las diferencias. Una de las principales diferencias entre DAO y
 * Repository se halla en el nivel en el que se ubican. El primero se
 * ubica en un nivel mas bajo, mucho mas cerca a la fuente de datos,
 * mientras que el segundo se ubica al mismo nivel de la capa de modelo de dominio,
 * un poco más arriba que el primero.
 * DAO es una abstracción de la fuente de datos, y Repository es una
 * abstracción de una colección de objetos.
 * https://www.baeldung.com/java-dao-vs-repository
 */
public class DAOTest {
    public void test() {
        StudentDao studentDao = new StudentDaoImpl();

        //print all students
        for (Student student : studentDao.getAllStudents()) {
            System.out.println("Student: [RollNo : " + student.getRollNo() + ", Name : " + student.getName() + " ]");
        }


        //update student
        Student student =studentDao.getAllStudents().get(0);
        student.setName("Michael");
        studentDao.updateStudent(student);

        //get the student
        studentDao.getStudent(0);
        System.out.println("Student: [RollNo : " + student.getRollNo() + ", Name : " + student.getName() + " ]");
    }
}
