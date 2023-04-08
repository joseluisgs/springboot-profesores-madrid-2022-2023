package es.joseluisgs.dam.empleados.SI;

public class Empleado {
    private String nombre;
    private String email;
    private SeguridadSocial seguridadSocial;

    public Empleado(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    public void setSeguridadSocial(SeguridadSocial seguridadSocial) {
        this.seguridadSocial = seguridadSocial;
    }
}
