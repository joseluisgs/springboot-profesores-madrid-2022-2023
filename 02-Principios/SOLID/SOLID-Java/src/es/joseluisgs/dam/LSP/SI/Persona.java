package es.joseluisgs.dam.LSP.SI;

public abstract class Persona {

    private String nombre;
    private String apellidos;
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellidos() {
        return apellidos;
    }
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    public Persona( String nombre, String apellidos) {
        super();

        this.nombre = nombre;
        this.apellidos = apellidos;

    }

    abstract public void pagar();

}

