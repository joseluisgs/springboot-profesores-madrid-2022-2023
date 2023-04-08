package es.joseluisgs.dam.Creacionales.Singleton;

import java.util.UUID;

/**
 * Singleton es un patrón de diseño creacional que nos permite asegurarnos de que
 * una clase tenga una única instancia, a la vez que proporciona un punto de acceso
 * global a dicha instancia.
 * OJO, puede ser un antipatrón
 */
public class Singleton {
    private String id = UUID.randomUUID().toString();
    private static Singleton instance = null;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    @Override
    public String toString() {
        return "Singleton{" + "id=" + id + '}';
    }
}
