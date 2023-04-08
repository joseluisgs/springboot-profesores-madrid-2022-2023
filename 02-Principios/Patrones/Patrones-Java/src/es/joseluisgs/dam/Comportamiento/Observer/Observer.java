package es.joseluisgs.dam.Comportamiento.Observer;

public abstract class Observer {
    protected Subject subject;
    public abstract void update();
}