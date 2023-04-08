package es.joseluisgs.dam.LSP.SI;

public class Niño  extends Persona{

    private Adulto tutor;

    public Niño(String nombre, String apellidos,Adulto tutor) {
        super( nombre, apellidos);
        this.tutor=tutor;
    }

    public Adulto getTutor() {
        return tutor;
    }

    public void setTutor(Adulto tutor) {
        this.tutor = tutor;
    }

    public void pagar(){
        tutor.pagar();
    }

}