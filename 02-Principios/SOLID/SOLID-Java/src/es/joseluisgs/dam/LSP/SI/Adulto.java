package es.joseluisgs.dam.LSP.SI;

public class Adulto extends Persona {


    public Adulto(String nombre, String apellidos, String dni, String tarjeta) {
        super(nombre, apellidos);
        this.dni = dni;
        this.tarjeta = tarjeta;
    }

    private String dni;
    private String tarjeta;


    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(String tarjeta) {
        this.tarjeta = tarjeta;
    }

    public void pagar() {

        System.out.println("mi dni es " + getDni() + "pago con la tarjeta" + tarjeta);
    }
}