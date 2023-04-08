package es.joseluisgs.dam.empleados.NO;

// Realmente es un o tiene atributos de seguridad social, y si el empleado esta en paro...
public class EmpleadoSeguridadSocial extends Empleado {
    private String ssn;
    private double salario;

    public EmpleadoSeguridadSocial(String nombre, String email, String ssn, double salario) {
        super(nombre, email);
        this.ssn = ssn;
        this.salario = salario;
    }
}
